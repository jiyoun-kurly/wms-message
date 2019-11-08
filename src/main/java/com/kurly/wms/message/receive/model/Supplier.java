package com.kurly.wms.message.receive.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@Getter
@RequiredArgsConstructor
public class Supplier implements Serializable {

    //사업자번호
    private String licenseNum;

    //공급사명
    private String supplierName;

    //대표자명
    private String ceoName;

    //공급사 코드
    private String supplierCode;

    //주소
    private String address;

    //상세주소
    private String detailAddress;

    //대표 연락처
    private String tel;

    //대표 fax 번호
    private String fax;

    //거래여부
    private Boolean dealYn;

    //출고방법
    private String releaseCode;

    //출고주소
    private String releaseAddress;

    //출고상세주소
    private String releaseDetailAddress;

    //비고
    private String remarks;

    //공급사담당자명
    private String supplyManagerName;

    //공급사 담당자 연락처
    private String supplyManagerTel;


}
