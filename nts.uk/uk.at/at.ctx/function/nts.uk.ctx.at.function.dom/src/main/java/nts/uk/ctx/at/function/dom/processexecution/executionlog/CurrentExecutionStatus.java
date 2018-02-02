package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;

/**
 * 更新処理自動実行項目の状態
 */
@AllArgsConstructor
public enum CurrentExecutionStatus {
	/* 実行中 */
	RUNNING(0, "実行中"),
	
	/* 待機中 */
	WAITING(1, "待機中"),
	
	/* 無効 */
	INVALID(2, "無効");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
