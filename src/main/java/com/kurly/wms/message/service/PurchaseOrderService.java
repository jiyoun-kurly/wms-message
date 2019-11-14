package com.kurly.wms.message.service;

import com.kurly.wms.message.domain.MdOrderDocumentDto;
import com.kurly.wms.message.domain.WmsSupplierIf;

import java.util.List;

public interface PurchaseOrderService {
    void savePurchaseOrder(List<MdOrderDocumentDto> mdOrderDocumentDtos) throws Exception;
}
