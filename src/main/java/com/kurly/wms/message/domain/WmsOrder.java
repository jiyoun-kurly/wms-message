package com.kurly.wms.message.domain;

import com.kurly.wms.message.receive.model.order.OrderInterfaceItem;
import com.kurly.wms.message.receive.model.order.OrderInterfaceMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WmsOrder {
    private String	mandt;
    private int		seqno;
    private String	vbeln;
    private String	posnr;
    private String	bwart;

    private String	pstyv;
    private String	zlikp_erdat;
    private String	zlikp_erzet;
    private String	wadat;
    private String	kunnr;

    private String	kunag;
    private String	werks;
    private String	bwtar;
    private String	stknum;
    private String	status;
    private String	matnr;

    private String	tdate;
    private String	ifflg;
    private int		retry;
    private String	ertxt;
    private int		lfimg;

    private String	meins;
    private String	waerk;
    private String	cusrid;
    private String	cuname;
    private String	cpstlz;

    private String	telf1;
    private String	tele2;
    private String	smtp_addr;
    private String	vtext;
    private String	addr;

    private String	cname;
    private String	cphon;
    private String	wareky;
    private String	skukey;
    private String	desc01;
    private String	desc02;

    private String	credat;
    private String	cretim;
    private String	lmodat;
    private String	lmotim;

    private String	c00101;
    private String	c00102;
    private String	c00103;
    private String	c00106;
    private String	c00104;
    private String	c00105;
    private String	c00107;
    private String	c00108;
    private String	c00109;
    private String	c00110;

    private String	deptid2;
    private String	usrid3;
    private String	deptid3;
    private String	usrid4;
    private String	deptid4;
    private String	kukla;
    private String	usrid1;

    private String base_address_type;
    private String order_addr_gugun;
    private String order_addr_dong;
    private String courier_code;

    private String region_group_code; //권역그룹코드
    private String region_code; //권역코드
    private String delivery_round; // 1: 선배차, 2: 새벽배송


    public static WmsOrder getNewOrder (OrderInterfaceMaster master) {
        OrderInterfaceItem item = master.getOrderItems().get(0);
        LocalDateTime now = LocalDateTime.now();

        WmsOrder order = new WmsOrder();
        order.setMandt("110");
        order.setVbeln(master.getOrderCode());
        order.setPosnr("001");
        order.setBwart(item.getOrderType());

        order.setPstyv(master.getMemberGrade());
        order.setZlikp_erdat(" ");
        order.setZlikp_erzet(" ");
        order.setWadat(master.getDeliveryDate());
        order.setKunnr(item.getSupplyCode());

        order.setKunag(master.getDeliveryType());
        order.setWerks("TF");
        order.setBwtar("GEN");
        order.setStknum(" ");
        order.setStatus(master.getOrderStatus());
        order.setMatnr(item.getGoodsCode());

        order.setTdate(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        order.setIfflg(master.getIfStatus());
        order.setRetry(item.getCancelQnty());
        order.setErtxt(" ");
        order.setLfimg(1);
        order.setMeins(item.getGoodsUnit());
        order.setWaerk(master.getDeliveryStatus());

        order.setCusrid(" ");
        order.setCuname(master.getOrderName());
        order.setCpstlz(master.getZipcode());

        order.setTelf1(master.getOrderTelNo());
        order.setTele2(master.getOrderHpNo());
        order.setSmtp_addr(" ");
        order.setVtext(master.getAddress());
        order.setAddr(master.getAddressDetail());

        order.setCname(master.getReceiverName());
        order.setCphon(master.getReceiverHpNo());
        order.setWareky("WH1");
        order.setSkukey(item.getGoodsSubCode());
        order.setDesc01(item.getGoodsName());
        order.setDesc02(item.getGoodsOptionName());

        order.setCredat(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        order.setCretim(now.format(DateTimeFormatter.ofPattern("HHmmss")));
        order.setLmodat(" ");
        order.setLmotim(" ");

        order.setC00101(" ");
        order.setC00102(" ");
        order.setC00103(master.getRoadAddress());
        order.setC00106(master.getAddressDetail());
        order.setC00104(master.getAreacode());
        order.setC00105(item.getInvoiceNo());
        order.setC00107(master.getAddrX());
        order.setC00108(master.getAddrY());
        order.setC00109(master.getBcode());
        order.setC00110(master.getHcode());

//        order.setUsrid3(master.getCsMessage());
//        order.setUsrid4(master.getBoxPwd());


        order.setDeptid4(master.getEntrancePwd());
        order.setDeptid3(master.getEntranceType());
        order.setDeptid2(master.getDeliveryMessage());
        order.setUsrid3(" ");
        order.setUsrid4(" ");
        order.setKukla(master.getDeliverySmsType());
        order.setUsrid1(" ");
//        order.setUsrid1(master.getOldOrderCode());

        order.setBase_address_type("J");
        order.setOrder_addr_gugun("");
        order.setOrder_addr_dong("");
        order.setCourier_code(master.getCourierCode());

        order.setRegion_group_code(master.getRegionGroupCode()); //권역그룹코드
        order.setRegion_code(master.getRegionCode()); //권역코드
        order.setDelivery_round(master.getDeliveryRound().toString()); // 1: 선배차, 2: 새벽배송

        return order;
    }



    public static WmsOrder getUpdateOrder (OrderInterfaceMaster master, OrderInterfaceItem item) {
        return WmsOrder.builder()
                .vbeln(master.getOrderCode())
                .desc02(item.getGoodsOptionName())
                .deptid2(master.getDeliveryMessage())
                .deptid3(master.getEntranceType())
                .deptid4(master.getEntrancePwd())
                .build();
    }

    public boolean isChanged (OrderInterfaceMaster master, OrderInterfaceItem item) {
        if (!item.getGoodsOptionName().equals(this.desc02)) {
            return true;
        }
        if (!master.getDeliveryMessage().equals(this.deptid2)) {
            return true;
        }
        if (!master.getEntranceType().equals(this.deptid3)) {
            return true;
        }
        if (!master.getEntrancePwd().equals(this.deptid4)) {
            return true;
        }
        return false;
    }
}
