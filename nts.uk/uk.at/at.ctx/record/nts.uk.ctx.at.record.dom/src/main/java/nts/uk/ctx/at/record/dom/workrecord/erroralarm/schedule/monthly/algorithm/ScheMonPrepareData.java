package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.ClosureDateOfEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItems;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdPresentClosingPeriod;
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
	
	/**
	 * ドメインモデル「公休設定」を取得
	 */
	private Optional<PublicHolidaySetting> publicHdSettingOpt;
	
	/**
	 * ドメインモデル「公休利用単位設定」を取得
	 */
	private Optional<PublicHolidayManagementUsageUnit> publicHolidayManagementUsageUnitOpt;
	
	/**
	 * List＜締めID, 現在の締め期間＞
	 */
	private List<ClosureIdPresentClosingPeriod> closingPeriods;
	
	/**
	 * List＜雇用毎の締め日＞
	 */
	private List<ClosureDateOfEmploymentImport> closureDateOfEmps;
	
	/**
	 * 利用単位の設定
	 */
	private Optional<PublicHolidayManagementUsageUnit> publicHolidayManagementUsageUnit;
	
	/**
	 * List 雇用月間日数設定
	 */
	private List<EmploymentMonthDaySetting> employmentMonthDaySetting;
	
	/**
	 * List 社員月間日数設定
	 */
	private List<EmployeeMonthDaySetting> employeeMonthDaySetting;
	
	/**
	 * List 職場月間日数設定
	 */
	private List<WorkplaceMonthDaySetting> workplaceMonthDaySetting;
	
	/**
	 * 会社月間日数設定
	 */
	private Optional<CompanyMonthDaySetting> companyMonthDaySetting;
	
	/**
	 * 労働時間と日数の設定の利用単位の設定
	 */
	private Optional<UsageUnitSetting> usageUnitSetting;
	
	/**
	 * List 会社別月単位労働時間
	 */
	private List<MonthlyWorkTimeSetCom> monthlyWorkTimeSetComs;
	
	/**
	 * List 雇用別月単位労働時間
	 */
	private List<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmps;
	
	/**
	 * List 職場別月単位労働時間
	 */
	private List<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkps;
	
	/**
	 * List 社員別月単位労働時間 
	 */
	private List<MonthlyWorkTimeSetSha> monthlyWorkTimeSetShas;
	
	/**
	 * List 雇用別フレックス勤務集計方法
	 */
	private List<EmpFlexMonthActCalSet> empFlexMonthActCalSets;
	
	/**
	 * List 職場別フレックス勤務集計方法
	 */
	private List<WkpFlexMonthActCalSet> wkpFlexMonthActCalSets;
	
	/**
	 * List 社員別フレックス勤務集計方法
	 */
	private List<ShaFlexMonthActCalSet> shaFlexMonthActCalSets;
	
	/**
	 * List 会社別フレックス勤務集計方法
	 */
	private Optional<ComFlexMonthActCalSet> comFlexMonthActCalSetOpt;
	
	/**
	 * List 労働条件
	 */
	private List<WorkingCondition> workingConditions;
}
