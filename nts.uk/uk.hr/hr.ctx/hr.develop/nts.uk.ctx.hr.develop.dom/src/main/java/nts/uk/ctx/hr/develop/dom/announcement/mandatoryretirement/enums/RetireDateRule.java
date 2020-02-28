package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums;

/**
 * @author thanhpv
 * 退職日基準
 */
public enum RetireDateRule {

	THE_DAY_OF_REACHING_RETIREMENT_AGE (0,"定年年齢に達した日"),
	
	RETIREMENT_DATE_DESIGNATED_DATE (1,"退職日指定日"),
	
	THE_LAST_DAY_OF_THE_MONTH_INCLUDING_THE_DAY_OF_REACHING_RETIREMENT_AGE (2,"定年年齢に達した日を含む月の末日"),
	
	THE_LAST_DAY_OF_THE_YEAR_INCLUDING_THE_DAY_OF_REACHING_RETIREMENT_AGE (3,"定年年齢に達した日を含む年度の末日"),
	
	FIRST_WAGE_CLOSING_DATE_AFTER_THE_DATE_OF_RETIREMENT_AGE (4,"定年年齢に達した日以後の最初の賃金締め日"),
	
	FIRST_DUE_DATE_AFTER_REACHING_RETIREMENT_AGE (5,"定年年齢に達した日以後の最初の勤怠締め日 ");
	
	public int value;
	
	public String nameId;

	RetireDateRule(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
