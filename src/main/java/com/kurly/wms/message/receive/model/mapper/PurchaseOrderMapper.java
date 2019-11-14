package com.kurly.wms.message.receive.model.mapper;

import com.kurly.wms.message.domain.MdOrderDocumentDto;
import com.kurly.wms.message.domain.WmsSupplierIf;
import com.kurly.wms.message.receive.model.PurchaseOrder;
import com.kurly.wms.message.receive.model.PurchaseOrderItem;
import com.kurly.wms.message.receive.model.Supplier;
import in.ashwanthkumar.utils.lang.StringUtils;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderMapper {
    public MdOrderDocumentDto createInstance(PurchaseOrder purchaseOrder) {
        MdOrderDocumentDto mdOrderDocumentDto = new MdOrderDocumentDto();
        mdOrderDocumentDto.setOrder_code(purchaseOrder.getPurchaseOrderCode());
        mdOrderDocumentDto.setSupply_code(purchaseOrder.getSupplierCode());
        mdOrderDocumentDto.setCenter_code("WH1");
        mdOrderDocumentDto.setWarehouse_plan_date(purchaseOrder.getReceivingEstimateDate());
        return mdOrderDocumentDto;
    }

    public MdOrderDocumentDto createInstanceItem(MdOrderDocumentDto mdOrderDocumentDto, PurchaseOrderItem purchaseOrderItem) {
        mdOrderDocumentDto.setOrder_sub_code(purchaseOrderItem.getPurchaseOrderItemSeq().toString());
        mdOrderDocumentDto.setGoods_code(purchaseOrderItem.getGoodsCode());
        mdOrderDocumentDto.setGoods_sub_code(purchaseOrderItem.getGoodsSubCode());
        mdOrderDocumentDto.setGoods_qnty(purchaseOrderItem.getTotalQnty().toString());
        mdOrderDocumentDto.setRelease_code(convertReleaseCode(purchaseOrderItem.getReleaseCode()));
        mdOrderDocumentDto.setPickup_address(purchaseOrderItem.getReleaseAddress());
        mdOrderDocumentDto.setPickup_detail_address(purchaseOrderItem.getReleaseDetailAddress());
        mdOrderDocumentDto.setKeep_level(purchaseOrderItem.getKeepLevel());
        mdOrderDocumentDto.setPickup_note(purchaseOrderItem.getRemarks());
        mdOrderDocumentDto.setShelf_life_date(convertNumber(purchaseOrderItem.getExpirationDate()));
        mdOrderDocumentDto.setProduce_life_date(convertNumber(purchaseOrderItem.getManufactureDate()));
        mdOrderDocumentDto.setSelling_type("STORE_FIRST".equals(purchaseOrderItem.getSellingType()) ? "02" : "01");
        mdOrderDocumentDto.setSupply_price(null); // TODO :: 확인하기
        return mdOrderDocumentDto;
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
