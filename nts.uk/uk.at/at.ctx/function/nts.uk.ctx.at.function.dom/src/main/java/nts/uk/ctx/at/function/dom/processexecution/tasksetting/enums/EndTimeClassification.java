package nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums;

import lombok.AllArgsConstructor;

/**
 * 実行タスク終了時刻ありなし区分
 */
@AllArgsConstructor
public enum EndTimeClassification {
	/* なし */
	NONE(0),
	
	/* あり */
	YES(1);
	
	/** The value. */
	public final int value;
}
