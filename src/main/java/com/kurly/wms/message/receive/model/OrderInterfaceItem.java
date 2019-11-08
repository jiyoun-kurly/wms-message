package com.kurly.wms.message.receive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInterfaceItem {

    private int seq;
    private String orderCode;
    private String orderType;
    private String invoiceNo;
    private String supplyCode;
    private String goodsCode;
    private String goodsSubCode;
    private String goodsName;
    private String goodsOptionName;
    private int orderQnty;
    private int cancelQnty;
    private String goodsUnit;
    private String goodsType;
    private Date createDate;
    private Date updateDate;



}
