
package com.kurly.wms.message.repository;

import com.kurly.wms.message.domain.WmsPurchaseOrderIf;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface PurchaseOrderRepository {

	/* 발주서등록 인터페이스 등록 */
	void savePurchaseOrder(WmsPurchaseOrderIf wmsPurchaseOrderIf) throws SQLException;


}
