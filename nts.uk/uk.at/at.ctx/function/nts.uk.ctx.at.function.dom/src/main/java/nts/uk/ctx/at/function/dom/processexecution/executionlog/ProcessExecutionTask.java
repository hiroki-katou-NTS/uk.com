package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;

/**
 * 更新処理自動実行項目
 */
@AllArgsConstructor
public enum ProcessExecutionTask {
	/* アラーム抽出 */
	AL_EXTRACTION(0, "アラーム抽出"),

	/* インデックス再構成*/
	INDEX_RECUNSTRUCTION(1, "インデックス再構成"),
	
	/* スケジュールの作成 */
	SCH_CREATION(2, "スケジュールの作成"),
	
	/* データの保存 */
	SAVE_DATA(3, "データの保存"),
	
	/* データの削除 */
	DELETE_DATA(4, "データの削除"),
	
	/* 任意期間の集計 */
	AGGREGATION_OF_ARBITRARY_PERIOD(5, "任意期間の集計"),
	
	/* 外部出力 */
	EXTERNAL_OUTPUT(6, "外部出力"),
	
	/* 外部受入 */
	EXTERNAL_ACCEPTANCE(7, "外部受入"),
	
	/* 承認ルート更新（日次） */
	APP_ROUTE_U_DAI(8, "承認ルート更新（日次）"),
	
	/* 承認ルート更新（月次） */
	APP_ROUTE_U_MON(9, "承認ルート更新（月次）"),
	
	/* 承認結果反映 */
	RFL_APR_RESULT(10, "承認結果反映"),
	
	/* 日別作成 */
	DAILY_CREATION(11, "日別作成"),
	
	/* 日別計算 */
	DAILY_CALCULATION(12, "日別計算"),
	
	/* 月別集計 */
	MONTHLY_AGGR(13, "月別集計");
	
//	/* アラーム抽出（個人別） */
//	INDV_ALARM(5, "アラーム抽出（個人別）"),
//	
//	/* アラーム抽出（職場別） */
//	WKP_ALARM(6, "アラーム抽出（職場別）");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
