package com.kurly.wms.message.receive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Goods implements Serializable {

    private Integer goodsId;

    //상품코드
    private String goodsCode;

    //상품명
    private String goodsName;

    //공급사아이디
    private Integer supplierId;

    //상품 사용여부
    private Boolean useYn;

    //대분류
    private String mainCategoryCode;

    //중분류
    private String middleCategoryCode;

    //판매방법
    private String sellingType;

    //출고방법
    private String releaseCode;

    //출고주소
    private String releaseAddress;

    //출고상세주소
    private String releaseDetailAddress;

    //판매가능일수
    private Integer sellAvailableDate;

    //판매마감일수
    private Integer sellDeadlineDate;

    //샛별오더유형
    private String deliveryType;

    //택배오더유형
    private String deliveryDocumentNum;

    //대량오더유형
    private String massDocumentNum;

    //과면세
    private String taxation;

    //유통가능일수
    private Integer availableDays;

    //보관유형
    private String keepLevel;

    //리드타임
    private Integer leadTime;

    //최소주문수량
    private Integer moq;

    //단위
    private String unit;

    //박스당 입고수량
    private Integer boxPerQnty;

    //순 중량
    private BigDecimal netWeight;

    //총 중량
    private BigDecimal grossWeight;

    //가로
    private BigDecimal width;

    //세로
    private BigDecimal length;

    //높이
    private BigDecimal height;

    //CBM
    private BigDecimal cbm;

    //박스 순 중량
    private BigDecimal boxNetWeight;

    //박스 총 중량
    private BigDecimal boxGrossWeight;

    //박스 가로
    private BigDecimal boxWidth;

    //박스 세로
    private BigDecimal boxLength;

    //박스 높이
    private BigDecimal boxHeight;

    //박스 CBM
    private BigDecimal boxCbm;

    //비고
    private String remarks;

    //대체코드(88코드)
    private String alternativeCode;

    //대체상품명
    private String goodsSubName;

    //대체상품코드
    private String goodsSubCode;

    /*센터 코드*/
    private String center;

    /*창고 코드*/
    private String warehouse;

    /*적치존 코드*/
    private String mountingZone;

    /* 검품대상여부 */
    private Boolean inspection;

    /* 상품이미지 full 주소 */
    private String goodsImageUrl;

    /*적치존 관련 id값*/
    private Integer goodsReceivingId;
}
