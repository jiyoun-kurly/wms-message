package com.kurly.wms.message.send.impl;

import com.kurly.wms.message.send.MessageQueueService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("messageQueueService")
public class MessageQueueServiceImpl implements MessageQueueService {

    @Value("${slack.info.slackUrl}")
    private String slackUrl;

    private final JmsTemplate jmsTemplate;

    /**
     * 입고연동 처리 결과 메세지 전송
     *
     * @param destination
     * @param sendMessageId
     * @param message
     */
    @Override
    public void sendReceivingReplyMessage(Destination destination, String sendMessageId, List<Long> message) {

        log.info ( "##### [WmsReceiving I/F Reply Send] Start" );
        jmsTemplate.convertAndSend ( destination, message, jmsMessage -> {
            log.info("##### destination: " + destination);
            jmsMessage.setJMSCorrelationID ( sendMessageId );
            jmsMessage.setStringProperty ( "systemCode", "WMS" );   // 시스템명

            return  jmsMessage;
        });
        log.info ( "##### [WmsReceiving I/F Reply Send] End" );
    }

    /**
     * 주문연동 처리 결과 메세지 전송
     *
     * @param destination
     * @param sendMessageId
     * @param message
     */
    @Override
    public void sendOrderReplyMessage(Destination destination, String sendMessageId, List<String> message, int degreeCount, String orderType) {

        log.info ( "##### [WmsOrder I/F Reply Send] Start" );
        jmsTemplate.convertAndSend ( destination, message, jmsMessage -> {
            log.info("##### destination: " + destination);
            jmsMessage.setJMSCorrelationID ( sendMessageId );
            jmsMessage.setStringProperty ( "sub_system", "WMS" );   // 시스템명
            jmsMessage.setStringProperty ( "order_type", orderType);    // 주문유형
            jmsMessage.setIntProperty ( "degree_count", degreeCount );  // 연동회차

            return  jmsMessage;
        });
        log.info ( "##### [WmsReceiving I/F Reply Send] End" );
    }

}
