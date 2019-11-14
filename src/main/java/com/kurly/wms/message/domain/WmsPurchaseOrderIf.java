
package com.kurly.wms.message.domain;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class WmsPurchaseOrderIf {

    public enum Day {
        MONDAY("월요일"), TUESDAY("화요일"), WEDNESDAY("수요일"), THURSDAY("목요일"), FRIDAY("금요일"), SATURDAY("토요일"), SUNDAY("일요일");

        private String day;

        Day(String day) {
            this.day = day;
        }

        public String whatsDay() {
            return this.day;
        }
    }

    private String http_status;
    private long row_number;
    private int total_cnt;
    private String user_seq;
    private String user_id;

    /* OPS 발주 */
    private String order_idx;                    // PK
    private String block_code;                // 상품 블록코드
    private String md_code;                    // MD코드
    private String md_name;                    // MD코드
    private String balju_yn;                    // OPS 발주 성공 여부
    private String[] ops_order_idxs;            // OPS 발주 IDX 배열
    private String ops_order_idx;                // OPS 발주 IDX
    private String start_date;                    // 조건검색 시작일
    private String end_date;                    // 조건검색 종료일
    private String reg_date2;                    // yyyy-mm-dd 날짜 검색

    /* MD 발주관리(리스트) Transfer */
    private String goods_name;                    // 상품명
    private String goods_sub_name;                // 상품명
    private String goods_unit;                    // 단위
    private String lead_time;                    // 리드타임
    private String moq;                        // 최소발주수량
    private String supply_nm;                    // 공급사명
    private String contact_nm;                    // 담당자명
    private String box_per_inqnty;                // 박스당 입수

    /* MD 발주 Transfer */
    private String employee_code;            // 사원코드(md코드로 활용)
    private String supply_id;
    private String supply_idx;
    private String[] goods_idxs;                    // 상품 PK
    private String[] order_sub_codes;            // 배열 : 발주순번코드
    private String[] supply_codes;                // 공급사 코드
    private String[] goods_codes;
    private String[] goods_sub_codes;
    private String[] md_codes;                    // MD코드
    private String[] center_codes;
    private String[] goods_qntys;
    private String[] goods_stnds;
    private String[] supply_prices;
    private String[] delivery_prices;
    private String[] box_per_inqntys;                // 박스당 입수
    private String[] warehouse_units;
    private String[] warehouse_plan_dates;
    private String[] produce_life_dates;
    private String[] shelf_life_dates;
    private String[] non_warehouse_reasons;    //미입고사유
    private String[] release_dates;                //출고일
    private String[] release_codes;                //출고방법
    private String[] pickup_times;                    //픽업시간
    private String[] pickup_addresss;                //픽업주소
    private String[] pickup_detail_addresss;    //상세주소
    private String[] pickup_notes;                    //픽업유의사항
    private String[] release_details;                //출고상태
    private String[] release_etcs;                    //출고기타
    private String[] order_descs;                    //비고
    private String[] shipping_days;                    //배송기일
    private String[] keep_levels;

    /**
     * DB : ESCM_SUPPLY_ORDER
     */
    private String[] order_idxs;
    private String order_code;
    private String order_sub_code;
    private String order_code_number;
    private String supply_code;
    private String goods_code;
    private String goods_sub_code;
    private String center_code;
    private String goods_qnty;
    private String goods_desc;
    private String qnty;
    private String goods_stnd;
    private String goods_taxation;
    private String selling_type;
    private String supply_price;
    private String delivery_price;
    private String release_code;
    private String release_code1;
    private String release_detail;
    private String release_etc;
    private String release_date;
    private String release_address;
    private String release_detail_address;
    private String shipping_day;
    private String status;
    private int count_unit;
    private String warehouse_unit;
    private String warehouse_plan_date;
    private String warehouse_plan_date_from;
    private String warehouse_plan_date_to;
    private String produce_life_date;
    private String shelf_life_date;
    private String warehouse_poss_yn;
    private String non_warehouse_reason;
    private String keep_level;
    private String order_desc;
    private String reg_id;
    private String reg_nm;
    private String reg_date;
    private String reg_date_from;
    private String reg_date_to;
    private String udt_id;
    private String udt_date;
    private String order_cnt;
    private String if_yn;                                    // 인터페이스 성공여부
    private String supply_desc;
    private String order_info_list;
    private int send_mail_cnt;

    /**
     * DB : ESCM_SUPPLY_ORDER_PICKUP
     */
    private String goods_idx;                            // 상품 PK
    private String goods_cost;                            // 공급원가
    private String pickup_time;                        // 픽업시간
    private String pickup_address;                        // 픽업주소
    private String pickup_detail_address;                // 픽업상세주소
    private String pickup_jibeon_address;                //	픽업주소(지번)
    private String pickup_doro_address;                //	픽업주소(도로)
    private String pickup_note;                        // 픽업유의사항


    private String mode;
    private String excel_down_type;
    private String menu_type;                            // 메뉴 종류
    private String supply_contact_code;
    private String supply_contact_nm;
    private String orderType;                            // 정렬종류(내림 or 오름)
    private String sortType;                            // 정렬대상(항목)
    private String limitSize;                            // 페이지 목록 개수 설정
    private ArrayList<WmsPurchaseOrderIf> orderInfoList;

    private String order_info;                            //발주정보(발주번호, 공급사명)

    /**
     * DB INTERFACE : WMS
     */
    private int seqno;                                    // 일련번호

	public void setHttp_status(String http_status) {
		this.http_status = http_status;
	}

	public void setRow_number(long row_number) {
		this.row_number = row_number;
	}

	public void setTotal_cnt(int total_cnt) {
		this.total_cnt = total_cnt;
	}

	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setOrder_idx(String order_idx) {
		this.order_idx = order_idx;
	}

	public void setBlock_code(String block_code) {
		this.block_code = block_code;
	}

	public void setMd_code(String md_code) {
		this.md_code = md_code;
	}

	public void setMd_name(String md_name) {
		this.md_name = md_name;
	}

	public void setBalju_yn(String balju_yn) {
		this.balju_yn = balju_yn;
	}

	public void setOps_order_idxs(String[] ops_order_idxs) {
		this.ops_order_idxs = ops_order_idxs;
	}

	public void setOps_order_idx(String ops_order_idx) {
		this.ops_order_idx = ops_order_idx;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setReg_date2(String reg_date2) {
		this.reg_date2 = reg_date2;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public void setGoods_sub_name(String goods_sub_name) {
		this.goods_sub_name = goods_sub_name;
	}

	public void setGoods_unit(String goods_unit) {
		this.goods_unit = goods_unit;
	}

	public void setLead_time(String lead_time) {
		this.lead_time = lead_time;
	}

	public void setMoq(String moq) {
		this.moq = moq;
	}

	public void setSupply_nm(String supply_nm) {
		this.supply_nm = supply_nm;
	}

	public void setContact_nm(String contact_nm) {
		this.contact_nm = contact_nm;
	}

	public void setBox_per_inqnty(String box_per_inqnty) {
		this.box_per_inqnty = box_per_inqnty;
	}

	public void setEmployee_code(String employee_code) {
		this.employee_code = employee_code;
	}

	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}

	public void setSupply_idx(String supply_idx) {
		this.supply_idx = supply_idx;
	}

	public void setGoods_idxs(String[] goods_idxs) {
		this.goods_idxs = goods_idxs;
	}

	public void setOrder_sub_codes(String[] order_sub_codes) {
		this.order_sub_codes = order_sub_codes;
	}

	public void setSupply_codes(String[] supply_codes) {
		this.supply_codes = supply_codes;
	}

	public void setGoods_codes(String[] goods_codes) {
		this.goods_codes = goods_codes;
	}

	public void setGoods_sub_codes(String[] goods_sub_codes) {
		this.goods_sub_codes = goods_sub_codes;
	}

	public void setMd_codes(String[] md_codes) {
		this.md_codes = md_codes;
	}

	public void setCenter_codes(String[] center_codes) {
		this.center_codes = center_codes;
	}

	public void setGoods_qntys(String[] goods_qntys) {
		this.goods_qntys = goods_qntys;
	}

	public void setGoods_stnds(String[] goods_stnds) {
		this.goods_stnds = goods_stnds;
	}

	public void setSupply_prices(String[] supply_prices) {
		this.supply_prices = supply_prices;
	}

	public void setDelivery_prices(String[] delivery_prices) {
		this.delivery_prices = delivery_prices;
	}

	public void setBox_per_inqntys(String[] box_per_inqntys) {
		this.box_per_inqntys = box_per_inqntys;
	}

	public void setWarehouse_units(String[] warehouse_units) {
		this.warehouse_units = warehouse_units;
	}

	public void setWarehouse_plan_dates(String[] warehouse_plan_dates) {
		this.warehouse_plan_dates = warehouse_plan_dates;
	}

	public void setProduce_life_dates(String[] produce_life_dates) {
		this.produce_life_dates = produce_life_dates;
	}

	public void setShelf_life_dates(String[] shelf_life_dates) {
		this.shelf_life_dates = shelf_life_dates;
	}

	public void setNon_warehouse_reasons(String[] non_warehouse_reasons) {
		this.non_warehouse_reasons = non_warehouse_reasons;
	}

	public void setRelease_dates(String[] release_dates) {
		this.release_dates = release_dates;
	}

	public void setRelease_codes(String[] release_codes) {
		this.release_codes = release_codes;
	}

	public void setPickup_times(String[] pickup_times) {
		this.pickup_times = pickup_times;
	}

	public void setPickup_addresss(String[] pickup_addresss) {
		this.pickup_addresss = pickup_addresss;
	}

	public void setPickup_detail_addresss(String[] pickup_detail_addresss) {
		this.pickup_detail_addresss = pickup_detail_addresss;
	}

	public void setPickup_notes(String[] pickup_notes) {
		this.pickup_notes = pickup_notes;
	}

	public void setRelease_details(String[] release_details) {
		this.release_details = release_details;
	}

	public void setRelease_etcs(String[] release_etcs) {
		this.release_etcs = release_etcs;
	}

	public void setOrder_descs(String[] order_descs) {
		this.order_descs = order_descs;
	}

	public void setShipping_days(String[] shipping_days) {
		this.shipping_days = shipping_days;
	}

	public void setKeep_levels(String[] keep_levels) {
		this.keep_levels = keep_levels;
	}

	public void setOrder_idxs(String[] order_idxs) {
		this.order_idxs = order_idxs;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public void setOrder_sub_code(String order_sub_code) {
		this.order_sub_code = order_sub_code;
	}

	public void setOrder_code_number(String order_code_number) {
		this.order_code_number = order_code_number;
	}

	public void setSupply_code(String supply_code) {
		this.supply_code = supply_code;
	}

	public void setGoods_code(String goods_code) {
		this.goods_code = goods_code;
	}

	public void setGoods_sub_code(String goods_sub_code) {
		this.goods_sub_code = goods_sub_code;
	}

	public void setCenter_code(String center_code) {
		this.center_code = center_code;
	}

	public void setGoods_qnty(String goods_qnty) {
		this.goods_qnty = goods_qnty;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public void setQnty(String qnty) {
		this.qnty = qnty;
	}

	public void setGoods_stnd(String goods_stnd) {
		this.goods_stnd = goods_stnd;
	}

	public void setGoods_taxation(String goods_taxation) {
		this.goods_taxation = goods_taxation;
	}

	public void setSelling_type(String selling_type) {
		this.selling_type = selling_type;
	}

	public void setSupply_price(String supply_price) {
		this.supply_price = supply_price;
	}

	public void setDelivery_price(String delivery_price) {
		this.delivery_price = delivery_price;
	}

	public void setRelease_code(String release_code) {
		this.release_code = release_code;
	}

	public void setRelease_code1(String release_code1) {
		this.release_code1 = release_code1;
	}

	public void setRelease_detail(String release_detail) {
		this.release_detail = release_detail;
	}

	public void setRelease_etc(String release_etc) {
		this.release_etc = release_etc;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public void setRelease_address(String release_address) {
		this.release_address = release_address;
	}

	public void setRelease_detail_address(String release_detail_address) {
		this.release_detail_address = release_detail_address;
	}

	public void setShipping_day(String shipping_day) {
		this.shipping_day = shipping_day;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCount_unit(int count_unit) {
		this.count_unit = count_unit;
	}

	public void setWarehouse_unit(String warehouse_unit) {
		this.warehouse_unit = warehouse_unit;
	}

	public void setWarehouse_plan_date(String warehouse_plan_date) {
		this.warehouse_plan_date = warehouse_plan_date;
	}

	public void setWarehouse_plan_date_from(String warehouse_plan_date_from) {
		this.warehouse_plan_date_from = warehouse_plan_date_from;
	}

	public void setWarehouse_plan_date_to(String warehouse_plan_date_to) {
		this.warehouse_plan_date_to = warehouse_plan_date_to;
	}

	public void setProduce_life_date(String produce_life_date) {
		this.produce_life_date = produce_life_date;
	}

	public void setShelf_life_date(String shelf_life_date) {
		this.shelf_life_date = shelf_life_date;
	}

	public void setWarehouse_poss_yn(String warehouse_poss_yn) {
		this.warehouse_poss_yn = warehouse_poss_yn;
	}

	public void setNon_warehouse_reason(String non_warehouse_reason) {
		this.non_warehouse_reason = non_warehouse_reason;
	}

	public void setKeep_level(String keep_level) {
		this.keep_level = keep_level;
	}

	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public void setReg_date_from(String reg_date_from) {
		this.reg_date_from = reg_date_from;
	}

	public void setReg_date_to(String reg_date_to) {
		this.reg_date_to = reg_date_to;
	}

	public void setUdt_id(String udt_id) {
		this.udt_id = udt_id;
	}

	public void setUdt_date(String udt_date) {
		this.udt_date = udt_date;
	}

	public void setOrder_cnt(String order_cnt) {
		this.order_cnt = order_cnt;
	}

	public void setIf_yn(String if_yn) {
		this.if_yn = if_yn;
	}

	public void setSupply_desc(String supply_desc) {
		this.supply_desc = supply_desc;
	}

	public void setOrder_info_list(String order_info_list) {
		this.order_info_list = order_info_list;
	}

	public void setSend_mail_cnt(int send_mail_cnt) {
		this.send_mail_cnt = send_mail_cnt;
	}

	public void setGoods_idx(String goods_idx) {
		this.goods_idx = goods_idx;
	}

	public void setGoods_cost(String goods_cost) {
		this.goods_cost = goods_cost;
	}

	public void setPickup_time(String pickup_time) {
		this.pickup_time = pickup_time;
	}

	public void setPickup_address(String pickup_address) {
		this.pickup_address = pickup_address;
	}

	public void setPickup_detail_address(String pickup_detail_address) {
		this.pickup_detail_address = pickup_detail_address;
	}

	public void setPickup_jibeon_address(String pickup_jibeon_address) {
		this.pickup_jibeon_address = pickup_jibeon_address;
	}

	public void setPickup_doro_address(String pickup_doro_address) {
		this.pickup_doro_address = pickup_doro_address;
	}

	public void setPickup_note(String pickup_note) {
		this.pickup_note = pickup_note;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setExcel_down_type(String excel_down_type) {
		this.excel_down_type = excel_down_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public void setSupply_contact_code(String supply_contact_code) {
		this.supply_contact_code = supply_contact_code;
	}

	public void setSupply_contact_nm(String supply_contact_nm) {
		this.supply_contact_nm = supply_contact_nm;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public void setLimitSize(String limitSize) {
		this.limitSize = limitSize;
	}

	public void setOrderInfoList(ArrayList<WmsPurchaseOrderIf> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}

	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
}
