package com.kurly.wms.message.service;

import com.kurly.wms.message.receive.model.order.OrderInterfaceMaster;

import java.util.List;

public interface OrderService {

	List<String> getDupOrderCodeList(List<OrderInterfaceMaster> transactionList);

	List<String> insertOrderInterface(List<OrderInterfaceMaster> orderIfList);
}
