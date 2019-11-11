package com.kurly.wms.message.receive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurly.wms.message.domain.WmsSupplierIf;
import com.kurly.wms.message.receive.model.Supplier;
import com.kurly.wms.message.receive.model.mapper.SupplierMapper;
import com.kurly.wms.message.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class SupplierMessageListener {

    private static final String CONSUMER = "Consumer.wms.";
    private static final String QUEUE_SUPPLIER_CHANNEL = CONSUMER + "VirtualTopic.supplierTest1"; // TODO:: 변경하기
    private final ObjectMapper objectMapper;
    private final SupplierService supplierService;

    /**
     * new eSCM 으로 부터 공급사 정보
     * @throws JMSException
     */
    @JmsListener(destination = QUEUE_SUPPLIER_CHANNEL, containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveSupplierInfo(@Payload TextMessage supplierInfo) throws JMSException {

        try {
            Supplier supplier = objectMapper.readValue(supplierInfo.getText(), new TypeReference<Supplier>() {});
            log.debug("supplier => {}", supplier);
            WmsSupplierIf wmsSupplierIf = SupplierMapper.createInstance(supplier);
            supplierService.saveSupplier(wmsSupplierIf);
        } catch (Exception ioe) {
            log.error("supplier => {}", supplierInfo.getText());
            ioe.printStackTrace();
        }

    }

}
