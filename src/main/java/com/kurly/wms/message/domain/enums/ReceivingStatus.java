package com.kurly.wms.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceivingStatus {
    NEW("N","초기 상태"),
    CREATED("C","재고 생성"),
    DELETED("D","발주 취소"),
    GOODS_NE("GNE","상품 없음"),
    SUPPLIER_NE("SNE","공급사 없음"),
    WMS("W","WMS 입고");

    private String value;
    private String desc;

    public static String getDescOfValue(String value) {

        for (ReceivingStatus receivingStatus : ReceivingStatus.values()) {
            if (receivingStatus.getValue().equals(value)) {
                return receivingStatus.getDesc();
            }
        }
        return null;
    }
}
