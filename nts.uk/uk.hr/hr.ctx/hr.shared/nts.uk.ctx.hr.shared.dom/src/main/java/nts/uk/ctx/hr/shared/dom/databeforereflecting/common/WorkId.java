package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

/**
 * @author thanhpv
 * 業務ID
 */
public enum WorkId {
	
	REGISTERING_RETIREES(1, "退職者の登録"),

	REGISTRATION_OF_RETIRED_EMPLOYEES(2, "定年退職者の登録"),
	
	FIXED_TERM_CONTRACTOR_INFORMATION(3, "有期雇用契約者情報"),
	
	DEPARTMENT_TRANSFER_INFORMATION(4, "部門異動者情報"),
	
	PROMOTION_INFORMATION(5, "昇進・昇格者情報"),
	
	TEMPORARY_DISPATCH_INFORMATION(6, "出向派遣者情報"),
	
	TRANSFEREE_INFORMATION(7, "転籍者情報"),
	
	QUALIFICATION_HOLDER_INFORMATION(8, "資格取得者情報");

	public int value;
	
	public String nameId;

	WorkId(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
