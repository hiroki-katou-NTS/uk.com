package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums;

/**
 * @author thanhpv
 * 継続区分
 */
public enum DurationFlg {

	RETIREMENT (0, "退職"),
	
	CONTINUED (1, "継続");
	
	public int value;
	
	public String nameId;

	DurationFlg(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
