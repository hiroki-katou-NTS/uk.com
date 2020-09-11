package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

import lombok.AllArgsConstructor;

/**
 * 1日の繰り返し間隔ありなし区分
 */
@AllArgsConstructor
public enum OneDayRepeatClassification {
	/* 10分 */
	MINUTE_10(0),
	
	/* 15分 */
	MINUTE_15(1),
	
	/* 20分 */
	MINUTE_20(2),
	
	/* 30分 */
	MINUTE_30(3),
	
	/* 60分 */
	MINUTE_60(4);
	
	/** The value. */
	public final int value;
}
