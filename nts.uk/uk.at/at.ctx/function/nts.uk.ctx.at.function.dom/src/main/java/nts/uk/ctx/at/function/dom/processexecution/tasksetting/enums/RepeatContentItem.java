package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

/**
 * 繰り返し内容項目
 */
public enum RepeatContentItem {
	/* 毎日 */
	DAILY(0),
	
	/* 毎週 */
	WEEKLY(1),
	
	/* 毎月 */
	MONTHLY(2);
	
	/** The value. */
	public final int value;
	private RepeatContentItem(int type) {
		this.value = type;
	}
}
