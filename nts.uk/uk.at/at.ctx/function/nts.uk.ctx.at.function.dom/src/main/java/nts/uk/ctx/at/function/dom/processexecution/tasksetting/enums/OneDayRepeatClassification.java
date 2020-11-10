package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

import lombok.AllArgsConstructor;

/**
 * 1日の繰り返し間隔ありなし区分
 */
@AllArgsConstructor
public enum OneDayRepeatClassification {
	NONE(0),
	YES(1);
	
	/** The value. */
	public final int value;
}
