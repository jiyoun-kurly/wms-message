package com.kurly.wms.message.receive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kurly.wms.message.common.deserializer.LocalDateDeserializer;
import com.kurly.wms.message.common.deserializer.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RcvTransaction {

    // mq 전송 key
    private Long transactionId;

    private Long purchaseOrderId;

    private Long purchaseOrderItemId;

    private Integer purchaseOrderItemSeq;

    // wms 내 발주 아이템 seq code
    private String purchaseOrderSubCode;

    private Long inspectionHeaderId;

    private String purchaseOrderCode;

    // 발주일
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate purchaseOrderDate;

    // 입고예정일
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate receivingExpectedDate;

    // 입고일
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime receivingDate;

    // 발주유형
    private String orderType;

    private String supplierCode;

    private String goodsCode;

    private String unit;

    // 발주 판매유형 (선판매-SALES_FIRST/후판매-STORE_FIRST)
    private String poSellingType;

    // 발주수량
    private Integer orderQty;

    // 검품수량
    private Integer inspectionQty;

    // 정상입고 수량 (검품수량 - 불량수량)
    private Integer receivingQty;

    // 불량 수량
    private Integer defectQty;

    // 유통기한
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expirationDate;

    // 제조일자
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate manufactureDate;

    // 센터판매마감일자
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate centerSalesDeadlineDate;

    // 판매가능일
    private Integer sellAvailableDate;

    // 판매마감일
    private Integer sellDeadlineDate;

    // 출고방법
    private String releaseCode;

    // 증정품여부
    private Boolean giftYn;

    // 입고센터
    private String receivingCenter;

    // 입고창고
    private String receivingWarehouse;

    private String createdBy;

    private List<String> files;

}
