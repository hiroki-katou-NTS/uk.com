package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums;

/**
 * @author thanhpv
 * 継続区分
 */
public enum DurationFlg {

	RETIREMENT (0, "退職"),
	
	CONTINUED (1, "継続");
	
	public int value;
	
	public String name;

	DurationFlg(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
}
