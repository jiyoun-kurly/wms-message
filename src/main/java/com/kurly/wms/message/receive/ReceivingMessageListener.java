package com.kurly.wms.message.receive;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.kurly.wms.message.receive.model.RcvTransaction;
import com.kurly.wms.message.domain.WmsReceivingIf;
import com.kurly.wms.message.send.MessageQueueService;
import com.kurly.wms.message.service.MessagingService;
import com.kurly.wms.message.service.ReceivingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceivingMessageListener {

    private static final String CONSUMER = "Consumer.wms.";
    private static final String QUEUE_RECEIVING_CHANNEL = CONSUMER + "VirtualTopic.rcvTransaction";

    private final ReceivingService receivingService;

    private final MessageQueueService messageQueueService;

    private final MessagingService messagingService;

    @Value("${slack.info.receivingIfSlackChannel}")
    private String receivingIfChannel;

    @Value("${slack.info.orderIfSlackChannel}")
    private String orderIfChannel;

    /**
     * 입고정보 Subscriber
     * - 입고정보 WMS 인터페이스 테이블에 등록
     * - 인터페이스 테이블에 등록된 정보를 RECDH, RECDI 테이블에 등록
     * - 등록성공 건수 메세지 전송 (ReplyTo)
     *
     * @param receiveMessage
     * @param headers
     * @param messageHeaders
     * @param messageHeaderAccessor
     */
    @JmsListener(destination = QUEUE_RECEIVING_CHANNEL, containerFactory = "jmsListenerContainerFactory")
    public void receivingListener(@Payload TextMessage receiveMessage,
                                  @Headers Map<String, Object> headers,
                                  MessageHeaders messageHeaders,
                                  JmsMessageHeaderAccessor messageHeaderAccessor) throws JMSException {

        try {
            log.info("### receivingListener");
            String jsonMessage = receiveMessage.getText();
            log.info(" [CorrelationId]:" + receiveMessage.getJMSCorrelationID());

            // JSON Text -> POJO
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
            CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, RcvTransaction.class);
            List<RcvTransaction> receivingIfList = mapper.readValue(jsonMessage, javaType);
            log.info("[WmsReceiving Count]" + receivingIfList.size());
            if (receivingIfList.size() == 0) return;

            // 중복입고 체크, 중복입고가 있는 경우 처리목록에세 제거함.
            receivingIfList = receivingService.removeDuplicatedTransaction(receivingIfList);
            log.info("##### [연동대상 주문건수]" + receivingIfList.size());
            if (receivingIfList.size() == 0) return;

            // WMS 입고정보 인터페이스 테이블 등록
            List<WmsReceivingIf> insertedReceivings = receivingService.insertReceivingIf(receivingIfList);
            List<Long> insertedReceivingNos = insertedReceivings.stream().map(WmsReceivingIf::getTransactionId).collect(Collectors.toList());
            log.debug("##### [연동완료 전송번호]" + insertedReceivingNos);

            // 등록된 입고전송ID목록 Reply channel로 전송.
            messageQueueService.sendReceivingReplyMessage(receiveMessage.getJMSReplyTo(), receiveMessage.getJMSCorrelationID(), insertedReceivingNos);

            // 입고 인터페이스 등록완료 이벤트 발행  --> RECDI 등록
            receivingService.insertReceiving(insertedReceivings);

        } catch (Exception e) {
            log.error("##### Message Listener Exception");
            e.printStackTrace();
            String displayName = "[RMS -> WMS 에러발생]";
            StringBuilder alarmMessage = new StringBuilder();
            alarmMessage.append("<!channel>\n");
            alarmMessage.append(e.getMessage() + "\n");
            messagingService.sendSlackMessage(displayName, receivingIfChannel, alarmMessage.toString());

        }
    }


}
