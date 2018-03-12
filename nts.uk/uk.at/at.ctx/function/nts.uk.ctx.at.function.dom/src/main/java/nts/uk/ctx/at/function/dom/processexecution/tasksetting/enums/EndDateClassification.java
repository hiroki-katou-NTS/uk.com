package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

import lombok.AllArgsConstructor;

/**
 * 実行タスク終了日ありなし区分
 */
@AllArgsConstructor
public enum EndDateClassification {
	/* なし */
	NONE(0),
	
	/* 日付指定 */
	DATE(1);
	
	/** The value. */
	public final int value;
}
