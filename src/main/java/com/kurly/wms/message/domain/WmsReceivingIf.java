package com.kurly.wms.message.domain;

import com.kurly.wms.message.receive.model.RcvTransaction;
import com.kurly.wms.message.domain.enums.ReceivingStatus;
import com.kurly.wms.message.domain.modelMapper.WmsReceivingIfMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WmsReceivingIf {

    private Long transactionId; // mq 전송 key
    private Long purchaseOrderId;
    private Long purchaseOrderItemId;
    private Integer purchaseOrderItemSeq;
    private String purchaseOrderSubCode; // wms 내 발주 아이템 seq code
    private Long inspectionHeaderId;
    private String purchaseOrderCode;
    private LocalDate purchaseOrderDate; // 발주일
    private LocalDate receivingExpectedDate; // 입고예정일
    private LocalDateTime receivingDate; // 입고일
    private String orderType; // 발주유형
    private String supplierCode;
    private String goodsCode;
    private String unit;
    private String poSellingType; // 발주 판매유형 (선판매-SALES_FIRST/후판매-STORE_FIRST)
    private Integer orderQty; // 발주수량
    private Integer inspectionQty; // 검품수량
    private Integer receivingQty; // 정상입고 수량 (검품수량 - 불량수량)
    private Integer defectQty; // 불량 수량
    private LocalDate expirationDate; // 유통기한
    private LocalDate manufactureDate; // 제조일자
    private LocalDate centerSalesDeadlineDate; // 센터판매마감일자
    private Integer sellAvailableDate; // 판매가능일
    private Integer sellDeadlineDate; // 판매마감일
    private String releaseCode; // 출고방법
    private Boolean giftYn; // 증정품여부
    private String receivingCenter;  // 입고센터
    private String receivingWarehouse; // 입고창고
    private String status; // purchaseOrder 테이블 생성 여부
    private String createdBy;
    private String creationDate;
    private String updatedBy;
    private String updatedDate;


    public static WmsReceivingIf toWmsReceivingIf(RcvTransaction rcvTransaction) {
        WmsReceivingIf wmsReceivingIf = WmsReceivingIfMapper.getMapper().map(rcvTransaction, WmsReceivingIf.class);
        wmsReceivingIf.setStatus(ReceivingStatus.NEW.getValue());

        return wmsReceivingIf;
    }

    public static List<WmsReceivingIf> toWmsReceivingIfList(List<RcvTransaction> rcvTransactionList) {
        if (rcvTransactionList == null || rcvTransactionList.isEmpty()) {
            return new ArrayList<>();
        }

        return rcvTransactionList.stream()
                .map(rcv -> WmsReceivingIf.toWmsReceivingIf(rcv))
                .collect(Collectors.toList());
    }

    public String getStatusDesc() {
        return ReceivingStatus.getDescOfValue(this.status);
    }

    public void setPurchaseOrderSubCode() {
        this.purchaseOrderSubCode = String.format("01%04d", this.purchaseOrderItemSeq);
    }

    public StringBuilder getErrorMessage(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.transactionId).append("_");
        builder.append(this.purchaseOrderCode).append("_");
        builder.append(this.goodsCode).append("(");
        builder.append(this.receivingQty).append("/");
        builder.append(this.orderQty).append(") - ");
        builder.append(message);
        builder.append("\n");

        return builder;
    }
}
