package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

import lombok.AllArgsConstructor;

/**
 * 繰り返し内容項目
 */
@AllArgsConstructor
public enum RepeatContentItem {
	/* 毎日 */
	DAILY(0, "毎日"),
	
	/* 毎週 */
	WEEKLY(1, "毎週"),
	
	/* 毎月 */
	MONTHLY(2, "毎月");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
