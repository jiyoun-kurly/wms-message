package com.kurly.wms.message.repository;

import com.kurly.wms.message.domain.WmsOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {

    List<String> findDuplicateNewOrder(List<String> orderNoList);

    WmsOrder findOrder(String orderCode, String orderType);

    void addOrder(WmsOrder orderDto);

    void modOrder(WmsOrder orderDto);

    void cancelOrder(String orderCode);
}
