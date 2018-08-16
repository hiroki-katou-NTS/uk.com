package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;
/* 年齢基準年区分 */
public enum AgeStandardType {
	//当年
	THIS_YEAR(1),
	//翌年
	NEXT_YEAR(2);
	
	public int value;

	AgeStandardType(int type) {
		this.value = type;
	}
}
