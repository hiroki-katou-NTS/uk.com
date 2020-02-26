package nts.uk.ctx.hr.shared.dom.enumeration;

/**
 * @author thanhpv
 * 日付基準
 */
public enum DateRule {

	UNSPECIFIED (0,"指定なし"),
	
	SAME_AS_TARGET_DATE (1,"対象日と同じ"),
	
	DAYS_BEFORE_THE_TARGET_DATE (2,"対象日より日数前"),
	
	MONTHS_BEFORE_THE_TARGET_DATE (3,"対象日より月数前"),
	
	DAYS_AFTER_THE_TARGET_DATE (4,"対象日より日数後"),
	
	MONTHS_AFTER_THE_TARGET_DATE (5,"対象日より月数後"),
	
	DESIGNATED_DATE (6, "指定日");
	
	public int value;
	
	public String nameId;

	DateRule(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
