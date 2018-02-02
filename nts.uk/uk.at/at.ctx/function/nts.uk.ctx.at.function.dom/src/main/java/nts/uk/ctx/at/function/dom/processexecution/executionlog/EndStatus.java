package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;

/**
 * 更新処理自動実行タスクの状態
 */
@AllArgsConstructor
public enum EndStatus {
	/* 未実施 */
	NOT_IMPLEMENT(0, "未実施"),
	
	/* 正常終了 */
	SUCCESS(1, "正常終了"),
	
	/* 強制終了 */
	FORCE_END(2, "強制終了"),
	
	/* 異常終了 */
	ABNORMAL_END(3, "異常終了");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
