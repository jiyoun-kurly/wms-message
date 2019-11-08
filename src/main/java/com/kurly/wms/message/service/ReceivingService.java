package com.kurly.wms.message.service;

import com.kurly.wms.message.receive.model.RcvTransaction;
import com.kurly.wms.message.domain.WmsReceivingIf;

import java.util.List;

public interface ReceivingService {

	List<RcvTransaction> removeDuplicatedTransaction(List<RcvTransaction> transactionList);

	List<WmsReceivingIf> insertReceivingIf(List<RcvTransaction> orderIfList);

	void insertReceiving(List<WmsReceivingIf> orderIfList);

	void manualSaveReceiving();
}
