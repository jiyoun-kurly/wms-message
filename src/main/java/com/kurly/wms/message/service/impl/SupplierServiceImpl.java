package com.kurly.wms.message.service.impl;

import com.kurly.wms.message.domain.WmsSupplierIf;
import com.kurly.wms.message.repository.SupplierRepository;
import com.kurly.wms.message.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    /**
     * 공급사 등록 from new eSCM
     * @param wmsSupplierIf
     */
    public void saveSupplier(WmsSupplierIf wmsSupplierIf) throws Exception {
        log.debug("### save supplier start : {} " + wmsSupplierIf.getZcode());
        int count =  supplierRepository.checkPartner(wmsSupplierIf);
        if (count > 0) {
            supplierRepository.modPartner(wmsSupplierIf);
        } else {
            supplierRepository.addPartner(wmsSupplierIf);
        }
        log.debug("### save supplier end: {} " + wmsSupplierIf.getZcode());
    }


}
