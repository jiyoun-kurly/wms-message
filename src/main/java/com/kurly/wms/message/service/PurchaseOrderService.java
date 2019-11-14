package com.kurly.wms.message.service;

import com.kurly.wms.message.domain.WmsPurchaseOrderIf;

import java.util.List;

public interface PurchaseOrderService {
    void savePurchaseOrder(List<WmsPurchaseOrderIf> wmsPurchaseOrderIfs) throws Exception;
}
