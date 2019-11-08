
package com.kurly.wms.message.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class WmsSupplierIf implements Serializable {
    private String mandt;
    private String werks;
    private String land1;
    private String aedat;
    private String ifflg;
    private String zclassifier;
    private String telf1;
    private String telfx;
    private String stcd2;
    private String j_1kfrepre;
    private String addr;
    private String addr2;
    private String addr3;
    private String addr4;
    private String smtp_addr;
    private String cname;
    private String cphon;
    private String bname;
    private String bphon;
    private String pstlz;
    private String credat;
    private String cretim;
    private String tdate;
    private String zcode;
    private String name1;
    private String erdat;

    public void setMandt(String mandt) {
        this.mandt = mandt;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
    }

    public void setAedat(String aedat) {
        this.aedat = aedat;
    }

    public void setIfflg(String ifflg) {
        this.ifflg = ifflg;
    }

    public void setZclassifier(String zclassifier) {
        this.zclassifier = zclassifier;
    }

    public void setTelf1(String telf1) {
        this.telf1 = telf1;
    }

    public void setTelfx(String telfx) {
        this.telfx = telfx;
    }

    public void setStcd2(String stcd2) {
        this.stcd2 = stcd2;
    }

    public void setJ_1kfrepre(String j_1kfrepre) {
        this.j_1kfrepre = j_1kfrepre;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    public void setSmtp_addr(String smtp_addr) {
        this.smtp_addr = smtp_addr;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCphon(String cphon) {
        this.cphon = cphon;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setBphon(String bphon) {
        this.bphon = bphon;
    }

    public void setPstlz(String pstlz) {
        this.pstlz = pstlz;
    }

    public void setCredat(String credat) {
        this.credat = credat;
    }

    public void setCretim(String cretim) {
        this.cretim = cretim;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public void setZcode(String zcode) {
        this.zcode = zcode;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setErdat(String erdat) {
        this.erdat = erdat;
    }
}
