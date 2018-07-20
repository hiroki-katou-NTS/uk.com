package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

/*上限日数の設定方法*/
public enum MaxNumberDayType {
	/* 固定日数を上限とする */
	LIMIT_FIXED_DAY(1),
	/* 続柄ごとに上限を設定する */
	REFER_RELATIONSHIP(2);

	public int value;

	MaxNumberDayType(int type) {
		this.value = type;
	}
}
