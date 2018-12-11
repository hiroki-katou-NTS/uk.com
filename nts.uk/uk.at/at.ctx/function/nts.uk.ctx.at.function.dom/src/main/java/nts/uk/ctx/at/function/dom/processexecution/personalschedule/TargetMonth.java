package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

/**
 * 更新処理対象月
 */
public enum TargetMonth {
	/* システム日付の月 */
	CURRENT_MONTH(0),
	
	/* システム日付の翌月 */
	MONTH_LATER(1),
	
	/* システム日付の翌々月 */
	TWO_MONTH_LATER(2),
	
	/* 開始月を指定する */
	DESIGNATE_START_MONTH(3);
	
	/** The value. */
	public final int value;
	private TargetMonth(int type) {
		this.value = type;
	}
	
}
