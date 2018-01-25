package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

import lombok.AllArgsConstructor;

/**
 * 繰り返し内容項目
 */
@AllArgsConstructor
public enum RepeatContentItem {
	/* 毎日 */
	DAILY(0),
	
	/* 毎週 */
	WEEKLY(1),
	
	/* 毎月 */
	MONTHLY(2);
	
	/** The value. */
	public final int value;
}
