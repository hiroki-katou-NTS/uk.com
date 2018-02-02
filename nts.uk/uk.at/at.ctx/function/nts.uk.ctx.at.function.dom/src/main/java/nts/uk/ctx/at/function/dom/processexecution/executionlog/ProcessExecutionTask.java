package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;

/**
 * 更新処理自動実行項目
 */
@AllArgsConstructor
public enum ProcessExecutionTask {
	/* スケジュールの作成 */
	SCH_CREATION(0, "スケジュールの作成"),
	
	/* 日別作成 */
	DAILY_CREATION(1, "日別作成"),
	
	/* 日別計算 */
	DAILY_CALCULATION(2, "日別計算"),
	
	/* 承認結果反映 */
	RFL_APR_RESULT(3, "承認結果反映"),
	
	/* 月別集計 */
	MONTHLY_AGGR(4, "月別集計"),
	
	/* アラーム抽出（個人別） */
	INDV_ALARM(5, "アラーム抽出（個人別）"),
	
	/* アラーム抽出（職場別） */
	WKP_ALARM(6, "アラーム抽出（職場別）");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
