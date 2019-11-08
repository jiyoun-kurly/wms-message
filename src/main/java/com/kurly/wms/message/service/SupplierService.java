package com.kurly.wms.message.service;

import com.kurly.wms.message.domain.WmsSupplierIf;

public interface SupplierService {
    void saveSupplier(WmsSupplierIf wmsSupplierIf) throws Exception;
}
