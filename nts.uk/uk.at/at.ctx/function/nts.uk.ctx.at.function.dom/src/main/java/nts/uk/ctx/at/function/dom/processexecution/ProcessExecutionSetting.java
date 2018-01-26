package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.processexecution.alarmextraction.IndividualAlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.alarmextraction.WorkplaceAlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;

/**
 * 更新処理実行設定
 */
@Getter
@Setter
@AllArgsConstructor
public class ProcessExecutionSetting {
	/* アラーム抽出（個人別） */
	private IndividualAlarmExtraction indvAlarm;
	
	/* アラーム抽出（職場別） */
	private WorkplaceAlarmExtraction wkpAlarm;
	
	/* 個人スケジュール作成 */
	private PersonalScheduleCreation perSchedule;
	
	/* 日別実績の作成・計算 */
	private DailyPerformanceCreation dailyPerf;
	
	/* 承認結果反映 */
	private boolean reflectResultCls;
	
	/* 月別集計 */
	private boolean monthlyAggCls;
}
