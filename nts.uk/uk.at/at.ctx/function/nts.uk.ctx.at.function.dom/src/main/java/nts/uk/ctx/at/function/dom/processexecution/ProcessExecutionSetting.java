package nts.uk.ctx.at.function.dom.processexecution;

import lombok.*;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;

/**
 * The class Process execution setting.<br>
 * Domain 更新処理実行設定
 *
 * @author nws-minhnb
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessExecutionSetting extends DomainObject {

	/**
	 * The Alarm extraction.<br>
	 * アラーム抽出
	 */
	private AlarmExtraction alarmExtraction;

	/**
	 * The Personal schedule creation.<br>
	 * 個人スケジュール作成
	 */
	private PersonalScheduleCreation perScheduleCreation;

	/**
	 * The Create daily performance.<br>
	 * 日別実績の作成・計算
	 */
	private DailyPerformanceCreation dailyPerf;

	/**
	 * The Reflect approval result.<br>
	 * 承認結果の反映
	 */
	private ReflectionApprovalResult reflectAppResult;

	/**
	 * The Monthly aggregate.<br>
	 * 月別実績の集計
	 */
	private MonthlyAggregate monthlyAggregate;

	/**
	 * The Approval route update daily.<br>
	 * 承認ルート更新（日次）
	 */
	private AppRouteUpdateDaily appRouteUpdateDaily;

	/**
	 * The Approval route update monthly.<br>
	 * 承認ルート更新（月次）
	 */
	private AppRouteUpdateMonthly appRouteUpdateMonthly;

	/**
	 * The Delete data.<br>
	 * データの削除
	 **/
	private DeleteData deleteData;

	/**
	 * The Save data.<br>
	 * データの保存
	 **/
	private SaveData saveData;

	/**
	 * The External acceptance.<br>
	 * 外部受入
	 **/
	private ExternalAcceptance externalAcceptance;

	/**
	 * The External output.<br>
	 * 外部出力
	 **/
	private ExternalOutput externalOutput;

	/**
	 * The Aggregation any period.<br>
	 * 任意期間の集計
	 **/
	private AggregationAnyPeriod aggrAnyPeriod;

	/**
	 * The Index reconstruction.<br>
	 * インデックス再構成
	 **/
	private IndexReconstruction indexReconstruction;

	/**
	 * The Re-execution condition.<br>
	 * 再実行条件
	 */
	private ReExecutionCondition reExecCondition;

	/**
	 * Instantiates a new <code>ProcessExecutionSetting</code>.
	 *
	 * @param alarmExtraction       the alarm extraction
	 * @param perScheduleCreation   the personal schedule creation
	 * @param dailyPerf             the create daily performance
	 * @param reflectAppResult      the reflect approval result
	 * @param monthlyAggregate      the monthly aggregate
	 * @param appRouteUpdateDaily   the approval route update daily
	 * @param appRouteUpdateMonthly the approval route update monthly
	 * @param deleteData            the delete data
	 * @param saveData              the save data
	 * @param externalAcceptance    the external acceptance
	 * @param externalOutput        the external output
	 * @param aggrAnyPeriod         the Aggregation of arbitrary period
	 * @param indexReconstruction   the index reconstruction
	 */
	public ProcessExecutionSetting(AlarmExtraction alarmExtraction,
								   PersonalScheduleCreation perScheduleCreation,
								   DailyPerformanceCreation dailyPerf,
								   boolean reflectAppResult,
								   boolean monthlyAggregate,
								   AppRouteUpdateDaily appRouteUpdateDaily,
								   boolean appRouteUpdateMonthly,
								   DeleteData deleteData,
								   SaveData saveData,
								   ExternalAcceptance externalAcceptance,
								   ExternalOutput externalOutput,
								   AggregationAnyPeriod aggrAnyPeriod,
								   IndexReconstruction indexReconstruction) {
		this.alarmExtraction = alarmExtraction;
		this.perScheduleCreation = perScheduleCreation;
		this.dailyPerf = dailyPerf;
		this.reflectAppResult = new ReflectionApprovalResult(reflectAppResult);
		this.monthlyAggregate = new MonthlyAggregate(monthlyAggregate);
		this.appRouteUpdateDaily = appRouteUpdateDaily;
		this.appRouteUpdateMonthly = new AppRouteUpdateMonthly(appRouteUpdateMonthly);
		this.deleteData = deleteData;
		this.saveData = saveData;
		this.externalAcceptance = externalAcceptance;
		this.externalOutput = externalOutput;
		this.aggrAnyPeriod = aggrAnyPeriod;
		this.indexReconstruction = indexReconstruction;
		this.reExecCondition = null;
	}

}
