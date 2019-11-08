
package com.kurly.wms.message.repository;

import com.kurly.wms.message.domain.WmsSupplierIf;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository {

	/* 파트너 인터페이스 등록 여부 확인 */
	int checkPartner(WmsSupplierIf iFDto) throws Exception;

	/* 파트너 인터페이스 등록 */
	void addPartner(WmsSupplierIf iFDto) throws Exception;

	/* 파트너 인터페이스 수정 */
	void modPartner(WmsSupplierIf iFDto) throws Exception;

}
