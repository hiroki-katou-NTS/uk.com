package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 更新処理実行設定
 */
@Getter
@Setter
@AllArgsConstructor
public class ProcessExecutionSetting extends DomainObject {	
	/* アラーム抽出 */
	private AlarmExtraction alarmExtraction;
	
	/* 個人スケジュール作成 */
	private PersonalScheduleCreation perSchedule;
	
	/* 日別実績の作成・計算 */
	private DailyPerformanceCreation dailyPerf;
	
	/* 承認結果反映 */
	private boolean reflectResultCls;
	
	/* 月別集計 */
	private boolean monthlyAggCls;
	
	/* 承認ルート更新（日次） */
	private AppRouteUpdateDaily appRouteUpdateDaily;
	
	/* 承認ルート更新（月次） */
	private NotUseAtr appRouteUpdateMonthly;
	
	/* データの削除 */
	private DeleteData deleteData;
	
	/* データの保存 */
	private SaveData saveData;
	
	/* 外部受入 */
	private ExternalAcceptance externalAcceptance;
	
	/* 外部出力 */
	private ExternalOutput externalOutput;
	
	/* 任意期間の集計 */
	private AggregationOfArbitraryPeriod aggregationOfArbitraryPeriod;
	
	/* インデックス再構成 */
	private IndexReconstruction indexReconstruction;
}
