package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

/**
 * @author thanhpv
 * 業務ID
 */
public enum WorkId {
	
	REGISTERING_RETIREES(1, "退職者の登録"),

	REGISTRATION_OF_RETIRED_EMPLOYEES(2, "定年退職者の登録"),
	
	FIXED_TERM_CONTRACTOR_INFORMATION(3, "有期雇用契約者情報");

	public int value;
	
	public String nameId;

	WorkId(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
