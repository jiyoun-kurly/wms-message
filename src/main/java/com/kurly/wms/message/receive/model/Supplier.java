package com.kurly.wms.message.receive.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Getter
@RequiredArgsConstructor
@ToString
public class Supplier implements Serializable {

    private Integer supplierId;

    //사업자번호
    private String licenseNum;

    //공급사명
    private String supplierName;

    //대표자명
    private String ceoName;

    //공급사 코드
    private String supplierCode;

    //법인등록번호
    private String corporateNum;

    //업태
    private String businessConditions;

    //종목(과목)
    private String businessCategory;

    //주요취금품목
    private String mainItem;

    //주소
    private String address;

    //상세주소
    private String detailAddress;

    //대표 연락처
    private String tel;

    //대표 fax 번호
    private String fax;

    // 과/면세
    private String taxation;

    //거래여부
    private Boolean dealYn;

    //제조일/유통기한 입고시 등록
    private Boolean excludeExpiration;

    //결제일(결제조건)
    private String paymentDate;

    //결제은행
    private String paymentBank;

    //결제 계좌번호
    private String paymentAccountNum;

    //결제 계좌 예금주
    private String paymentAccountName;

    //출고방법
    private String releaseCode;

    //출고주소
    private String releaseAddress;

    //출고상세주소
    private String releaseDetailAddress;

    //비고
    private String remarks;

    //거래개실(예정)일
    private String openDate;

    // 유통형태
    private String distributionType;

    // 월매출액
    private String monthlySales;

    // 종업원 수
    private Integer employeeCount;

    // 차량대 수
    private Integer carCount;

    // 월예상매입/매출액
    private String mothlyExpectedAmount;

    // 보증/담보사항
    private String guarantee;

    //사업자등록증 사본
    private Integer licenseImg;

    //통장사본
    private Integer bankbookImg;

    //공급계약서
    private Integer supplyContract;

    //영업신고증
    private Integer salesReport;

    //공급사담당자명
    private String supplyManagerName;

    //공급사 담당자 연락처
    private String supplyManagerTel;

}
