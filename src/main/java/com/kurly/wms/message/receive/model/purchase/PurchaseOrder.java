package com.kurly.wms.message.receive.model.purchase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PurchaseOrder implements Serializable {

    private Integer purchaseOrderId;

    // 발주코드
    // 발주서 등록 후 자동발번 / T+발주일+purchaseOrderId
    private String purchaseOrderCode;

    // 공급사 id
    private Integer supplierId;

    private String supplierCode;

    // 발주일
    private String purchaseOrderDate;

    // 입고예정일
    private String receivingEstimateDate;

    // 입고센터
    private String receivingCenter;

    // 입고창고
    private String receivingWarehouse;

    // 발주구분
    private String orderType;

    // 총 공급가(발주코드에 해당하는 총 공급가 정보들의 합계)
    private Integer purchaseOrderPrice;

    // 총 세금 공급가(발주코드에 해당하는 총 세금 정보들의 합계 )
    private Integer purchaseOrderTaxPrice;

    // 총 공급가(발주코드에 해당하는 총 공급가 정보들의 합계(세금포함))
    private Integer purchaseOrderTotalPrice;

    // 상태
    private String approvalStatus;

    // 발주자
    private String purchaseOrderManager;

    // 발주아이템목록
    private List<PurchaseOrderItem> purchaseOrderItems;
}
