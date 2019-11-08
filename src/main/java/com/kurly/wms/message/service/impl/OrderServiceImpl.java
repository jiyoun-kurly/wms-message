package com.kurly.wms.message.service.impl;

import com.kurly.wms.message.receive.model.OrderInterfaceItem;
import com.kurly.wms.message.receive.model.OrderInterfaceMaster;
import com.kurly.wms.message.domain.WmsOrder;
import com.kurly.wms.message.domain.enums.OrderStatus;
import com.kurly.wms.message.repository.OrderMapper;
import com.kurly.wms.message.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Value("${slack.info.orderIfSlackChannel}")
    private String orderIfChannel;

    /**
     * 수신받은 신규주문건들 중, 동일날짜,
     * 신규상태(A)로 동일주문번호가 ORDER_INTERFACE_MASTER 에 존재하는 경우, 중복주문건으로 처리
     *
     * @param orderInfo
     * @return
     */
    @Override
    public List<String> getDupOrderCodeList(List<OrderInterfaceMaster> orderInfo) {
        List<String> orderNoList = orderInfo.stream()
                .filter(order -> order.getOrderStatus().equals(OrderStatus.NEW_ORDER.getStatusCode())
                        && order.isCancelStatus() )   // 신규주문건 추출
                .map(OrderInterfaceMaster::getOrderCode).collect(toList());

        if (orderNoList.size() == 0) {
            return orderNoList;
        }

        return orderMapper.findDuplicateNewOrder(orderNoList);
    }

    /**
     * 주문연동 등록 From MessageBroker
     * @param orderList
     * @return
     */
    @Override
    public List<String> insertOrderInterface(List<OrderInterfaceMaster> orderList) {

        List<String> successNoList = new ArrayList<>();

        for (OrderInterfaceMaster master : orderList) {
            try {
                OrderInterfaceItem item = master.getOrderItems().get(0);
                WmsOrder order = orderMapper.findOrder(master.getOrderCode(), item.getOrderType());

                if (order == null) {
                    orderMapper.addOrder(WmsOrder.getNewOrder(master));
                } else if (!"D".equals(order.getStatus()) && master.isCancelStatus()) {
                    orderMapper.cancelOrder(master.getOrderCode());
                } else if (order.isChanged(master, item)){
                    orderMapper.modOrder(WmsOrder.getUpdateOrder(master, item));
                }

                successNoList.add(master.getOrderCode());
            } catch (Exception e) {
                e.printStackTrace();
                StringBuilder builder = new StringBuilder();
                builder.append("### insertOrderInterface fail: [");
                builder.append(master.getOrderStatus());
                builder.append("] ");
                builder.append(master.getOrderCode());
                builder.append("\n");
                builder.append(e.getMessage());
                log.debug(builder.toString());
            }
        }

        return successNoList;
    }

}
