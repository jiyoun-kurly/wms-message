package com.kurly.wms.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW_ORDER("A"), // 신규주문
    CHANGE_ORDER("T"),  // 주문변경
    CHANGE_ADDRESS_ORDER("C"),  // 주소변경
    CANCEL_ORDER("D");  // 주문취소

    private String statusCode;

    public static OrderStatus getStatusOfValue(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getStatusCode().equals(value)) {
                return orderStatus;
            }
        }
        return null;
    }
}
