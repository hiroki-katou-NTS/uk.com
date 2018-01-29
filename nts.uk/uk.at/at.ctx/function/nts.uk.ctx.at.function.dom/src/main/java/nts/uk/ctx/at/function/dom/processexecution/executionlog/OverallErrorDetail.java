package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;

/**
 * 全体のエラー詳細
 */
@AllArgsConstructor
public enum OverallErrorDetail {
	/* 更新処理の途中で終了時刻を超過したため中断しました。 */
	EXCEED_TIME(0, "更新処理の途中で終了時刻を超過したため中断しました。"),
	
	/* 前回の更新処理が完了していなかったため実行されませんでした。 */
	NOT_FINISHED(1, "前回の更新処理が完了していなかったため実行されませんでした。");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
