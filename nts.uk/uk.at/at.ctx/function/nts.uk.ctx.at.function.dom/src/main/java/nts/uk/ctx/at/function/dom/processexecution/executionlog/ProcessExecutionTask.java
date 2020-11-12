package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.AllArgsConstructor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.更新処理自動実行項目
 */
@AllArgsConstructor
public enum ProcessExecutionTask {
	
	/* 外部受入 */
	EXTERNAL_ACCEPTANCE(0, "外部受入"),
	
	/* スケジュールの作成 */
	SCH_CREATION(1, "スケジュールの作成"),
	
	/* 日別作成 */
	DAILY_CREATION(2, "日別作成"),

	/* 日別計算 */
	DAILY_CALCULATION(3, "日別計算"),

	/* 承認結果反映 */
	RFL_APR_RESULT(4, "承認結果反映"),

	/* 月別集計 */
	MONTHLY_AGGR(5, "月別集計"),
	
	/* 任意期間の集計 */
	AGGREGATION_OF_ARBITRARY_PERIOD(6, "任意期間の集計"),
	
	/* 外部出力 */
	EXTERNAL_OUTPUT(7, "外部出力"),
	
	/* アラーム抽出 */
	AL_EXTRACTION(8, "アラーム抽出"),

	/* 承認ルート更新（日次） */
	APP_ROUTE_U_DAI(9, "承認ルート更新（日次）"),
	
	/* 承認ルート更新（月次） */
	APP_ROUTE_U_MON(10, "承認ルート更新（月次）"),

	/* データの保存 */
	SAVE_DATA(11, "データの保存"),
	
	/* データの削除 */
	DELETE_DATA(12, "データの削除"),
	
	/* インデックス再構成*/
	INDEX_RECUNSTRUCTION(13, "インデックス再構成");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
