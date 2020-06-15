package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

/**
 * 勤務実績のチェック項目の種類
 * @author tutk
 *
 */
public enum TypeCheckWorkRecord {
	/**	時間 */
	TIME(0,"時間"),
	/**	回数 */
	TIMES(1,"回数"),
	/**	金額 */
	AMOUNT_OF_MONEY(2,"金額"),
	/**	時刻 */
	TIME_OF_DAY(3,"時刻"),
	/**	連続時間 */
	CONTINUOUS_TIME(4,"連続時間"),
	/**	連続勤務 */
	CONTINUOUS_WORK(5,"連続勤務"),
	/**	連続時間帯 */
	CONTINUOUS_TIME_ZONE(6,"連続時間帯"),
	/**	複合条件 */
	CONTINUOUS_CONDITION(7,"複合条件");	
	
	public int value;
	
	public String nameId;
	
	private TypeCheckWorkRecord (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
