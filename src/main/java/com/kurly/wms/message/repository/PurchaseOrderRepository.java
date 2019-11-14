
package com.kurly.wms.message.repository;

import com.kurly.wms.message.domain.MdOrderDocumentDto;
import com.kurly.wms.message.domain.WmsSupplierIf;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface PurchaseOrderRepository {

	/* 발주서등록 인터페이스 등록 */
	void savePurchaseOrder(MdOrderDocumentDto wmsPurchaseOrderIf) throws SQLException;


}
