package com.kurly.wms.message.receive.model.mapper;

import com.kurly.wms.message.domain.WmsPurchaseOrderIf;
import com.kurly.wms.message.receive.model.purchase.PurchaseOrder;
import com.kurly.wms.message.receive.model.purchase.PurchaseOrderItem;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderMapper {
    public WmsPurchaseOrderIf createInstance(PurchaseOrder purchaseOrder) {
        WmsPurchaseOrderIf purchaseOrderIf = new WmsPurchaseOrderIf();
        purchaseOrderIf.setOrder_code(purchaseOrder.getPurchaseOrderCode());
        purchaseOrderIf.setSupply_code(purchaseOrder.getSupplierCode());
        purchaseOrderIf.setCenter_code("WH1");
        purchaseOrderIf.setWarehouse_plan_date(purchaseOrder.getReceivingEstimateDate());
        return purchaseOrderIf;
    }

    public WmsPurchaseOrderIf createInstanceItem(WmsPurchaseOrderIf wmsPurchaseOrderIf, PurchaseOrderItem purchaseOrderItem) {
        wmsPurchaseOrderIf.setOrder_sub_code(purchaseOrderItem.getPurchaseOrderItemSeq().toString());
        wmsPurchaseOrderIf.setGoods_code(purchaseOrderItem.getGoodsCode());
        wmsPurchaseOrderIf.setGoods_sub_code(purchaseOrderItem.getGoodsSubCode());
        wmsPurchaseOrderIf.setGoods_qnty(purchaseOrderItem.getTotalQnty().toString());
        wmsPurchaseOrderIf.setRelease_code(convertReleaseCode(purchaseOrderItem.getReleaseCode()));
        wmsPurchaseOrderIf.setPickup_address(purchaseOrderItem.getReleaseAddress());
        wmsPurchaseOrderIf.setPickup_detail_address(purchaseOrderItem.getReleaseDetailAddress());
        wmsPurchaseOrderIf.setKeep_level(purchaseOrderItem.getKeepLevel());
        wmsPurchaseOrderIf.setPickup_note(purchaseOrderItem.getRemarks());
        wmsPurchaseOrderIf.setShelf_life_date(convertNumber(purchaseOrderItem.getExpirationDate()));
        wmsPurchaseOrderIf.setProduce_life_date(convertNumber(purchaseOrderItem.getManufactureDate()));
        wmsPurchaseOrderIf.setSelling_type("STORE_FIRST".equals(purchaseOrderItem.getSellingType()) ? "02" : "01");
        wmsPurchaseOrderIf.setSupply_price(null); // TODO :: 확인하기
        return wmsPurchaseOrderIf;
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
    
    private static String convertNumber(String source) {
        return source == null ? null : source.replaceAll("[^0-9]", "");
    }
    
    

}
