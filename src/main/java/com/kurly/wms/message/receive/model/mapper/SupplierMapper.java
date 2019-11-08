package com.kurly.wms.message.receive.model.mapper;

import com.kurly.wms.message.domain.WmsSupplierIf;
import com.kurly.wms.message.receive.model.Supplier;
import in.ashwanthkumar.utils.lang.StringUtils;

public class SupplierMapper {
    public static WmsSupplierIf createInstance(Supplier supplier) {
        WmsSupplierIf wmsSupplierIf = new WmsSupplierIf();
        wmsSupplierIf.setMandt("110");
        wmsSupplierIf.setWerks("TF");
        wmsSupplierIf.setLand1("KR");
        wmsSupplierIf.setAedat("N");
        wmsSupplierIf.setIfflg("N");
        wmsSupplierIf.setZcode(supplier.getSupplierCode());
        wmsSupplierIf.setName1(supplier.getSupplierName());
        wmsSupplierIf.setErdat(convertReleaseCode(supplier.getReleaseCode()));
        wmsSupplierIf.setAedat(wmsSupplierIf.getErdat().equals("IT002") ? "Y" : "N");
        wmsSupplierIf.setZclassifier(supplier.getSupplierCode().substring(0,2)); // TODO :: DB저장형태 다시한번 확인하기
        wmsSupplierIf.setTelf1(StringUtils.isEmpty(supplier.getTel()) ? " " : supplier.getTel());
        wmsSupplierIf.setTelfx(StringUtils.isEmpty(supplier.getFax()) ? " " : supplier.getFax());
        wmsSupplierIf.setStcd2(StringUtils.isEmpty(supplier.getLicenseNum()) ? " " : supplier.getLicenseNum());
        wmsSupplierIf.setJ_1kfrepre(StringUtils.isEmpty(supplier.getCeoName()) ? " " : supplier.getCeoName());
        wmsSupplierIf.setAddr(StringUtils.isEmpty(supplier.getAddress()) ? " " : supplier.getAddress());
        wmsSupplierIf.setAddr2(StringUtils.isEmpty(supplier.getDetailAddress()) ? " " : supplier.getDetailAddress());
        wmsSupplierIf.setAddr3(" ");
        wmsSupplierIf.setAddr4(" ");
        wmsSupplierIf.setSmtp_addr(" ");
        wmsSupplierIf.setCname(" ");
        wmsSupplierIf.setCphon(" ");
        wmsSupplierIf.setBname(" ");
        wmsSupplierIf.setBphon(" ");
        wmsSupplierIf.setPstlz(" ");

        /* TODO:: 시간넣기 DB저장값 파악해서 넣기*/
        wmsSupplierIf.setCredat(" ");
        wmsSupplierIf.setCretim(" ");
        wmsSupplierIf.setTdate(" ");

        return wmsSupplierIf;
    }

    private static String convertReleaseCode(String releaseCode) {
        if ("KURLY_PICKUP".equals(releaseCode)) {
            return "IT002";
        } else if ("PARCEL".equals(releaseCode)) {
            return "IT001";
        } else if ("DIRECT_DELIVERY".equals(releaseCode)) {
            return "IT003";
        } else {
            return "IT999";
        }
    }

}
