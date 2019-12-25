package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums;

/**
 * @author thanhpv
 * 年齢到達条件
 */
public enum ReachedAgeTerm {

	THE_DAY_BEFORE_THE_BIRTHDAY (0,"誕生日の前日"),
	
	ON_THE_DAY_OF_BIRTHDAY (1, "誕生日の当日");
	
	public int value;
	
	public String name;

	ReachedAgeTerm(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
}
