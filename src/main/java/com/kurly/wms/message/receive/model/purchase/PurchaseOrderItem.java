package com.kurly.wms.message.receive.model.purchase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class PurchaseOrderItem implements Serializable {

    private Integer purchaseOrderItemId;

    // 발주상품 순번
    private Integer purchaseOrderItemSeq;

    // 상품
    private Integer goodsId;

    private String goodsCode;

    private String goodsName;

    private String goodsSubCode;

    private String goodsSubName;

    //보관방법 from goods
    private String keepLevel;

    // 상품견적서
    private Integer goodsEstimatePrice;

    //판매방법
    private String sellingType;

    //박스당 입고수량
    private Integer boxPerQnty;

    //박스 수량
    private Integer boxQnty;

    //총 수량
    private Integer totalQnty;

    /*공급가*/
    private Integer price;

    /*세금가*/
    private Integer taxPrice;

    //총 공급가(세금포함)
    private Integer totalPrice;

    // 유통기한 - LocalDate
    private String expirationDate;

    // 제조일자 - LocalDate
    private String manufactureDate;

    //출고방법
    private String releaseCode;

    //출고주소
    private String releaseAddress;

    //출고상세주소
    private String releaseDetailAddress;

    //비고
    private String remarks;

    //증정품여부
    private Boolean giftYn;

    private String changeReasonCode;

    private String unit;
}
