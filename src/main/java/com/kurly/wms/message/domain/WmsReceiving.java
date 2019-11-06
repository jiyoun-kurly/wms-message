package com.kurly.wms.message.domain;

import com.kurly.wms.message.domain.enums.ReceivingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WmsReceiving {
    //recdh, recdi 생성을 위한 model
    private String recvky;
    private String recvit;
    private String statit;
    private String sapsts;
    private String skukey;
    private String lotnum;
    private String areaky;
    private String locaky;
    private String sectid;
    private String trnuid;
    private String packid;
    private String qtyrcv;
    private String qtydif;
    private String qtyuom;
    private String trunty;
    private String measky;
    private String uomkey;
    private String qtpuom;
    private String duomky;
    private String qtduom;
    private String indrcn;
    private String crecvd;
    private String rsncod;
    private String workid;
    private String worknm;
    private String hhttid;
    private String lota01;
    private String lota02;
    private String lota03;
    private String lota04;
    private String lota05;
    private String lota06;
    private String lota07;
    private String lota08;
    private String lota09;
    private String lota10;
    private String lota11;
    private String lota12;
    private String lota13;
    private String lota14;
    private String lota15;
    private String lota16;
    private String lota17;
    private String lota18;
    private String lota19;
    private String lota20;
    private String awmsno;
    private String refdky;
    private String refdit;
    private String refcat;
    private String refdat;
    private String desc01;
    private String desc02;
    private String asku01;
    private String asku02;
    private String asku03;
    private String asku04;
    private String asku05;
    private String eancod;
    private String gtincd;
    private String skug01;
    private String skug02;
    private String skug03;
    private String skug04;
    private String skug05;
    private String grswgt;
    private String netwgt;
    private String wgtunt;
    private String length;
    private String widthw;
    private String height;
    private String cubicm;
    private String capact;
    private String qtyorg;
    private String smandt;
    private String sebeln;
    private String sebelp;
    private String szmblno;
    private String szmipno;
    private String straid;
    private String svbeln;
    private String sposnr;
    private String stknum;
    private String stpnum;
    private String swerks;
    private String slgort;
    private String sdatbg;
    private String stdlnr;
    private String ssornu;
    private String ssorit;
    private String smblnr;
    private String szeile;
    private String smjahr;
    private String sxblnr;
    private String sbktxt;
    private String rcprsn;
    private String credat;
    private String cretim;
    private String creusr;
    private String lmodat;
    private String lmotim;
    private String lmousr;
    private String rcptty;
    private String status;

    public void setRecvitFromItemCnt(int itemCnt) {
        this.recvit = String.format("%06d", itemCnt);
    }

    public String getStatusDesc() {
        return ReceivingStatus.getDescOfValue(this.status);
    }

    public StringBuilder getErrorMessage (String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.awmsno).append("_");
        builder.append(this.refdky).append("_");
        builder.append(this.skukey).append("(");
        builder.append(this.qtyrcv).append("/");
        builder.append(this.qtyuom).append(") - ");
        builder.append(message);
        builder.append("\n");

        return builder;
    }

}
