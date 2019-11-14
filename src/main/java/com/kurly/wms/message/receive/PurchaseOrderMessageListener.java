package com.kurly.wms.message.receive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurly.wms.message.domain.WmsPurchaseOrderIf;
import com.kurly.wms.message.receive.model.purchase.PurchaseOrder;
import com.kurly.wms.message.receive.model.mapper.PurchaseOrderMapper;
import com.kurly.wms.message.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderMessageListener {

    private final ObjectMapper objectMapper;

    private final PurchaseOrderService purchaseOrderService;

    private final PurchaseOrderMapper purchaseOrderMapper;

    /**
     * new eSCM 으로 부터 공급사 정보
     * @throws JMSException
     */
    @JmsListener(destination = "${messages.topic.purchase-order}", containerFactory = "jmsListenerContainerFactory")
    public void receiveSupplierInfo(@Payload TextMessage purchaseOrderText) throws JMSException {

        try {
            PurchaseOrder purchaseOrder = objectMapper.readValue(purchaseOrderText.getText(), new TypeReference<PurchaseOrder>() {});
            log.debug("purchaseOrder => {}", purchaseOrder);
            WmsPurchaseOrderIf purchaseOrderInfo = purchaseOrderMapper.createInstance(purchaseOrder);
            List<WmsPurchaseOrderIf> purchaseOrderIfs = purchaseOrder.getPurchaseOrderItems().stream()
                    .map(item -> purchaseOrderMapper.createInstanceItem(purchaseOrderInfo, item)).collect(Collectors.toList());
            purchaseOrderService.savePurchaseOrder(purchaseOrderIfs);
        } catch (Exception ioe) {
            log.error("purchaseOrder => {}", purchaseOrderText.getText());
            ioe.printStackTrace();
        }
    }

}
