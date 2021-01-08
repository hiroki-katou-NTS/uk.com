package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

/**
 * 繰り返し内容項目
 */
public enum RepeatContentItem {
	
	/* 1回のみ */
	ONCE_TIME(0),
	
	/* 
	 * 毎週 */
	WEEKLY_DAYS(1),
	
	/* 
	 * 曜日指定 */
	SPECIFIED_WEEK_DAYS(2),
	
	/* 
	 * 月日指定 */
	SPECIFIED_IN_MONTH(3);
	
	/** The value. */
	public final int value;
	private RepeatContentItem(int type) {
		this.value = type;
	}
}
