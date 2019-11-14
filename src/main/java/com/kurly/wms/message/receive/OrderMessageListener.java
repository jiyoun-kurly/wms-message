package com.kurly.wms.message.receive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.kurly.wms.message.domain.enums.OrderType;
import com.kurly.wms.message.receive.model.OrderInterfaceMaster;
import com.kurly.wms.message.send.MessageQueueService;
import com.kurly.wms.message.service.JobStatusService;
import com.kurly.wms.message.service.MessagingService;
import com.kurly.wms.message.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageListener {

    private final OrderService orderService;

    private final MessageQueueService messageQueueService;

    private final MessagingService messagingService;

    private final JobStatusService jobStatusService;

    @Value("${slack.info.receivingIfSlackChannel}")
    private String receivingIfChannel;

    @Value("${slack.info.orderIfSlackChannel}")
    private String orderIfChannel;


    /**
     * 주문정보 Subscriber
     * - 주문정보 WMS 인터페이스 테이블 (IFWMS113)에 등록
     * - 등록성공 건수 메세지 전송 (ReplyTo)
     *
     * @param orderMessage
     * @param headers
     * @param messageHeaders
     * @param messageHeaderAccessor
     */
    @JmsListener(destination = "${messages.topic.order}", containerFactory = "jmsListenerContainerFactory")
    public void orderListener(@Payload TextMessage orderMessage,
                              @Headers Map<String, Object> headers,
                              MessageHeaders messageHeaders,
                              JmsMessageHeaderAccessor messageHeaderAccessor) throws Exception {

        try {
            log.info("### orderListener");

            if (jobStatusService.hasRunningJob(message ->
                    messagingService.sendSlackMessage("[WMS 주문연동 실패]", orderIfChannel, message))) {
                return;
            }

            // 실행 중으로 상태 변경
            jobStatusService.updateJobStatus("Y");

            String jsonMessage = orderMessage.getText();    // 수신 메세지
            String orderType = orderMessage.getStringProperty("order_type");   // 오더유형 (컬리,TC)
            int degreeCount = orderMessage.getIntProperty("degree_count");   // 연동회차
            Boolean isRegister = orderMessage.getBooleanProperty("is_register");

            if (!OrderType.TC_ORDER.getValue().equals(orderType)) {
                log.info("### not a tcOrder : " + orderType);
                return;
            }

            log.info(" [CorrelationId]:" + orderMessage.getJMSCorrelationID());

            // JSON Text -> POJO
            ObjectMapper mapper = new ObjectMapper();
            CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, OrderInterfaceMaster.class);
            List<OrderInterfaceMaster> orderList = mapper.readValue(jsonMessage, javaType);

            log.info("[Receive Order Count]" + orderList.size());
            if (orderList.size() == 0) return;

            List<String> dupOrderCodeList = new ArrayList<>();

            if (isRegister) {
                orderService.getDupOrderCodeList(orderList);
            }

            if (dupOrderCodeList.size() > 0) {
                log.info("###### 중복주문건 연동 ");
                log.info("[duplicate order count]" + dupOrderCodeList.size());
                log.info("[duplicate order list]" + dupOrderCodeList);
                String displayName = "[중복주문건 연동발생]";

                StringBuilder alarmMessage = new StringBuilder();
                alarmMessage.append("<!channel>\n");
                alarmMessage.append("  - *중복주문번호*: " + dupOrderCodeList + "\n");
                alarmMessage.append("해당 주문번호는 이미 신규주문건으로 연동된 주문번호입니다. 연동처리 대상에서 제외됩니다  \n");
                alarmMessage.append("주문정보를 다시 확인하시기 바랍니다.  \n");
                messagingService.sendSlackMessage(displayName, orderIfChannel, alarmMessage.toString());

                // 중복주문건 제거
                orderList = orderList.stream()
                        .filter(order -> !dupOrderCodeList.contains(order.getOrderCode()))
                        .collect(toList());
            }

            log.info("##### [연동대상 주문건수]" + orderList.size());
            if (orderList.size() == 0) return;

            // 주문정보 인터페이스 테이블 등록
            List<String> insertedOrderNos = orderService.insertOrderInterface(orderList);
            log.debug("##### [연동완료 주문번호]" + insertedOrderNos);

            messageQueueService.sendOrderReplyMessage(orderMessage.getJMSReplyTo(), orderMessage.getJMSCorrelationID(), insertedOrderNos, degreeCount, orderType);

            String displayName = "[WMS 주문연동 결과 - 차수: " + degreeCount + "]";

            StringBuilder alarmMessage = new StringBuilder();
            alarmMessage.append("<!channel>\n");
            alarmMessage.append("  - *수신건수*: " + orderList.size() + "건\n");
            alarmMessage.append("  - *중복건수*: " + dupOrderCodeList.size() + "건\n");
            alarmMessage.append("  - *등록건수*: " + insertedOrderNos.size() + "건\n");
            alarmMessage.append(" 수신건수와 등록건수가 다른 경우, 대시보드 또는 시스템로그를 확인하시고 조치하시기 바랍니다.  \n");
            messagingService.sendSlackMessage(displayName, orderIfChannel, alarmMessage.toString());

            // 주문 인터페이스 등록완료 이벤트 발행  --> TMS_ORDER 등록

        } catch (Exception e) {
            log.error("##### Message Listener Exception");
            e.printStackTrace();
            String displayName = "[주문메세지 수신 에러발생]";
            StringBuilder alarmMessage = new StringBuilder();
            alarmMessage.append("<!channel>\n");
            alarmMessage.append("" + e.getMessage() + "\n");
            messagingService.sendSlackMessage(displayName, orderIfChannel, alarmMessage.toString());

        } finally {
            jobStatusService.updateJobStatus("N");
        }
    }

}
