package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.algorithm;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYear;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItems;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * Schedule Monthly: Output of チェックする前にデータ準備
 *
 */
@Getter
@Builder
public class ScheMonPrepareData {
	/**
	 * List<勤怠項目コード、勤怠項目名称＞
	 */
	private Map<Integer, String> attendanceItems;
	
	/**
	 * List＜勤務種類＞
	 */
	private List<WorkType> listWorkType;
	
	/**
	 * List＜月別実績＞
	 */
	private List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlies;
	
	/**
	 * List＜勤務予定＞
	 */
	private List<WorkScheduleWorkInforImport> workScheduleWorkInfos;
	
	/**
	 * List＜スケジュール月次の固有抽出条件＞
	 */
	private List<FixedExtractionSMonCon> fixedScheConds;
	
	/**
	 * List＜スケジュール月次の固有抽出項目＞
	 */
	private List<FixedExtractionSMonItems> fixedScheCondItems;
	
	/**
	 * List＜スケジュール月次の任意抽出条件＞
	 */
	private List<ExtractionCondScheduleMonth> scheCondMonths;
	
	/**
	 * List<日別実績>
	 */
	private List<IntegrationOfDaily> listIntegrationDai;
	
	/**
	 * List＜社員ID、List＜期間、労働条件項目＞＞
	 */
	private Map<String, Map<DatePeriod, WorkingConditionItem>> empWorkingCondItem;
	
	/**
	 * List＜社員の雇用履歴＞
	 */
	private List<SharedSidPeriodDateEmploymentImport> lstEmploymentHis;
}
