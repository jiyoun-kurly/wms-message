package com.kurly.wms.message.receive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInterfaceMaster {

    private long seq;
    private String orderCode;
    private String orderId;
    private String memberGrade;
    private String blackMemberYn;
    private Date orderDate;
    private Date paymentDate;
    private String deliveryDate;
    private String orderName;
    private String orderEmail;
    private String orderTelNo;
    private String orderHpNo;
    private String receiverName;
    private String receiverTelNo;
    private String receiverHpNo;
    private String zipcode;
    private String areacode;
    private String address;
    private String addressDetail;
    private String roadAddress;
    private String bcode;
    private String hcode;
    private String orderAddrGugun;
    private String orderAddrDong;
    private String addrX;
    private String addrY;
    private String deliveryMessage;
    private String csMessage;
    private String entranceType;
    private String entrancePwd;
    private String boxPwd;
    private String deliveryStatus;
    private String orderStatus;
    private String deliveryType;
    private String deliverySmsType;
    private String oldOrderCode;
    private String ifStatus;
    private LocalDateTime createDate;
    private String userId;
    private LocalDateTime updateDate;
    private String updateId;
    private String courierCode;
    private Integer deliveryRound;
    private String regionGroupCode;
    private String regionCode;
    private List<OrderInterfaceItem> orderItems = new ArrayList<> (  );

    public boolean isCancelStatus() {
        return this.orderItems.get(0).getCancelQnty() > 0;
    }
}
