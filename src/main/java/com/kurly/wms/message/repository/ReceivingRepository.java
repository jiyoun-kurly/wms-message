package com.kurly.wms.message.repository;

import com.kurly.wms.message.domain.WmsReceiving;
import com.kurly.wms.message.domain.WmsReceivingIf;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ReceivingRepository {

    List<WmsReceivingIf> findNotCompletedIfList();

    int insert(WmsReceivingIf wmsReceivingIf);

    int changeStatusReceivingIfList(HashMap receivingIfIdList);

    void getRECVKY(WmsReceiving receiving);

    int saveFromIfToRecdh(WmsReceiving receiving);

    int saveFromIfToRecdi(WmsReceiving receiving);

    List<WmsReceivingIf> findByTransactionIdIn(List<Long> receivingIfIdList);

    List<WmsReceiving> findReceivingByTransactionIdIn(List<Long> receivingIfIdList);
}
