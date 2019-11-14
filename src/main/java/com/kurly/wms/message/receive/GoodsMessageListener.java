package com.kurly.wms.message.receive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurly.wms.message.receive.model.Goods;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoodsMessageListener {

    /**
     * new eSCM 으로 부터 상품정보 수신
     *
     * @param goodsInfo
     * @param headers
     * @param messageHeaders
     * @param messageHeaderAccessor
     * @throws JMSException
     */
    @JmsListener(destination = "${messages.topic.goods}", containerFactory = "jmsListenerContainerFactory")
    public void receiveGoodsInfo(@Payload TextMessage goodsInfo,
                                 @Headers Map<String, Object> headers,
                                 MessageHeaders messageHeaders,
                                 JmsMessageHeaderAccessor messageHeaderAccessor) throws JMSException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Goods goods = mapper.readValue(goodsInfo.getText(), new TypeReference<Goods>() {});

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
