package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import javax.enterprise.concurrent.ManagedExecutorService;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
//import lombok.extern.slf4j.Slf4j;
import nts.arc.diagnose.stopwatch.concurrent.ConcurrentStopwatches;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.AsyncTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AggregateAnyItem;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.ErrorType;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.Flex;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerRepository;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveAttdRateDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.RemainDataTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.pererror.CreatePerErrorsFromLeaveErrors;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.anyitem.AnyItemAggrResult;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.GetDaysForCalcAttdRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.GetDaysForCalcAttdRateImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateInterimAnnualMngData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.record.dom.service.RemainNumberCreateInformation;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQueryImpl;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCreateDataInputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateDataImpl;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQueryImpl;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.RemainDaysOfSpecialHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementServiceImpl;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveRemainNoMinus;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 処理：ドメインサービス：月別実績を集計する
 * 
 * @author shuichi_ishida
 */
@Getter
/* @Slf4j */
public class AggregateMonthlyRecordServiceProc {

	/** 月別集計が必要とするリポジトリ */
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** 月次処理用の暫定残数管理データを作成する */
	private InterimRemainOffMonthProcess interimRemOffMonth;
	/** 残数作成元情報を作成する */
	private RemainNumberCreateInformation remNumCreateInfo;
	/** 指定期間の暫定残数管理データを作成する */
	private InterimRemainOffPeriodCreateData periodCreateData;
	/** 暫定年休管理データを作成する */
	private CreateInterimAnnualMngData createInterimAnnual;
	/** 期間中の年休積休残数を取得 */
	private GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod;
	/** 出勤率計算用日数を取得する */
	private GetDaysForCalcAttdRate getDaysForCalcAttdRate;
	/** 期間内の振出振休残数を取得する */
	private AbsenceReruitmentMngInPeriodQuery absenceRecruitMng;
	/** 期間内の休出代休残数を取得する */
	private BreakDayOffMngInPeriodQuery breakDayoffMng;
	/** 特別休暇 */
	private SpecialHolidayRepository specialHolidayRepo;
	/** 期間内の特別休暇残を集計する */
	private SpecialLeaveManagementService specialLeaveMng;
	/** 休暇残数エラーから月別残数エラー一覧を作成する */
	private CreatePerErrorsFromLeaveErrors createPerErrorFromLeaveErrors;
	/** 月別実績の編集状態 */
	private EditStateOfMonthlyPerRepository editStateRepo;

	/** 集計結果 */
	private AggregateMonthlyRecordValue aggregateResult;
	/** エラー情報 */
	private Map<String, MonthlyAggregationErrorInfo> errorInfos;
	/** 編集状態リスト */
	private List<EditStateOfMonthlyPerformance> editStates;

	/** 会社ID */
	@Setter
	private String companyId;
	/** 社員ID */
	@Setter
	private String employeeId;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日 */
	private ClosureDate closureDate;

	/** 月別集計で必要な会社別設定 */
	@Setter
	private MonAggrCompanySettings companySets;
	/** 月別集計で必要な社員別設定 */
	private MonAggrEmployeeSettings employeeSets;
	/** 月の計算中の日別実績データ */
	@Setter
	private MonthlyCalculatingDailys monthlyCalculatingDailys;
	/** 集計前の月別実績データ */
	private MonthlyOldDatas monthlyOldDatas;
	/** 労働条件項目 */
	private List<WorkingConditionItem> workingConditionItems;
	/** 労働条件 */
	private Map<String, DatePeriod> workingConditions;
	/** 前回集計結果 （年休積立年休の集計結果） */
	private AggrResultOfAnnAndRsvLeave prevAggrResult;
	/** 前回集計結果 （振休振出の集計結果） */
	private Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt;
	/** 前回集計結果 （代休の集計結果） */
	private Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt;
	/** 前回集計結果 （特別休暇の集計結果） */
	private Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap;
	/** 週NO管理 */
	private Map<YearMonth, Integer> weekNoMap;
	/** 手修正あり */
	private boolean isRetouch;
	/** 暫定残数データ */
	private Map<GeneralDate, DailyInterimRemainMngData> dailyInterimRemainMngs;
	/** 暫定残数データ上書きフラグ */
	private boolean isOverWriteRemain;
	/** 並列処理用 */
	private ManagedExecutorService executerService;

	public AggregateMonthlyRecordServiceProc(RepositoriesRequiredByMonthlyAggr repositories,
			InterimRemainOffMonthProcess interimRemOffMonth, RemainNumberCreateInformation remNumCreateInfo,
			InterimRemainOffPeriodCreateData periodCreateData, CreateInterimAnnualMngData createInterimAnnual,
			GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod,
			AbsenceReruitmentMngInPeriodQuery absenceRecruitMng, BreakDayOffMngInPeriodQuery breakDayoffMng,
			GetDaysForCalcAttdRate getDaysForCalcAttdRate, SpecialHolidayRepository specialHolidayRepo,
			SpecialLeaveManagementService specialLeaveMng, CreatePerErrorsFromLeaveErrors createPerErrorFromLeaveErrors,
			EditStateOfMonthlyPerRepository editStateRepo, ManagedExecutorService executerService) {

		this.repositories = repositories;
		this.interimRemOffMonth = interimRemOffMonth;
		this.remNumCreateInfo = remNumCreateInfo;
		this.periodCreateData = periodCreateData;
		this.createInterimAnnual = createInterimAnnual;
		this.getAnnAndRsvRemNumWithinPeriod = getAnnAndRsvRemNumWithinPeriod;
		this.absenceRecruitMng = absenceRecruitMng;
		this.breakDayoffMng = breakDayoffMng;
		this.getDaysForCalcAttdRate = getDaysForCalcAttdRate;
		this.specialHolidayRepo = specialHolidayRepo;
		this.specialLeaveMng = specialLeaveMng;
		this.createPerErrorFromLeaveErrors = createPerErrorFromLeaveErrors;
		this.editStateRepo = editStateRepo;
		this.executerService = executerService;
	}

	/**
	 * 集計処理
	 * 
	 * @param companyId
	 *            会社ID
	 * @param employeeId
	 *            社員ID
	 * @param yearMonth
	 *            年月
	 * @param closureId
	 *            締めID
	 * @param closureDate
	 *            締め日付
	 * @param datePeriod
	 *            期間
	 * @param prevAggrResult
	 *            前回集計結果 （年休積立年休の集計結果）
	 * @param prevAbsRecResultOpt
	 *            前回集計結果 （振休振出の集計結果）
	 * @param prevBreakDayOffResultOpt
	 *            前回集計結果 （代休の集計結果）
	 * @param prevSpecialLeaveResultMap
	 *            前回集計結果 （特別休暇の集計結果）
	 * @param companySets
	 *            月別集計で必要な会社別設定
	 * @param employeeSets
	 *            月別集計で必要な社員別設定
	 * @param dailyWorksOpt
	 *            日別実績(WORK)List
	 * @param monthlyWorkOpt
	 *            月別実績(WORK)
	 * @return 集計結果
	 */
	public AggregateMonthlyRecordValue aggregate(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult, Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorksOpt, Optional<IntegrationOfMonthly> monthlyWorkOpt) {

		val require = createRequireImpl();
		val cacheCarrier = new CacheCarrier();

		return this.aggregate(require, cacheCarrier, companyId, employeeId, yearMonth, closureId, closureDate,
				datePeriod, prevAggrResult, prevAbsRecResultOpt, prevBreakDayOffResultOpt, prevSpecialLeaveResultMap,
				companySets, employeeSets, dailyWorksOpt, monthlyWorkOpt, false);
	}

	/**
	 * 集計処理
	 * 
	 * @param companyId
	 *            会社ID
	 * @param employeeId
	 *            社員ID
	 * @param yearMonth
	 *            年月
	 * @param closureId
	 *            締めID
	 * @param closureDate
	 *            締め日付
	 * @param datePeriod
	 *            期間
	 * @param prevAggrResult
	 *            前回集計結果 （年休積立年休の集計結果）
	 * @param prevAbsRecResultOpt
	 *            前回集計結果 （振休振出の集計結果）
	 * @param prevBreakDayOffResultOpt
	 *            前回集計結果 （代休の集計結果）
	 * @param prevSpecialLeaveResultMap
	 *            前回集計結果 （特別休暇の集計結果）
	 * @param companySets
	 *            月別集計で必要な会社別設定
	 * @param employeeSets
	 *            月別集計で必要な社員別設定
	 * @param dailyWorksOpt
	 *            日別実績(WORK)List
	 * @param monthlyWorkOpt
	 *            月別実績(WORK)
	 * @param remainingProcAtr
	 *            残数処理フラグ
	 * @return 集計結果
	 */
	public AggregateMonthlyRecordValue aggregate(Require require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult, Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorksOpt, Optional<IntegrationOfMonthly> monthlyWorkOpt,
			Boolean remainingProcAtr) {

		this.aggregateResult = new AggregateMonthlyRecordValue();
		this.errorInfos = new HashMap<>();
		this.editStates = new ArrayList<>();

		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.prevAggrResult = prevAggrResult;
		this.prevAbsRecResultOpt = prevAbsRecResultOpt;
		this.prevBreakDayOffResultOpt = prevBreakDayOffResultOpt;
		this.prevSpecialLeaveResultMap = prevSpecialLeaveResultMap;
		this.weekNoMap = new HashMap<>();
		this.isRetouch = false;
		this.dailyInterimRemainMngs = new HashMap<>();
		this.isOverWriteRemain = false;

		ConcurrentStopwatches.start("12100:集計期間ごと準備：");

		this.companySets = companySets;
		this.employeeSets = employeeSets;

		// 社員を取得する
		EmployeeImport employee = this.employeeSets.getEmployee();

		// 入社前、退職後を期間から除く → 一か月の集計期間
		val termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
		DatePeriod monthPeriod = this.confirmProcPeriod(datePeriod, termInOffice);
		if (monthPeriod == null) {
			// 処理期間全体が、入社前または退職後の時
			return this.aggregateResult;
		}

		// 前月の36協定の集計がありえるため、前月分のデータも読み込んでおく （Redmine#107701）
		DatePeriod loadPeriod = new DatePeriod(monthPeriod.start(), monthPeriod.end());
		if (monthPeriod.start().after(employee.getEntryDate())) { // 開始日が入社日より後の時のみ、前月を読み込む
			loadPeriod = new DatePeriod(monthPeriod.start().addMonths(-1), monthPeriod.end());
		}

		// 計算に必要なデータを準備する
		this.monthlyCalculatingDailys = MonthlyCalculatingDailys.loadData(require, employeeId, loadPeriod,
				dailyWorksOpt, this.repositories);

		// 集計前の月別実績データを確認する
		this.monthlyOldDatas = MonthlyOldDatas.loadDataRequire(require, employeeId, yearMonth, closureId, closureDate,
				monthlyWorkOpt, this.repositories);

		// 「労働条件項目」を取得
		List<WorkingConditionItem> workingConditionItems = require.getBySidAndPeriodOrderByStrD(employeeId,
				monthPeriod);
		if (workingConditionItems.isEmpty()) {
			this.aggregateResult.addErrorInfos("001", new ErrMessageContent(TextResource.localize("Msg_430")));
			return this.aggregateResult;
		}

		// 同じ労働制の履歴を統合
		this.IntegrateHistoryOfSameWorkSys(require, workingConditionItems);

		// 所属情報の作成
		val affiliationInfo = this.createAffiliationInfo(require, monthPeriod);
		if (affiliationInfo == null) {
			for (val errorInfo : this.errorInfos.values()) {
				this.aggregateResult.getErrorInfos().putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			return this.aggregateResult;
		}
		this.aggregateResult.setAffiliationInfo(Optional.of(affiliationInfo));

		// 残数と集計の処理を並列で実行
		{
			CountDownLatch cdlAggregation = new CountDownLatch(1);
			MutableValue<RuntimeException> excepAggregation = new MutableValue<>();

			// 日別修正等の画面から実行されている場合、集計処理を非同期で実行
			Runnable asyncAggregation = () -> {
				try {
					this.aggregationProcess(require, cacheCarrier, monthPeriod);
				} catch (RuntimeException ex) {
					excepAggregation.set(ex);
				} finally {
					cdlAggregation.countDown();
				}
			};

			if (Thread.currentThread().getName().indexOf("REQUEST:") == 0) {
				this.executerService
						.submit(AsyncTask.builder().withContexts().threadName("Aggregation").build(asyncAggregation));
			} else {
				// バッチなどの場合は非同期にせずそのまま実行
				asyncAggregation.run();
			}

			// 残数処理
			// こちらはDB書き込みをしているので非同期化できない
			this.remainingProcess(require, cacheCarrier, monthPeriod, datePeriod, remainingProcAtr);

			// 非同期実行中の集計処理と待ち合わせ
			try {
				cdlAggregation.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			// 集計処理で例外が起きていたらここでthrow
			if (excepAggregation.optional().isPresent()) {
				throw excepAggregation.get();
			}
		}

		// 月別実績の編集状態 取得
		this.editStates = require.findByClosure(this.employeeId, this.yearMonth, this.closureId, this.closureDate);

		if (this.aggregateResult.getAttendanceTime().isPresent()) {
			AttendanceTimeOfMonthly attendanceTime = this.aggregateResult.getAttendanceTime().get();

			// 手修正された項目を元に戻す （勤怠時間用）
			attendanceTime = this.undoRetouchValuesForAttendanceTime(attendanceTime, this.monthlyOldDatas);

			// 手修正を戻してから計算必要な項目を再度計算
			if (this.isRetouch) {
				this.aggregateResult.setAttendanceTime(Optional.of(this.recalcAttendanceTime(attendanceTime)));
			}
		}

		ConcurrentStopwatches.start("12300:36協定時間：");

		// 基本計算結果を確認する
		Optional<MonthlyCalculation> basicCalced = Optional.empty();
		if (this.aggregateResult.getAttendanceTime().isPresent()) {
			basicCalced = Optional.of(this.aggregateResult.getAttendanceTime().get().getMonthlyCalculation());
		}

		// 36協定時間の集計
		{
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreementTimeOpt = monthlyCalculationForAgreement.aggregateAgreementTimeRequire(require, cacheCarrier,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate, monthPeriod,
					Optional.empty(), Optional.empty(), Optional.empty(), this.companySets, this.employeeSets,
					this.monthlyCalculatingDailys, this.monthlyOldDatas, basicCalced, this.repositories);
			if (agreementTimeOpt.isPresent()) {
				val agreementTime = agreementTimeOpt.get();
				this.aggregateResult.getAgreementTimeList().add(agreementTime);
			}

			// 36協定運用設定を取得
			if (this.companySets.getAgreementOperationSet().isPresent()) {
				AgreementOperationSetting agreementOpeSet = this.companySets.getAgreementOperationSet().get();

				// 締めと36協定期間の締め日が同じかどうか判断
				int monthClosureDay = ClosingDateType.LASTDAY.value; // 締めを36協定期間の締め日の値に揃える
				if (this.closureDate.getLastDayOfMonth() == false) {
					monthClosureDay = this.closureDate.getClosureDay().v() - 1;
				}
				if (monthClosureDay != agreementOpeSet.getClosingDateType().value && // 締めが異なる
																						// かつ
						monthPeriod.start().after(employee.getEntryDate())) { // 開始日が入社日より後の時

					// 集計期間を一ヶ月手前にずらす
					YearMonth prevYM = this.yearMonth.addMonths(-1);
					GeneralDate prevEnd = monthPeriod.start().addDays(-1);
					GeneralDate prevStart = prevEnd.addMonths(-1).addDays(1);
					DatePeriod prevPeriod = new DatePeriod(prevStart, prevEnd);

					// 36協定時間の集計
					MonthlyOldDatas prevOldDatas = MonthlyOldDatas.loadData(employeeId, prevYM, closureId, closureDate,
							Optional.empty(), this.repositories);
					MonthlyCalculation prevCalculationForAgreement = new MonthlyCalculation();
					val prevAgreTimeOpt = prevCalculationForAgreement.aggregateAgreementTimeRequire(require,
							cacheCarrier, this.companyId, this.employeeId, prevYM, this.closureId, this.closureDate,
							prevPeriod, Optional.empty(), Optional.empty(), Optional.empty(), this.companySets,
							this.employeeSets, this.monthlyCalculatingDailys, prevOldDatas, Optional.empty(),
							this.repositories);
					if (prevAgreTimeOpt.isPresent()) {
						val prevAgreTime = prevAgreTimeOpt.get();
						this.aggregateResult.getAgreementTimeList().add(prevAgreTime);
					}
				}
			}
		}

		ConcurrentStopwatches.stop("12300:36協定時間：");
		ConcurrentStopwatches.start("12500:任意項目：");

		// 大塚カスタマイズの集計
		Map<Integer, Map<Integer, AnyItemAggrResult>> anyItemCustomizeValue = this
				.aggregateCustomizeForOtsuka(cacheCarrier, this.yearMonth, this.closureId, this.companySets);

		// 月別実績の任意項目を集計
		this.aggregateAnyItem(monthPeriod, anyItemCustomizeValue);

		// 手修正された項目を元に戻す （任意項目用）
		this.undoRetouchValuesForAnyItems(this.monthlyOldDatas);

		ConcurrentStopwatches.stop("12500:任意項目：");
		ConcurrentStopwatches.start("12600:大塚カスタマイズ：");

		// 大塚カスタマイズ
		this.customizeForOtsuka();

		ConcurrentStopwatches.stop("12600:大塚カスタマイズ：");

		// 戻り値にエラー情報を移送
		for (val errorInfo : this.errorInfos.values()) {
			this.aggregateResult.getErrorInfos().putIfAbsent(errorInfo.getResourceId(), errorInfo);
		}

		return this.aggregateResult;
	}

	/**
	 * 集計処理
	 * 
	 * @param monthPeriod
	 */
	private void aggregationProcess(Require require, CacheCarrier cacheCarrier, DatePeriod monthPeriod) {
		ConcurrentStopwatches.stop("12100:集計期間ごと準備：");

		// 項目の数だけループ
		List<Flex> perErrorsForFlex = new ArrayList<>();
		for (val workingConditionItem : this.workingConditionItems) {

			ConcurrentStopwatches.start("12200:労働条件ごと：");

			// 「労働条件」の該当履歴から期間を取得
			val historyId = workingConditionItem.getHistoryId();
			if (!this.workingConditions.containsKey(historyId))
				continue;

			// 処理期間を計算 （一か月の集計期間と労働条件履歴期間の重複を確認する）
			val term = this.workingConditions.get(historyId);
			DatePeriod aggrPeriod = this.confirmProcPeriod(monthPeriod, term);
			if (aggrPeriod == null) {
				// 履歴の期間と重複がない時
				continue;
			}

			// 月別実績の勤怠時間を集計
			val aggregateResult = this.aggregateAttendanceTime(require, cacheCarrier, aggrPeriod, workingConditionItem);
			val attendanceTime = aggregateResult.getAttendanceTime();
			if (attendanceTime == null)
				continue;

			// 社員の月別実績のエラー（フレックス不足補填）を確認する
			val resultPerErrorsForFlex = attendanceTime.getMonthlyCalculation().getFlexTime().getPerErrors();
			for (val resultPerError : resultPerErrorsForFlex) {
				if (!perErrorsForFlex.contains(resultPerError))
					perErrorsForFlex.add(resultPerError);
			}

			// データを合算する
			if (this.aggregateResult.getAttendanceTime().isPresent()) {
				val calcedAttendanceTime = this.aggregateResult.getAttendanceTime().get();
				attendanceTime.sum(calcedAttendanceTime);
			}

			// 計算中のエラー情報の取得
			val monthlyCalculation = attendanceTime.getMonthlyCalculation();
			for (val errorInfo : monthlyCalculation.getErrorInfos()) {
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}

			// 計算結果を戻り値に蓄積
			this.aggregateResult.setAttendanceTime(Optional.of(attendanceTime));
			this.aggregateResult.getAttendanceTimeWeeks().addAll(aggregateResult.getAttendanceTimeWeeks());

			ConcurrentStopwatches.stop("12200:労働条件ごと：");
		}

		// 社員の月別実績のエラー（フレックス不足補填）を出力する
		for (val perError : perErrorsForFlex) {
			this.aggregateResult.getPerErrors().add(new EmployeeMonthlyPerError(ErrorType.FLEX, this.yearMonth,
					this.employeeId, this.closureId, this.closureDate, perError, null, null));
		}

		// 合算後のチェック処理
		this.checkAfterSum(monthPeriod);
	}

	/**
	 * 残数処理
	 * 
	 * @param monthPeriod
	 *            1か月の集計期間
	 * @param datePeriod
	 *            期間（実行期間）
	 * @param remainingProcAtr
	 *            残数処理フラグ
	 */
	private void remainingProcess(Require require, CacheCarrier cacheCarrier, DatePeriod monthPeriod,
			DatePeriod datePeriod, Boolean remainingProcAtr) {

		ConcurrentStopwatches.start("12400:残数処理：");

		// 当月の判断
		boolean isCurrentMonth = false;
		if (this.companySets.getCurrentMonthPeriodMap().containsKey(this.closureId.value)) {
			DatePeriod currentPeriod = this.companySets.getCurrentMonthPeriodMap().get(this.closureId.value);
			// 当月の締め期間と実行期間を比較し、重複した期間を取り出す
			DatePeriod confPeriod = this.confirmProcPeriod(currentPeriod, datePeriod);
			// 重複期間があれば、当月
			if (confPeriod != null)
				isCurrentMonth = true;
		}
		if (remainingProcAtr == true)
			isCurrentMonth = true;

		// 2019.4.25 UPD shuichi_ishida Redmine#107271(1)(EA.3359)
		// 残数処理を実行する当月判断方法を変更
		// 集計開始日を締め開始日をする時だけ、残数処理を実行する （集計期間の初月（＝締めの当月）だけ実行する）
		// if (this.employeeSets.isNoCheckStartDate()){
		// 実行期間が当月の締め期間に含まれる時だけ、残数処理を実行する
		if (isCurrentMonth) {

			// 残数処理
			this.remainingProcess(require, cacheCarrier, monthPeriod, InterimRemainMngMode.MONTHLY, true);
		}

		ConcurrentStopwatches.stop("12400:残数処理：");
	}

	/**
	 * 同じ労働制の履歴を統合
	 * 
	 * @param target
	 *            労働条件項目リスト （統合前）
	 * @return 労働条件項目リスト （統合後）
	 */
	private void IntegrateHistoryOfSameWorkSys(Require require, List<WorkingConditionItem> target) {

		this.workingConditionItems = new ArrayList<>();
		this.workingConditions = new HashMap<>();

		val itrTarget = target.listIterator();
		while (itrTarget.hasNext()) {

			// 要素[n]を取得
			WorkingConditionItem startItem = itrTarget.next();
			val startHistoryId = startItem.getHistoryId();
			val startConditionOpt = require.getByHistoryId(startHistoryId);
			if (!startConditionOpt.isPresent())
				continue;
			val startCondition = startConditionOpt.get();
			if (startCondition.getDateHistoryItem().isEmpty())
				continue;
			DatePeriod startPeriod = startCondition.getDateHistoryItem().get(0).span();

			// 要素[n]と要素[n+1]以降を順次比較
			WorkingConditionItem endItem = null;
			while (itrTarget.hasNext()) {
				WorkingConditionItem nextItem = target.get(itrTarget.nextIndex());
				if (startItem.getLaborSystem() != nextItem.getLaborSystem()
						|| startItem.getHourlyPaymentAtr() != nextItem.getHourlyPaymentAtr()) {

					// 労働制または時給者区分が異なる履歴が見つかった時点で、労働条件の統合をやめる
					break;
				}

				// 労働制と時給者区分が同じ履歴の要素を順次取得
				endItem = itrTarget.next();
			}

			// 次の要素がなくなった、または、異なる履歴が見つかれば、集計要素を確定する
			if (endItem == null) {
				this.workingConditionItems.add(startItem);
				this.workingConditions.putIfAbsent(startHistoryId, startPeriod);
				continue;
			}
			val endHistoryId = endItem.getHistoryId();
			val endConditionOpt = require.getByHistoryId(endHistoryId);
			if (!endConditionOpt.isPresent())
				continue;
			;
			val endCondition = endConditionOpt.get();
			if (endCondition.getDateHistoryItem().isEmpty())
				continue;
			this.workingConditionItems.add(endItem);
			this.workingConditions.putIfAbsent(endHistoryId,
					new DatePeriod(startPeriod.start(), endCondition.getDateHistoryItem().get(0).end()));
		}
	}

	/**
	 * 月別実績の勤怠時間を集計
	 * 
	 * @param datePeriod
	 *            期間
	 * @param workingConditionItem
	 *            労働条件項目
	 * @return 月別実績の勤怠時間
	 */
	private AggregateAttendanceTimeValue aggregateAttendanceTime(Require require, CacheCarrier cacheCarrier,
			DatePeriod datePeriod, WorkingConditionItem workingConditionItem) {

		AggregateAttendanceTimeValue result = new AggregateAttendanceTimeValue();

		// 週Noを確認する
		this.weekNoMap.putIfAbsent(this.yearMonth, 0);
		val startWeekNo = this.weekNoMap.get(this.yearMonth) + 1;

		// 労働制を確認する
		val workingSystem = workingConditionItem.getLaborSystem();

		ConcurrentStopwatches.start("12210:集計準備：");

		// 月別実績の勤怠時間 初期設定
		val attendanceTime = new AttendanceTimeOfMonthly(this.employeeId, this.yearMonth, this.closureId,
				this.closureDate, datePeriod);
		attendanceTime.prepareAggregationRequire(require, cacheCarrier, this.companyId, datePeriod,
				workingConditionItem, startWeekNo, this.companySets, this.employeeSets, this.monthlyCalculatingDailys,
				this.monthlyOldDatas, this.repositories);
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		if (monthlyCalculation.getErrorInfos().size() > 0) {
			for (val errorInfo : monthlyCalculation.getErrorInfos()) {
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			return result;
		}

		ConcurrentStopwatches.stop("12210:集計準備：");
		ConcurrentStopwatches.start("12220:月の計算：");

		// 月の計算
		monthlyCalculation.aggregateRequire(require, datePeriod, MonthlyAggregateAtr.MONTHLY, Optional.empty(),
				Optional.empty(), Optional.empty(), this.repositories);

		ConcurrentStopwatches.stop("12220:月の計算：");
		ConcurrentStopwatches.start("12230:縦計：");

		// 縦計
		{
			// 週単位の期間を取得
			for (val attendanceTimeWeek : attendanceTime.getMonthlyCalculation().getAttendanceTimeWeeks()) {
				DatePeriod weekPeriod = attendanceTimeWeek.getPeriod();

				// 週の縦計
				val verticalTotalWeek = attendanceTimeWeek.getVerticalTotal();
				verticalTotalWeek.verticalTotalRequire(require, this.companyId, this.employeeId, weekPeriod,
						workingSystem, this.companySets, this.employeeSets, this.monthlyCalculatingDailys,
						this.repositories);
			}

			// 月の縦計
			val verticalTotal = attendanceTime.getVerticalTotal();
			verticalTotal.verticalTotalRequire(require, this.companyId, this.employeeId, datePeriod, workingSystem,
					this.companySets, this.employeeSets, this.monthlyCalculatingDailys, this.repositories);
		}

		ConcurrentStopwatches.stop("12230:縦計：");
		ConcurrentStopwatches.start("12240:時間外超過：");

		// 時間外超過
		ExcessOutsideWorkMng excessOutsideWorkMng = new ExcessOutsideWorkMng(monthlyCalculation);
		excessOutsideWorkMng.aggregateRequire(require, cacheCarrier, this.repositories);
		if (excessOutsideWorkMng.getErrorInfos().size() > 0) {
			for (val errorInfo : excessOutsideWorkMng.getErrorInfos()) {
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
		}
		attendanceTime.setExcessOutsideWork(excessOutsideWorkMng.getExcessOutsideWork());

		ConcurrentStopwatches.stop("12240:時間外超過：");
		ConcurrentStopwatches.start("12250:回数集計：");

		// 回数集計
		{
			// 週単位の期間を取得
			for (val attendanceTimeWeek : attendanceTime.getMonthlyCalculation().getAttendanceTimeWeeks()) {
				DatePeriod weekPeriod = attendanceTimeWeek.getPeriod();

				// 週の回数集計
				val totalCountWeek = attendanceTimeWeek.getTotalCount();
				totalCountWeek.totalizeRequire(require, this.companyId, this.employeeId, weekPeriod, this.companySets,
						this.monthlyCalculatingDailys, this.repositories);
				if (totalCountWeek.getErrorInfos().size() > 0) {
					for (val errorInfo : totalCountWeek.getErrorInfos()) {
						this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
					}
				}
			}

			// 月の回数集計
			val totalCount = attendanceTime.getTotalCount();
			totalCount.totalizeRequire(require, this.companyId, this.employeeId, datePeriod, this.companySets,
					this.monthlyCalculatingDailys, this.repositories);
			if (totalCount.getErrorInfos().size() > 0) {
				for (val errorInfo : totalCount.getErrorInfos()) {
					this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
				}
			}
		}

		ConcurrentStopwatches.stop("12250:回数集計：");

		// 集計結果を返す
		result.setAttendanceTime(attendanceTime);
		for (val attendanceTimeWeek : attendanceTime.getMonthlyCalculation().getAttendanceTimeWeeks()) {
			val nowWeekNo = this.weekNoMap.get(this.yearMonth);
			if (nowWeekNo < attendanceTimeWeek.getWeekNo()) {
				this.weekNoMap.put(this.yearMonth, attendanceTimeWeek.getWeekNo());
			}
			result.getAttendanceTimeWeeks().add(attendanceTimeWeek);
		}
		return result;
	}

	/**
	 * 月別実績の任意項目を集計
	 * 
	 * @param monthPeriod
	 *            月の期間
	 * @param anyItemCustomizeValue
	 *            任意項目カスタマイズ値
	 */
	private void aggregateAnyItem(DatePeriod monthPeriod,
			Map<Integer, Map<Integer, AnyItemAggrResult>> anyItemCustomizeValue) {

		// 週単位の期間を取得
		ListIterator<AttendanceTimeOfWeekly> itrWeeks = this.aggregateResult.getAttendanceTimeWeeks().listIterator();
		while (itrWeeks.hasNext()) {
			AttendanceTimeOfWeekly attendanceTimeWeek = itrWeeks.next();

			// 週ごとの集計
			val weekResults = this.aggregateAnyItemPeriod(attendanceTimeWeek.getPeriod(), true,
					anyItemCustomizeValue.get(attendanceTimeWeek.getWeekNo()));
			for (val weekResult : weekResults.values()) {
				attendanceTimeWeek.getAnyItem().getAnyItemValues().put(weekResult.getOptionalItemNo(),
						AggregateAnyItem.of(weekResult.getOptionalItemNo(), weekResult.getAnyTime(),
								weekResult.getAnyTimes(), weekResult.getAnyAmount()));
			}
			itrWeeks.set(attendanceTimeWeek);
		}

		// 月ごとの集計
		val monthResults = this.aggregateAnyItemPeriod(monthPeriod, false, anyItemCustomizeValue.get(0));
		for (val monthResult : monthResults.values()) {
			this.aggregateResult.putAnyItemOrUpdate(AnyItemOfMonthly.of(this.employeeId, this.yearMonth, this.closureId,
					this.closureDate, monthResult));
		}
	}

	/**
	 * 大塚カスタマイズ （任意項目集計）
	 * 
	 * @param yearMonth
	 *            年月
	 * @param closureId
	 *            締めID
	 * @param companySets
	 *            月別集計で必要な会社別設定
	 * @return 任意項目カスタマイズ値
	 */
	private Map<Integer, Map<Integer, AnyItemAggrResult>> aggregateCustomizeForOtsuka(CacheCarrier cacheCarrier,
			YearMonth yearMonth, ClosureId closureId, MonAggrCompanySettings companySets) {

		// 任意項目カスタマイズ値 ※ 最初のInteger=0（月結果）、1～（各週結果（週No））
		Map<Integer, Map<Integer, AnyItemAggrResult>> results = new HashMap<>();
		results.put(0, new HashMap<>()); // 月結果

		// 月ごとの集計
		AnyItemAggrResult monthResult = this.getPredWorkingDays(cacheCarrier, yearMonth, closureId, companySets);
		results.get(0).putIfAbsent(monthResult.getOptionalItemNo(), monthResult);

		// 任意項目カスタマイズ値を返す
		return results;
	}

	/**
	 * 任意項目期間集計
	 * 
	 * @param period
	 *            期間
	 * @param isWeek
	 *            週間集計
	 * @param anyItemCustomizeValue
	 *            任意項目カスタマイズ値
	 * @return 任意項目集計結果
	 */
	private Map<Integer, AnyItemAggrResult> aggregateAnyItemPeriod(DatePeriod period, boolean isWeek,
			Map<Integer, AnyItemAggrResult> anyItemCustomizeValue) {

		Map<Integer, AnyItemAggrResult> results = new HashMap<>();
		List<AnyItemOfMonthly> anyItems = new ArrayList<>();

		// 任意項目ごとに集計する
		Map<Integer, AggregateAnyItem> anyItemTotals = new HashMap<>();
		for (val anyItemValueOfDaily : this.monthlyCalculatingDailys.getAnyItemValueOfDailyList()) {
			if (!period.contains(anyItemValueOfDaily.getYmd()))
				continue;
			if (anyItemValueOfDaily.getItems() == null)
				continue;
			val ymd = anyItemValueOfDaily.getYmd();
			for (val item : anyItemValueOfDaily.getItems()) {
				if (item.getItemNo() == null)
					continue;
				Integer itemNo = item.getItemNo().v();

				if (period.contains(ymd)) {
					anyItemTotals.putIfAbsent(itemNo, new AggregateAnyItem(itemNo));
					anyItemTotals.get(itemNo).addFromDaily(item);
				}
			}
		}

		// 任意項目を取得
		for (val optionalItem : this.companySets.getOptionalItemMap().values()) {
			Integer optionalItemNo = optionalItem.getOptionalItemNo().v();

			// 大塚カスタマイズ （月別実績の任意項目←任意項目カスタマイズ値）
			if (anyItemCustomizeValue != null) {
				if (anyItemCustomizeValue.containsKey(optionalItemNo)) {
					results.put(optionalItemNo, anyItemCustomizeValue.get(optionalItemNo));
					anyItems.add(AnyItemOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate,
							anyItemCustomizeValue.get(optionalItemNo)));
					continue;
				}
			}

			// 利用条件の判定
			Optional<EmpCondition> empCondition = Optional.empty();
			if (this.companySets.getEmpConditionMap().containsKey(optionalItemNo)) {
				empCondition = Optional.of(this.companySets.getEmpConditionMap().get(optionalItemNo));
			}
			val bsEmploymentHistOpt = this.employeeSets.getEmployment(period.end());
			if (optionalItem.checkTermsOfUse(empCondition, bsEmploymentHistOpt)) {
				// 利用する

				// 初期化
				AnyItemAggrResult result = AnyItemAggrResult.of(optionalItemNo, optionalItem);

				// 「実績区分」を判断
				if (optionalItem.getPerformanceAtr() == PerformanceAtr.DAILY_PERFORMANCE || isWeek) {

					// 日別実績 縦計処理
					result = AnyItemAggrResult.calcFromDailys(optionalItemNo, optionalItem, anyItemTotals);
				} else if (this.aggregateResult.getAttendanceTime().isPresent()) {
					val attendanceTime = this.aggregateResult.getAttendanceTime().get();

					// 月別実績 計算処理
					result = AnyItemAggrResult.calcFromMonthly(optionalItemNo, optionalItem, attendanceTime, anyItems,
							this.companySets, this.repositories);
				}
				results.put(optionalItemNo, result);
				anyItems.add(
						AnyItemOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate, result));
			} else {
				// 利用しない

				// 日別実績 縦計処理
				AnyItemAggrResult result = AnyItemAggrResult.calcFromDailys(optionalItemNo, optionalItem,
						anyItemTotals);
				results.put(optionalItemNo, result);
				anyItems.add(
						AnyItemOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate, result));
			}
		}

		return results;
	}

	/**
	 * 計画所定労働日数
	 * 
	 * @param yearMonth
	 *            年月
	 * @param closureId
	 *            締めID
	 * @param companySets
	 *            月別集計で必要な会社別設定
	 * @return 任意項目集計結果
	 */
	private AnyItemAggrResult getPredWorkingDays(CacheCarrier cacheCarrier, YearMonth yearMonth, ClosureId closureId,
			MonAggrCompanySettings companySets) {

		AnyItemAggrResult emptyResult = AnyItemAggrResult.of(69, null, new AnyTimesMonth(0.0), null);

		// 指定した年月の締め期間を取得する
		DatePeriod period = null;
		{
			// 対象の締めを取得する
			if (!companySets.getClosureMap().containsKey(closureId.value))
				return emptyResult;
			Closure closure = companySets.getClosureMap().get(closureId.value);

			// 指定した年月の期間をすべて取得する
			List<DatePeriod> periods = closure.getPeriodByYearMonth(yearMonth);
			if (periods.size() == 0)
				return emptyResult;

			// 期間を合算する
			GeneralDate startDate = periods.get(0).start();
			GeneralDate endDate = periods.get(0).end();
			if (periods.size() == 2) {
				if (startDate.after(periods.get(1).start()))
					startDate = periods.get(1).start();
				if (endDate.before(periods.get(1).end()))
					endDate = periods.get(1).end();
			}
			period = new DatePeriod(startDate, endDate);
		}

		// RQ608：指定期間の所定労働日数を取得する(大塚用)
		double predWorkingDays = this.repositories.getPredWorkingDaysAdaptor()
				.byPeriod(cacheCarrier, period, this.companySets.getAllWorkTypeMap()).v();

		// 任意項目69へ格納
		return AnyItemAggrResult.of(69, null, new AnyTimesMonth(predWorkingDays), null);
	}

	/**
	 * 合算後のチェック処理
	 * 
	 * @param period
	 *            期間
	 */
	private void checkAfterSum(DatePeriod period) {

		// 「日別実績の勤怠時間」を取得する
		if (!this.monthlyCalculatingDailys.isExistDailyRecord(period)) {
			val errorInfo = new MonthlyAggregationErrorInfo("018",
					new ErrMessageContent(TextResource.localize("Msg_1376")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return;
		}

		if (this.aggregateResult.getAttendanceTime().isPresent()) {
			val attendanceTime = this.aggregateResult.getAttendanceTime().get();

			// フレックス時間を確認する
			val flexTime = attendanceTime.getMonthlyCalculation().getFlexTime();
			int flexMinutes = flexTime.getFlexTime().getFlexTime().getTime().v();
			if (flexMinutes < 0) {
				this.aggregateResult.getPerErrors().add(new EmployeeMonthlyPerError(ErrorType.FLEX_SUPP, this.yearMonth,
						this.employeeId, this.closureId, this.closureDate, null, null, null));
			}
		}

		// ※ 乖離フラグは、縦計の合算と同時に再チェックされるため、ここでは不要。
		// ※ 平均時間関連は、縦計の合算と同時に再計算されるため、ここでは不要。
	}

	/**
	 * 手修正された項目を元に戻す （勤怠時間用）
	 * 
	 * @param attendanceTime
	 *            月別実績の勤怠時間
	 * @param monthlyOldDatas
	 *            集計前の月別実績データ
	 * @return 月別実績の勤怠時間
	 */
	private AttendanceTimeOfMonthly undoRetouchValuesForAttendanceTime(AttendanceTimeOfMonthly attendanceTime,
			MonthlyOldDatas monthlyOldDatas) {

		this.isRetouch = false;

		// 既存データを確認する
		val oldDataOpt = monthlyOldDatas.getAttendanceTime();
		if (!oldDataOpt.isPresent())
			return attendanceTime;
		val oldConverter = this.repositories.getAttendanceItemConverter().createMonthlyConverter();
		MonthlyRecordToAttendanceItemConverter oldItemConvert = oldConverter.withAttendanceTime(oldDataOpt.get());
		if (monthlyOldDatas.getAnnualLeaveRemain().isPresent()) {
			oldItemConvert = oldItemConvert.withAnnLeave(monthlyOldDatas.getAnnualLeaveRemain().get());
		}
		if (monthlyOldDatas.getReserveLeaveRemain().isPresent()) {
			oldItemConvert = oldItemConvert.withRsvLeave(monthlyOldDatas.getReserveLeaveRemain().get());
		}
		if (monthlyOldDatas.getAbsenceLeaveRemain().isPresent()) {
			oldItemConvert = oldItemConvert.withAbsenceLeave(monthlyOldDatas.getAbsenceLeaveRemain().get());
		}
		if (monthlyOldDatas.getMonthlyDayoffRemain().isPresent()) {
			oldItemConvert = oldItemConvert.withDayOff(monthlyOldDatas.getMonthlyDayoffRemain().get());
		}
		oldItemConvert = oldItemConvert.withSpecialLeave(monthlyOldDatas.getSpecialLeaveRemainList());

		// 計算後データを確認
		val monthlyConverter = this.repositories.getAttendanceItemConverter().createMonthlyConverter();
		MonthlyRecordToAttendanceItemConverter convert = monthlyConverter.withAttendanceTime(attendanceTime);
		if (this.aggregateResult.getAnnLeaRemNumEachMonthList().size() > 0) {
			convert = convert.withAnnLeave(this.aggregateResult.getAnnLeaRemNumEachMonthList().get(0));
		}
		if (this.aggregateResult.getRsvLeaRemNumEachMonthList().size() > 0) {
			convert = convert.withRsvLeave(this.aggregateResult.getRsvLeaRemNumEachMonthList().get(0));
		}
		if (this.aggregateResult.getAbsenceLeaveRemainList().size() > 0) {
			convert = convert.withAbsenceLeave(this.aggregateResult.getAbsenceLeaveRemainList().get(0));
		}
		if (this.aggregateResult.getMonthlyDayoffRemainList().size() > 0) {
			convert = convert.withDayOff(this.aggregateResult.getMonthlyDayoffRemainList().get(0));
		}
		if (this.aggregateResult.getSpecialLeaveRemainList().size() > 0) {
			convert = convert.withSpecialLeave(this.aggregateResult.getSpecialLeaveRemainList());
		}

		// 月別実績の編集状態を取得
		for (val editState : this.editStates) {

			// 勤怠項目IDから項目を判断
			val itemValueOpt = oldItemConvert.convert(editState.getAttendanceItemId());
			if (!itemValueOpt.isPresent())
				continue;
			val itemValue = itemValueOpt.get();
			if (itemValue.value() == null)
				continue;

			// 該当する勤怠項目IDの値を計算前に戻す
			convert.merge(itemValue);
			this.isRetouch = true;
		}

		// いずれかの手修正値を戻した時、戻した後の勤怠時間を返す
		if (this.isRetouch) {
			val convertedOpt = convert.toAttendanceTime();
			if (convertedOpt.isPresent()) {
				val retouchedTime = convertedOpt.get();
				retouchedTime.getMonthlyCalculation().copySettings(attendanceTime.getMonthlyCalculation());

				val convertedAnnLeaveOpt = convert.toAnnLeave();
				if (convertedAnnLeaveOpt.isPresent()) {
					this.aggregateResult.getAnnLeaRemNumEachMonthList().clear();
					this.aggregateResult.getAnnLeaRemNumEachMonthList().add(convertedAnnLeaveOpt.get());
				}
				val convertedRsvLeaveOpt = convert.toRsvLeave();
				if (convertedRsvLeaveOpt.isPresent()) {
					this.aggregateResult.getRsvLeaRemNumEachMonthList().clear();
					this.aggregateResult.getRsvLeaRemNumEachMonthList().add(convertedRsvLeaveOpt.get());
				}
				val convertedAbsLeaveOpt = convert.toAbsenceLeave();
				if (convertedAbsLeaveOpt.isPresent()) {
					this.aggregateResult.getAbsenceLeaveRemainList().clear();
					this.aggregateResult.getAbsenceLeaveRemainList().add(convertedAbsLeaveOpt.get());
				}
				val convertedDayOffOpt = convert.toDayOff();
				if (convertedDayOffOpt.isPresent()) {
					this.aggregateResult.getMonthlyDayoffRemainList().clear();
					this.aggregateResult.getMonthlyDayoffRemainList().add(convertedDayOffOpt.get());
				}
				val convertedSpcLeaveList = convert.toSpecialHoliday();
				if (convertedSpcLeaveList.size() > 0) {
					this.aggregateResult.getSpecialLeaveRemainList().clear();
					this.aggregateResult.getSpecialLeaveRemainList().addAll(convertedSpcLeaveList);
				}
				return retouchedTime;
			}
		}
		return attendanceTime;
	}

	/**
	 * 手修正を戻してから計算必要な項目を再度計算
	 * 
	 * @param attendanceTime
	 *            月別実績の勤怠時間
	 * @return 月別実績の勤怠時間
	 */
	private AttendanceTimeOfMonthly recalcAttendanceTime(AttendanceTimeOfMonthly attendanceTime) {

		val monthlyCalculation = attendanceTime.getMonthlyCalculation();

		// 残業合計時間を集計する
		monthlyCalculation.getAggregateTime().getOverTime().recalcTotal();

		// 休出合計時間を集計する
		monthlyCalculation.getAggregateTime().getHolidayWorkTime().recalcTotal();

		// 総労働時間と36協定時間の再計算
		monthlyCalculation.recalcTotalAndAgreement(attendanceTime.getDatePeriod(), MonthlyAggregateAtr.MONTHLY,
				this.repositories);

		return attendanceTime;
	}

	/**
	 * 手修正された項目を元に戻す （任意項目用）
	 * 
	 * @param monthlyOldDatas
	 *            集計前の月別実績データ
	 */
	private void undoRetouchValuesForAnyItems(MonthlyOldDatas monthlyOldDatas) {

		this.isRetouch = false;

		// 既存データを確認する
		val oldDataList = monthlyOldDatas.getAnyItemList();
		if (oldDataList.size() == 0)
			return;
		val oldConverter = this.repositories.getAttendanceItemConverter().createMonthlyConverter();
		val oldItemConvert = oldConverter.withAnyItem(oldDataList);

		// 計算後データを確認
		val monthlyConverter = this.repositories.getAttendanceItemConverter().createMonthlyConverter();
		MonthlyRecordToAttendanceItemConverter convert = monthlyConverter
				.withAttendanceTime(this.aggregateResult.getAttendanceTime().get());
		convert = convert.withAnyItem(this.aggregateResult.getAnyItemList());

		// 月別実績の編集状態を取得
		for (val editState : this.editStates) {

			// 勤怠項目IDから項目を判断
			val itemValueOpt = oldItemConvert.convert(editState.getAttendanceItemId());
			if (!itemValueOpt.isPresent())
				continue;
			val itemValue = itemValueOpt.get();
			if (itemValue.value() == null)
				continue;

			// 該当する勤怠項目IDの値を計算前に戻す
			convert.merge(itemValue);
			this.isRetouch = true;
		}

		// いずれかの手修正値を戻した時、戻した後の任意項目を返す
		if (this.isRetouch) {
			val convertedList = convert.toAnyItems();
			this.aggregateResult.setAnyItemList(convertedList);
		}
	}

	/**
	 * 残数処理
	 * 
	 * @param period
	 *            期間
	 * @param interimRemainMngMode
	 *            暫定残数データ管理モード
	 * @param isCalcAttendanceRate
	 *            出勤率計算フラグ
	 */
	private void remainingProcess(Require require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate) {

		ConcurrentStopwatches.start("12405:暫定データ作成：");

		// Workを考慮した月次処理用の暫定残数管理データを作成する
		this.createDailyInterimRemainMngs(require, cacheCarrier, period);

		ConcurrentStopwatches.stop("12405:暫定データ作成：");
		ConcurrentStopwatches.start("12410:年休積休：");

		// 年休、積休
		this.annualAndReserveLeaveRemain(require, cacheCarrier, period, interimRemainMngMode, isCalcAttendanceRate);

		ConcurrentStopwatches.stop("12410:年休積休：");
		ConcurrentStopwatches.start("12420:振休：");

		// 振休
		this.absenceLeaveRemain(require, cacheCarrier, period, interimRemainMngMode);

		ConcurrentStopwatches.stop("12420:振休：");
		ConcurrentStopwatches.start("12430:代休：");

		// 代休
		this.dayoffRemain(require, cacheCarrier, period, interimRemainMngMode);

		ConcurrentStopwatches.stop("12430:代休：");
		ConcurrentStopwatches.start("12440:特別休暇：");

		// 特別休暇
		this.specialLeaveRemain(require, cacheCarrier, period, interimRemainMngMode);

		ConcurrentStopwatches.stop("12440:特別休暇：");
	}

	/**
	 * Workを考慮した月次処理用の暫定残数管理データを作成する
	 * 
	 * @param period
	 *            期間
	 */
	public void createDailyInterimRemainMngs(Require require, CacheCarrier cacheCarrier, DatePeriod period) {

		// 【参考：旧処理】 月次処理用の暫定残数管理データを作成する
		// this.dailyInterimRemainMngs =
		// this.interimRemOffMonth.monthInterimRemainData(
		// this.companyId, this.employeeId, period);

		// 残数作成元情報(実績)を作成する
		List<RecordRemainCreateInfor> recordRemains = this.remNumCreateInfo.createRemainInfor(
				new ArrayList<>(this.monthlyCalculatingDailys.getAttendanceTimeOfDailyMap().values()),
				new ArrayList<>(this.monthlyCalculatingDailys.getWorkInfoOfDailyMap().values()));

		// 指定期間の暫定残数管理データを作成する
		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(this.companyId,
				this.employeeId, period, recordRemains, Collections.emptyList(), Collections.emptyList(), false);
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(this.companyId,
				this.companySets.getAbsSettingOpt(), this.companySets.getDayOffSetting());
		this.dailyInterimRemainMngs = this.periodCreateData.createInterimRemainDataMngRequire(require, cacheCarrier,
				inputPara, comHolidaySetting);

		this.isOverWriteRemain = (this.dailyInterimRemainMngs.size() > 0);
	}

	/**
	 * 年休、積休
	 * 
	 * @param period
	 *            期間
	 * @param interimRemainMngMode
	 *            暫定残数データ管理モード
	 * @param isCalcAttendanceRate
	 *            出勤率計算フラグ
	 */
	private void annualAndReserveLeaveRemain(Require require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate) {

		// 暫定残数データを年休・積立年休に絞り込む
		List<TmpAnnualLeaveMngWork> tmpAnnualLeaveMngs = new ArrayList<>();
		List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			val master = dailyInterimRemainMng.getRecAbsData().get(0);

			// 年休
			if (dailyInterimRemainMng.getAnnualHolidayData().isPresent()) {
				val data = dailyInterimRemainMng.getAnnualHolidayData().get();
				tmpAnnualLeaveMngs.add(TmpAnnualLeaveMngWork.of(master, data));
			}

			// 積立年休
			if (dailyInterimRemainMng.getResereData().isPresent()) {
				val data = dailyInterimRemainMng.getResereData().get();
				tmpReserveLeaveMngs.add(TmpReserveLeaveMngWork.of(master, data));
			}
		}

		// 月別実績の計算結果が存在するかチェック
		boolean isOverWriteAnnual = this.isOverWriteRemain;
		if (this.aggregateResult.getAttendanceTime().isPresent()) {

			// 年休控除日数分の年休暫定残数データを作成する
			val compensFlexWorkOpt = this.createInterimAnnual
					.ofCompensFlexToWork(this.aggregateResult.getAttendanceTime().get(), period.end());
			if (compensFlexWorkOpt.isPresent()) {
				tmpAnnualLeaveMngs.add(compensFlexWorkOpt.get());
				isOverWriteAnnual = true;
			}
		}

		// 「モード」をチェック
		CalYearOffWorkAttendRate daysForCalcAttdRate = new CalYearOffWorkAttendRate();
		if (interimRemainMngMode == InterimRemainMngMode.MONTHLY) {

			// 日別実績から出勤率計算用日数を取得 （月別集計用）
			daysForCalcAttdRate = this.getDaysForCalcAttdRate.algorithmRequire(require, cacheCarrier, require,
					this.companyId, this.employeeId, period, this.companySets, this.monthlyCalculatingDailys,
					this.repositories);
		}

		// 期間中の年休積休残数を取得
		val aggrResult = this.getAnnAndRsvRemNumWithinPeriod.algorithm(this.companyId, this.employeeId, period,
				interimRemainMngMode,
				// period.end(), true, isCalcAttendanceRate,
				period.end(), false, isCalcAttendanceRate, Optional.of(isOverWriteAnnual),
				Optional.of(tmpAnnualLeaveMngs), Optional.of(tmpReserveLeaveMngs), Optional.of(false),
				Optional.of(this.employeeSets.isNoCheckStartDate()), this.prevAggrResult.getAnnualLeave(),
				this.prevAggrResult.getReserveLeave(), Optional.of(this.companySets), Optional.of(this.employeeSets),
				Optional.of(this.monthlyCalculatingDailys));

		// 2回目の取得以降は、締め開始日を確認させる
		this.employeeSets.setNoCheckStartDate(false);

		if (aggrResult.getAnnualLeave().isPresent()) {
			val asOfPeriodEnd = aggrResult.getAnnualLeave().get().getAsOfPeriodEnd();
			val asOfStartNextDayOfPeriodEnd = aggrResult.getAnnualLeave().get().getAsOfStartNextDayOfPeriodEnd();
			val remainingNumber = asOfPeriodEnd.getRemainingNumber();

			// 年休月別残数データを更新
			AnnLeaRemNumEachMonth annLeaRemNum = AnnLeaRemNumEachMonth.of(this.employeeId, this.yearMonth,
					this.closureId, this.closureDate, period, ClosureStatus.UNTREATED,
					remainingNumber.getAnnualLeaveNoMinus(), remainingNumber.getAnnualLeaveWithMinus(),
					remainingNumber.getHalfDayAnnualLeaveNoMinus(), remainingNumber.getHalfDayAnnualLeaveWithMinus(),
					asOfStartNextDayOfPeriodEnd.getGrantInfo(), remainingNumber.getTimeAnnualLeaveNoMinus(),
					remainingNumber.getTimeAnnualLeaveWithMinus(),
					AnnualLeaveAttdRateDays.of(new MonthlyDays(daysForCalcAttdRate.getWorkingDays()),
							new MonthlyDays(daysForCalcAttdRate.getPrescribedDays()),
							new MonthlyDays(daysForCalcAttdRate.getDeductedDays())),
					asOfStartNextDayOfPeriodEnd.isAfterGrantAtr());
			this.aggregateResult.getAnnLeaRemNumEachMonthList().add(annLeaRemNum);

			// 年休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors()
					.addAll(this.createPerErrorFromLeaveErrors.fromAnnualLeave(this.employeeId, this.yearMonth,
							this.closureId, this.closureDate,
							aggrResult.getAnnualLeave().get().getAnnualLeaveErrors()));
		}

		if (aggrResult.getReserveLeave().isPresent()) {
			val asOfPeriodEnd = aggrResult.getReserveLeave().get().getAsOfPeriodEnd();
			val asOfStartNextDayOfPeriodEnd = aggrResult.getReserveLeave().get().getAsOfStartNextDayOfPeriodEnd();
			val remainingNumber = asOfPeriodEnd.getRemainingNumber();

			// 積立年休月別残数データを更新
			ReserveLeaveGrant reserveLeaveGrant = null;
			if (asOfStartNextDayOfPeriodEnd.getGrantInfo().isPresent()) {
				reserveLeaveGrant = ReserveLeaveGrant
						.of(asOfStartNextDayOfPeriodEnd.getGrantInfo().get().getGrantDays());
			}
			RsvLeaRemNumEachMonth rsvLeaRemNum = RsvLeaRemNumEachMonth.of(this.employeeId, this.yearMonth,
					this.closureId, this.closureDate, period, ClosureStatus.UNTREATED,
					remainingNumber.getReserveLeaveNoMinus(), remainingNumber.getReserveLeaveWithMinus(),
					Optional.ofNullable(reserveLeaveGrant), asOfStartNextDayOfPeriodEnd.isAfterGrantAtr());
			this.aggregateResult.getRsvLeaRemNumEachMonthList().add(rsvLeaRemNum);

			// 積立年休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors()
					.addAll(this.createPerErrorFromLeaveErrors.fromReserveLeave(this.employeeId, this.yearMonth,
							this.closureId, this.closureDate,
							aggrResult.getReserveLeave().get().getReserveLeaveErrors()));
		}

		// 集計結果を前回集計結果に引き継ぐ
		this.aggregateResult.setAggrResultOfAnnAndRsvLeave(aggrResult);
	}

	/**
	 * 振休
	 * 
	 * @param period
	 *            期間
	 * @param interimRemainMngMode
	 *            暫定残数データ管理モード
	 */
	private void absenceLeaveRemain(Require require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode) {

		// 暫定残数データを振休・振出に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimAbsMng> useAbsMng = new ArrayList<>();
		List<InterimRecMng> useRecMng = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			interimMng.addAll(dailyInterimRemainMng.getRecAbsData());

			// 振休
			if (dailyInterimRemainMng.getInterimAbsData().isPresent()) {
				useAbsMng.add(dailyInterimRemainMng.getInterimAbsData().get());
			}

			// 振出
			if (dailyInterimRemainMng.getRecData().isPresent()) {
				useRecMng.add(dailyInterimRemainMng.getRecData().get());
			}
		}

		// 期間内の振休振出残数を取得する
		AbsRecMngInPeriodParamInput paramInput = new AbsRecMngInPeriodParamInput(this.companyId, this.employeeId,
				period, period.end(), (interimRemainMngMode == InterimRemainMngMode.MONTHLY), this.isOverWriteRemain,
				useAbsMng, interimMng, useRecMng, this.prevAbsRecResultOpt, Optional.empty(), Optional.empty());
		val aggrResult = this.absenceRecruitMng.getAbsRecMngInPeriodRequire(require, cacheCarrier, paramInput);
		if (aggrResult != null) {

			// 振休月別残数データを更新
			AbsenceLeaveRemainData absLeaRemNum = new AbsenceLeaveRemainData(this.employeeId, this.yearMonth,
					this.closureId.value, this.closureDate.getClosureDay().v(), this.closureDate.getLastDayOfMonth(),
					ClosureStatus.UNTREATED, period.start(), period.end(),
					new RemainDataDaysMonth(aggrResult.getOccurrenceDays()),
					new RemainDataDaysMonth(aggrResult.getUseDays()),
					new AttendanceDaysMonthToTal(aggrResult.getRemainDays()),
					new AttendanceDaysMonthToTal(aggrResult.getCarryForwardDays()),
					new RemainDataDaysMonth(aggrResult.getUnDigestedDays()));
			this.aggregateResult.getAbsenceLeaveRemainList().add(absLeaRemNum);

			// 振休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors().addAll(this.createPerErrorFromLeaveErrors.fromPause(this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, aggrResult.getPError()));

			// 集計結果を前回集計結果に引き継ぐ
			this.aggregateResult.setAbsRecRemainMngOfInPeriodOpt(Optional.of(aggrResult));
		}
	}

	/**
	 * 代休
	 * 
	 * @param period
	 *            期間
	 * @param interimRemainMngMode
	 *            暫定残数データ管理モード
	 */
	private void dayoffRemain(Require require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode) {

		// 暫定残数データを休出・代休に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			interimMng.addAll(dailyInterimRemainMng.getRecAbsData());

			// 休出
			if (dailyInterimRemainMng.getBreakData().isPresent()) {
				breakMng.add(dailyInterimRemainMng.getBreakData().get());
			}

			// 代休
			if (dailyInterimRemainMng.getDayOffData().isPresent()) {
				dayOffMng.add(dailyInterimRemainMng.getDayOffData().get());
			}
		}

		// 期間内の休出代休残数を取得する
		BreakDayOffRemainMngParam inputParam = new BreakDayOffRemainMngParam(this.companyId, this.employeeId, period,
				(interimRemainMngMode == InterimRemainMngMode.MONTHLY), period.end(), this.isOverWriteRemain,
				interimMng, breakMng, dayOffMng, this.prevBreakDayOffResultOpt, Optional.empty(), Optional.empty());
		val aggrResult = this.breakDayoffMng.getBreakDayOffMngInPeriodRequire(require, cacheCarrier, inputParam);
		if (aggrResult != null) {

			// 代休月別残数データを更新
			MonthlyDayoffRemainData monDayRemNum = new MonthlyDayoffRemainData(this.employeeId, this.yearMonth,
					this.closureId.value, this.closureDate.getClosureDay().v(), this.closureDate.getLastDayOfMonth(),
					ClosureStatus.UNTREATED, period.start(), period.end(),
					new DayOffDayAndTimes(new RemainDataDaysMonth(aggrResult.getOccurrenceDays()),
							Optional.of(new RemainDataTimesMonth(aggrResult.getOccurrenceTimes()))),
					new DayOffDayAndTimes(new RemainDataDaysMonth(aggrResult.getUseDays()),
							Optional.of(new RemainDataTimesMonth(aggrResult.getUseTimes()))),
					new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(aggrResult.getRemainDays()),
							Optional.of(new RemainingMinutes(aggrResult.getRemainTimes()))),
					new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(aggrResult.getCarryForwardDays()),
							Optional.of(new RemainingMinutes(aggrResult.getRemainTimes()))),
					new DayOffDayAndTimes(new RemainDataDaysMonth(aggrResult.getUnDigestedDays()),
							Optional.of(new RemainDataTimesMonth(aggrResult.getUnDigestedTimes()))));
			this.aggregateResult.getMonthlyDayoffRemainList().add(monDayRemNum);

			// 代休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors().addAll(this.createPerErrorFromLeaveErrors.fromDayOff(this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, aggrResult.getLstError()));

			// 集計結果を前回集計結果に引き継ぐ
			this.aggregateResult.setBreakDayOffRemainMngOfInPeriodOpt(Optional.of(aggrResult));
		}
	}

	/**
	 * 特別休暇
	 * 
	 * @param period
	 *            期間
	 * @param interimRemainMngMode
	 *            暫定残数データ管理モード
	 */
	private void specialLeaveRemain(Require require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode) {

		// 暫定残数データを特別休暇に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimSpecialHolidayMng> interimSpecialData = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			if (dailyInterimRemainMng.getSpecialHolidayData().size() <= 0)
				continue;
			interimMng.addAll(dailyInterimRemainMng.getRecAbsData());
			interimSpecialData.addAll(dailyInterimRemainMng.getSpecialHolidayData());
		}

		// 「特別休暇」を取得する
		val specialHolidays = require.findSpecialHolidayByCompanyId(this.companyId);
		for (val specialHoliday : specialHolidays) {
			Integer specialLeaveCode = specialHoliday.getSpecialHolidayCode().v();

			// 前回集計結果を確認する
			Optional<InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResult = Optional.empty();
			if (this.prevSpecialLeaveResultMap.containsKey(specialLeaveCode)) {
				prevSpecialLeaveResult = Optional.of(this.prevSpecialLeaveResultMap.get(specialLeaveCode));
			}

			// マイナスなしを含めた期間内の特別休暇残を集計する
			// 期間内の特別休暇残を集計する
			ComplileInPeriodOfSpecialLeaveParam param = new ComplileInPeriodOfSpecialLeaveParam(this.companyId,
					this.employeeId, period,
					// (interimRemainMngMode == InterimRemainMngMode.MONTHLY),
					// period.end(), specialLeaveCode, true,
					(interimRemainMngMode == InterimRemainMngMode.MONTHLY), period.end(), specialLeaveCode, false,
					this.isOverWriteRemain, interimMng, interimSpecialData, prevSpecialLeaveResult);
			InPeriodOfSpecialLeaveResultInfor aggrResult = this.specialLeaveMng
					.complileInPeriodOfSpecialLeaveRequire(require, cacheCarrier, param);
			InPeriodOfSpecialLeave inPeriod = aggrResult.getAggSpecialLeaveResult();

			// マイナスなしの残数・使用数を計算
			RemainDaysOfSpecialHoliday remainDays = inPeriod.getRemainDays();
			SpecialLeaveRemainNoMinus remainNoMinus = new SpecialLeaveRemainNoMinus(remainDays);

			// 特別休暇月別残数データを更新
			SpecialHolidayRemainData speLeaRemNum = SpecialHolidayRemainData.of(this.employeeId, this.yearMonth,
					this.closureId, this.closureDate, period, specialLeaveCode, inPeriod, remainNoMinus);
			this.aggregateResult.getSpecialLeaveRemainList().add(speLeaRemNum);

			// 特別休暇エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors()
					.addAll(this.createPerErrorFromLeaveErrors.fromSpecialLeave(this.employeeId, this.yearMonth,
							this.closureId, this.closureDate, specialLeaveCode, inPeriod.getLstError()));

			// 集計結果を前回集計結果に引き継ぐ
			this.aggregateResult.getInPeriodOfSpecialLeaveResultInforMap().put(specialLeaveCode, aggrResult);
		}
	}

	/**
	 * 大塚カスタマイズ
	 */
	private void customizeForOtsuka() {

		// 2018.01.21 DEL shuichi_ishida Redmine#105681
		// 時短日割適用日数
		// this.TimeSavingDailyRateApplyDays();
	}

	/**
	 * 処理期間との重複を確認する （重複期間を取り出す）
	 * 
	 * @param target
	 *            処理期間
	 * @param comparison
	 *            比較対象期間
	 * @return 重複期間 （null = 重複なし）
	 */
	private DatePeriod confirmProcPeriod(DatePeriod target, DatePeriod comparison) {

		DatePeriod overlap = null; // 重複期間

		// 開始前
		if (target.isBefore(comparison))
			return overlap;

		// 終了後
		if (target.isAfter(comparison))
			return overlap;

		// 重複あり
		overlap = target;

		// 開始日より前を除外
		if (overlap.contains(comparison.start())) {
			overlap = overlap.cutOffWithNewStart(comparison.start());
		}

		// 終了日より後を除外
		if (overlap.contains(comparison.end())) {
			overlap = overlap.cutOffWithNewEnd(comparison.end());
		}

		return overlap;
	}

	/**
	 * 所属情報の作成
	 * 
	 * @param datePeriod
	 *            期間
	 * @return 月別実績の所属情報
	 */
	private AffiliationInfoOfMonthly createAffiliationInfo(Require require, DatePeriod datePeriod) {

		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(this.employeeId);

		// 月初の所属情報を取得
		boolean isExistStartWorkInfo = false;
		if (this.monthlyCalculatingDailys.getWorkInfoOfDailyMap().containsKey(datePeriod.start())) {
			isExistStartWorkInfo = true;
		}
		val firstInfoOfDailyList = require.getAffiliationInfoOfDaily(employeeIds, datePeriod);
		firstInfoOfDailyList.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));
		if (firstInfoOfDailyList.size() <= 0) {
			if (isExistStartWorkInfo) {
				val errorInfo = new MonthlyAggregationErrorInfo("003",
						new ErrMessageContent(TextResource.localize("Msg_1157")));
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			return null;
		}
		val firstInfoOfDaily = firstInfoOfDailyList.get(0);
		val firstWorkTypeOfDailyList = require.findWorkTypeByKey(employeeIds, datePeriod);
		firstWorkTypeOfDailyList.sort((a, b) -> a.getDate().compareTo(b.getDate()));
		if (firstWorkTypeOfDailyList.size() <= 0) {
			if (isExistStartWorkInfo) {
				val errorInfo = new MonthlyAggregationErrorInfo("003",
						new ErrMessageContent(TextResource.localize("Msg_1157")));
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			return null;
		}
		val firstWorkTypeOfDaily = firstWorkTypeOfDailyList.get(0);

		// 月初の情報を作成
		val firstInfo = AggregateAffiliationInfo.of(firstInfoOfDaily.getEmploymentCode(),
				new WorkplaceId(firstInfoOfDaily.getWplID()), new JobTitleId(firstInfoOfDaily.getJobTitleID()),
				firstInfoOfDaily.getClsCode(), firstWorkTypeOfDaily.getWorkTypeCode());

		// 月末がシステム日付以降の場合、月初の情報を月末の情報とする
		if (datePeriod.end().after(GeneralDate.today())) {

			// 月別実績の所属情報を返す
			return AffiliationInfoOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					firstInfo, firstInfo);
		}

		// 月末の所属情報を取得
		val lastInfoOfDailyOpt = require.findAffiliationByKey(this.employeeId, datePeriod.end());
		if (!lastInfoOfDailyOpt.isPresent()) {
			// val errorInfo = new MonthlyAggregationErrorInfo(
			// "004", new ErrMessageContent(TextResource.localize("Msg_1157")));
			// this.errorInfos.putIfAbsent(errorInfo.getResourceId(),
			// errorInfo);
			// return null;

			// 月別実績の所属情報を返す （エラーにせず、月末に月初の情報を入れる）
			return AffiliationInfoOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					firstInfo, firstInfo);
		}
		val lastInfoOfDaily = lastInfoOfDailyOpt.get();
		val lastWorkTypeOfDailyOpt = require.findWorkTypeByKey(this.employeeId, datePeriod.end());
		if (!lastWorkTypeOfDailyOpt.isPresent()) {
			// val errorInfo = new MonthlyAggregationErrorInfo(
			// "004", new ErrMessageContent(TextResource.localize("Msg_1157")));
			// this.errorInfos.putIfAbsent(errorInfo.getResourceId(),
			// errorInfo);
			// return null;

			// 月別実績の所属情報を返す （エラーにせず、月末に月初の情報を入れる）
			return AffiliationInfoOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					firstInfo, firstInfo);
		}
		val lastWorkTypeOfDaily = lastWorkTypeOfDailyOpt.get();

		// 月末の情報を作成
		val lastInfo = AggregateAffiliationInfo.of(lastInfoOfDaily.getEmploymentCode(),
				new WorkplaceId(lastInfoOfDaily.getWplID()), new JobTitleId(lastInfoOfDaily.getJobTitleID()),
				lastInfoOfDaily.getClsCode(), lastWorkTypeOfDaily.getWorkTypeCode());

		// 月別実績の所属情報を返す
		return AffiliationInfoOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate, firstInfo,
				lastInfo);
	}

	/**
	 * 時短日割適用日数
	 */
	private void TimeSavingDailyRateApplyDays() {

		// 月別実績の所属情報を取得
		val affiliationInfoOpt = this.aggregateResult.getAffiliationInfo();
		if (!affiliationInfoOpt.isPresent())
			return;

		// 月末の勤務情報を判断
		val lastInfo = affiliationInfoOpt.get().getLastInfo();
		if (lastInfo.getBusinessTypeCd().v().compareTo("0000002030") == 0) {

			// 任意項目50にセット
			this.aggregateResult.putAnyItemOrUpdate(AnyItemOfMonthly.of(this.employeeId, this.yearMonth, this.closureId,
					this.closureDate, 50, Optional.empty(), Optional.of(new AnyTimesMonth(20.67)), Optional.empty()));
		}
	}

	public static interface Require extends MonthlyCalculatingDailys.Require, MonthlyOldDatas.Require,
			AttendanceTimeOfMonthly.Require, VerticalTotalOfMonthly.Require, TotalCountByPeriod.Require,
			MonthlyCalculation.Require, InterimRemainOffPeriodCreateDataImpl.Require,
			GetDaysForCalcAttdRateImpl.Require, AbsenceReruitmentMngInPeriodQueryImpl.Require,
			BreakDayOffMngInPeriodQueryImpl.Require, SpecialLeaveManagementServiceImpl.Require,
			GetAnnAndRsvRemNumWithinPeriodImpl.Require, ExcessOutsideWorkMng.Require {
		// this.repositories.getWorkingConditionItem().getBySidAndPeriodOrderByStrD(employeeId,
		// monthPeriod);
		List<WorkingConditionItem> getBySidAndPeriodOrderByStrD(String employeeId, DatePeriod datePeriod);

		// this.repositories.getWorkingCondition().getByHistoryId(startHistoryId);
		Optional<WorkingCondition> getByHistoryId(String historyId);

		// this.repositories.getAffiliationInfoOfDaily().findByKey(this.employeeId,
		// datePeriod.start());
		Optional<AffiliationInforOfDailyPerfor> findAffiliationByKey(String employeeId, GeneralDate ymd);

		// this.repositories.getWorkTypeOfDaily().findByKey(this.employeeId,
		// datePeriod.end());
		Optional<WorkTypeOfDailyPerformance> findWorkTypeByKey(String employeeId, GeneralDate processingDate);

		// this.specialHolidayRepo.findByCompanyId(this.companyId);
		List<SpecialHoliday> findSpecialHolidayByCompanyId(String companyId);

		// this.editStateRepo.findByClosure(this.employeeId, this.yearMonth,
		// this.closureId, this.closureDate);
		List<EditStateOfMonthlyPerformance> findByClosure(String employeeId, YearMonth yearMonth, ClosureId closureId,
				ClosureDate closureDate);
	}

	private Require createRequireImpl() {
		return new AggregateMonthlyRecordServiceProc.Require() {
			@Override
			public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
				return repositories.getWorkingConditionItem().getBySidAndStandardDate(employeeId, baseDate);
			}

			@Override
			public List<PCLogOnInfoOfDaily> findsPcLongOnInfo(List<String> employeeIds, DatePeriod baseDate) {
				return repositories.getPCLogonInfoOfDaily().finds(employeeIds, baseDate);
			}

			@Override
			public List<AnyItemValueOfDaily> findsAnyItemValue(List<String> employeeIds, DatePeriod baseDate) {
				return repositories.getAnyItemValueOfDaily().finds(employeeIds, baseDate);
			}

			@Override
			public List<WorkTypeOfDailyPerformance> finds(List<String> employeeIds, DatePeriod baseDate) {
				return repositories.getWorkTypeOfDaily().finds(employeeIds, baseDate);
			}

			@Override
			public List<WorkInfoOfDailyPerformance> findWorkInfoByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public List<TimeLeavingOfDailyPerformance> findTimeLeavingbyPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getTimeLeavingOfDaily().findbyPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public List<TemporaryTimeOfDailyPerformance> findTemporarybyPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getTemporaryTimeOfDaily().findbyPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public List<SpecificDateAttrOfDailyPerfor> findSpecificDateByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getSpecificDateAttrOfDaily().findByPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public List<ReserveLeaveGrantRemainingData> findNotExp(String employeeId, String cId) {
				return repositories.getRsvLeaGrantRemData().findNotExp(employeeId, null);
			}

			@Override
			public List<AnnualLeaveGrantRemainingData> findNotExp(String employeeId) {
				return repositories.getAnnLeaGrantRemData().findNotExp(employeeId);
			}

			@Override
			public List<EmployeeDailyPerError> findEmployeeDailyPerErrorByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getEmployeeDailyError().findByPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public List<AttendanceTimeOfDailyPerformance> findAttendanceTimeByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public Optional<ShainNormalSetting> findShainNormalSetting(String cid, String empId, int year) {
				return repositories.getShainNormalSettingRepository().find(cid, empId, year);
			}

			@Override
			public Optional<ShainDeforLaborSetting> findShainDeforLaborSetting(String cid, String empId, int year) {
				return repositories.getShainDeforLaborSettingRepository().find(cid, empId, year);
			}

			@Override
			public Optional<WkpNormalSetting> findWkpNormalSetting(String cid, String wkpId, int year) {
				return repositories.getWkpNormalSettingRepository().find(cid, wkpId, year);
			}

			@Override
			public Optional<WkpDeforLaborSetting> findWkpDeforLaborSetting(String cid, String wkpId, int year) {
				return repositories.getWkpDeforLaborSettingRepository().find(cid, wkpId, year);
			}

			@Override
			public Optional<EmpNormalSetting> findEmpNormalSetting(String cid, String emplCode, int year) {
				return repositories.getEmpNormalSettingRepository().find(cid, emplCode, year);
			}

			@Override
			public Optional<EmpDeforLaborSetting> findEmpDeforLaborSetting(String cid, String emplCode, int year) {
				return repositories.getEmpDeforLaborSettingRepository().find(cid, emplCode, year);
			}

			@Override
			public Optional<ComNormalSetting> findComNormalSetting(String companyId, int year) {
				return repositories.getComNormalSettingRepository().find(companyId, year);
			}

			@Override
			public Optional<ComDeforLaborSetting> findComDeforLaborSetting(String companyId, int year) {
				return repositories.getComDeforLaborSettingRepository().find(companyId, year);
			}

			@Override
			public Optional<ShainFlexSetting> findShainFlexSetting(String cid, String empId, int year) {
				return repositories.getShainFlexSettingRepository().find(cid, empId, year);
			}

			@Override
			public Optional<EmpFlexSetting> findEmpFlexSetting(String cid, String emplCode, int year) {
				return repositories.getEmpFlexSettingRepository().find(cid, emplCode, year);
			}

			@Override
			public Optional<ComFlexSetting> findComFlexSetting(String companyId, int year) {
				return repositories.getComFlexSettingRepository().find(companyId, year);
			}

			@Override
			public Optional<UsageUnitSetting> findByCompany(String companyId) {
				return repositories.getUsageUnitSetRepo().findByCompany(companyId);
			}

			@Override
			public Optional<WkpFlexMonthActCalSet> findWkpFlexMonthActCalSet(String cid, String wkpId) {
				return repositories.getWkpFlexMonthActCalSetRepository().find(cid, wkpId);
			}

			@Override
			public Optional<EmpFlexMonthActCalSet> findEmpFlexMonthActCalSet(String cid, String empCode) {
				return repositories.getEmpFlexMonthActCalSetRepository().find(cid, empCode);
			}

			@Override
			public Optional<WkpDeforLaborMonthActCalSet> findWkpDeforLaborMonthActCalSet(String cid, String wkpId) {
				return repositories.getWkpDeforLaborMonthActCalSetRepository().find(companyId, wkpId);
			}

			@Override
			public Optional<EmpDeforLaborMonthActCalSet> findEmpDeforLaborMonthActCalSet(String cid, String empCode) {
				return repositories.getEmpDeforLaborMonthActCalSetRepository().find(companyId, empCode);
			}

			@Override
			public Optional<WkpRegulaMonthActCalSet> findWkpRegulaMonthActCalSet(String cid, String wkpId) {
				return repositories.getWkpRegulaMonthActCalSetRepository().find(companyId, wkpId);
			}

			@Override
			public Optional<EmpRegulaMonthActCalSet> findEmpRegulaMonthActCalSet(String cid, String empCode) {
				return repositories.getEmpRegulaMonthActCalSetRepository().find(companyId, empCode);
			}

			@Override
			public List<WorkingConditionItem> getBySidAndPeriodOrderByStrD(String employeeId, DatePeriod datePeriod) {
				return repositories.getWorkingConditionItem().getBySidAndPeriodOrderByStrD(employeeId, datePeriod);
			}

			@Override
			public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId,
					YearMonth yearMonth) {
				return repositories.getAttendanceTimeOfMonthly().findByYearMonthOrderByStartYmd(employeeId, yearMonth);
			}

			@Override
			public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
				return repositories.getWorkType().findByPK(companyId, workTypeCd);
			}

			@Override
			public Optional<WkpRegularLaborTime> findWkpRegularLaborTime(String cid, String wkpId) {
				return repositories.getWkpRegularLaborTime().find(cid, wkpId);
			}

			@Override
			public Optional<EmpRegularLaborTime> findEmpRegularLaborTimeById(String cid, String employmentCode) {
				return repositories.getEmpRegularWorkTime().findById(cid, employmentCode);
			}

			@Override
			public Optional<WkpTransLaborTime> findWkpTransLaborTime(String cid, String wkpId) {
				return repositories.getWkpTransLaborTime().find(cid, wkpId);
			}

			@Override
			public Optional<EmpTransLaborTime> findEmpTransLaborTime(String cid, String emplId) {
				return repositories.getEmpTransWorkTime().find(cid, emplId);
			}

			@Override
			public BasicAgreementSetting getBasicSet(String companyId, String employeeId, GeneralDate criteriaDate,
					WorkingSystem workingSystem) {
				return repositories.getAgreementDomainService().getBasicSet(companyId, employeeId, criteriaDate,
						workingSystem);
			}

			@Override
			public Optional<AgreementMonthSetting> findByKey(String employeeId, YearMonth yearMonth) {
				return repositories.getAgreementMonthSet().findByKey(employeeId, yearMonth);
			}

			@Override
			public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {
				return repositories.getPredetermineTimeSet().findByWorkTimeCode(companyId, workTimeCode);
			}

			@Override
			public Optional<EmpRegularLaborTime> findById(String cid, String employmentCode) {
				return repositories.getEmpRegularWorkTime().findById(cid, employmentCode);
			}

			@Override
			public Optional<ComTransLaborTime> findcomTransLaborTime(String companyId) {
				return repositories.getComTransLaborTime().find(companyId);
			}

			@Override
			public Optional<ComRegularLaborTime> findcomRegularLaborTime(String companyId) {
				return repositories.getComRegularLaborTime().find(companyId);
			}

			@Override
			public Optional<ShainRegularLaborTime> findShainRegularLaborTime(String Cid, String EmpId) {
				return repositories.getShainRegularWorkTime().find(Cid, EmpId);
			}

			@Override
			public Optional<ShainTransLaborTime> findShainTransLaborTime(String cid, String empId) {
				return repositories.getShainTransLaborTime().find(cid, empId);
			}

			@Override
			public Optional<WorkTimeSetting> findWorkTimeSettingByCode(String companyId, String workTimeCode) {
				return repositories.getWorkTimeSetRepository().findByCode(companyId, workTimeCode);
			}

			@Override
			public Optional<FlowWorkSetting> findFlowWorkSetting(String companyId, String workTimeCode) {
				return repositories.getFlowWorkSetRepository().find(companyId, workTimeCode);
			}

			@Override
			public Optional<FlexWorkSetting> findFlexWorkSetting(String companyId, String workTimeCode) {
				return repositories.getFlexWorkSetRepository().find(companyId, workTimeCode);
			}

			@Override
			public Optional<FixedWorkSetting> findFixedWorkSettingByKey(String companyId, String workTimeCode) {
				return repositories.getFixedWorkSetRepository().findByKey(companyId, workTimeCode);
			}

			@Override
			public Optional<DiffTimeWorkSetting> findDiffTimeWorkSetting(String companyId, String workTimeCode) {
				return repositories.getDiffWorkSetRepository().find(companyId, workTimeCode);
			}

			@Override
			public Optional<WorkingCondition> getByHistoryId(String historyId) {
				return repositories.getWorkingCondition().getByHistoryId(historyId);
			}

			@Override
			public Optional<InterimRecMng> getReruitmentById(String recId) {
				return repositories.getInterimRecAbasMngRepository().getReruitmentById(recId);
			}

			@Override
			public List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData,
					RemainType remainType) {
				return repositories.getInterimRemainRepository().getRemainBySidPriod(employeeId, dateData, remainType);
			}

			@Override
			public List<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
				return repositories.getInterimRecAbasMngRepository().getRecOrAbsMng(interimId, false, mngAtr);
			}

			@Override
			public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd,
					double unOffseDays) {
				return repositories.getSubstitutionOfHDManaDataRepository().getByYmdUnOffset(cid, sid, ymd,
						unOffseDays);
			}

			@Override
			public List<PayoutManagementData> getByUnUseState(String cid, String sid, GeneralDate ymd, double unUse,
					DigestionAtr state) {
				return repositories.getPayoutManagementDataRepository().getByUnUseState(cid, sid, ymd, unUse, state);
			}

			@Override
			public Optional<InterimAbsMng> getAbsById(String absId) {
				return repositories.getInterimRecAbasMngRepository().getAbsById(absId);
			}

			@Override
			public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
				return repositories.getClosureEmployment().findByEmploymentCD(companyID, employmentCD);
			}

			@Override
			public List<Closure> findAllUse(String companyId) {
				return repositories.getClosure().findAllUse(companyId);
			}

			@Override
			public Optional<ComSubstVacation> findComSubstVacationById(String companyId) {
				return repositories.getSubstVacationMng().findById(companyId);
			}

			@Override
			public Optional<EmpSubstVacation> findEmpSubstVacationById(String companyId, String contractTypeCode) {
				return repositories.getEmpSubstVacationRepository().findById(companyId, contractTypeCode);
			}

			@Override
			public CompensatoryLeaveEmSetting findCompensatoryLeaveEmSetting(String companyId, String employmentCode) {
				return repositories.getCompensLeaveEmSetRepository().find(companyId, employmentCode);
			}

			@Override
			public CompensatoryLeaveComSetting findCompensatoryLeaveComSetting(String companyId) {
				return repositories.getCompensLeaveMng().find(companyId);
			}

			@Override
			public Optional<Closure> findClosureById(String companyId, int closureId) {
				return repositories.getClosure().findById(companyId, closureId);
			}

			@Override
			public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
				return repositories.getAnnLeaEmpBasicInfo().get(employeeId);
			}

			@Override
			public List<LengthServiceTbl> findLengthServiceTblByCode(String companyId, String yearHolidayCode) {
				return repositories.getLengthService().findByCode(companyId, yearHolidayCode);
			}

			@Override
			public Optional<GrantHdTblSet> findGrantHdTblSetByCode(String companyId, String yearHolidayCode) {
				return repositories.getYearHoliday().findByCode(companyId, yearHolidayCode);
			}

			@Override
			public AnnualPaidLeaveSetting findAnnualPaidLeaveSettingByCompanyId(String companyId) {
				return repositories.getAnnualPaidLeaveSet().findByCompanyId(companyId);
			}

			@Override
			public List<AnnLeaRemNumEachMonth> findByClosurePeriod(String employeeId, DatePeriod closurePeriod) {
				return repositories.getAnnLeaRemNumEachMonth().findByClosurePeriod(employeeId, closurePeriod);
			}

			@Override
			public Optional<AnnualLeaveEmpBasicInfo> getAnnualLeaveEmpBasicInfo(String employeeId) {
				return repositories.getAnnLeaEmpBasicInfo().get(employeeId);
			}

			@Override
			public Optional<OperationStartSetDailyPerform> findByCid(CompanyId companyId) {
				return repositories.getOperationStartSet().findByCid(companyId);
			}

			@Override
			public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId) {
				return repositories.getClosureStatusMng().getLatestByEmpId(employeeId);
			}

			@Override
			public Optional<AnnualLeaveMaxData> getAnnualLeaveMaxData(String employeeId) {
				return repositories.getAnnLeaMaxDataRepository().get(employeeId);
			}

			@Override
			public Optional<GrantHdTbl> findGrantHdTbl(String companyId, int conditionNo, String yearHolidayCode,
					int grantNum) {
				return repositories.getGrantYearHolidayRepository().find(companyId, conditionNo, yearHolidayCode,
						grantNum);
			}

			@Override
			public Optional<TmpResereLeaveMng> getById(String resereMngId) {
				return repositories.getTmpResereLeaveMngRepository().getById(resereMngId);
			}

			@Override
			public Optional<RetentionYearlySetting> findRetentionYearlySettingByCompanyId(String companyId) {
				return repositories.getRetentionYearlySet().findByCompanyId(companyId);
			}

			@Override
			public Optional<GrantHdTbl> find(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
				return repositories.getGrantYearHolidayRepository().find(companyId, conditionNo, yearHolidayCode,
						grantNum);
			}

			@Override
			public Optional<EmptYearlyRetentionSetting> findEmptYearlyRetentionSetting(String companyId,
					String employmentCode) {
				return repositories.getEmploymentSet().find(companyId, employmentCode);
			}

			@Override
			public Optional<SpecialLeaveGrantRemainingData> getBySpecialId(String specialId) {
				return repositories.getSpecialLeaveGrantRepository().getBySpecialId(specialId);
			}

			@Override
			public Optional<SpecialLeaveBasicInfo> getBySidLeaveCdUser(String sid, int spLeaveCD, UseAtr use) {
				return repositories.getSpecialLeaveBasicInfoRepository().getBySidLeaveCdUser(sid, spLeaveCD, use);
			}

			@Override
			public List<SpecialLeaveGrantRemainingData> getByPeriodStatus(String sid, int specialLeaveCode,
					LeaveExpirationStatus expirationStatus, GeneralDate grantDate, GeneralDate deadlineDate) {
				return repositories.getSpecialLeaveGrantRepository().getByPeriodStatus(sid, specialLeaveCode,
						expirationStatus, grantDate, deadlineDate);
			}

			@Override
			public List<SpecialLeaveGrantRemainingData> getByNextDate(String sid, int speCode, DatePeriod datePriod,
					GeneralDate startDate, LeaveExpirationStatus expirationStatus) {
				return repositories.getSpecialLeaveGrantRepository().getByNextDate(sid, speCode, datePriod, startDate,
						expirationStatus);
			}

			@Override
			public Optional<SpecialHoliday> findBySingleCD(String companyID, int specialHolidayCD) {
				return repositories.getSpecialHolidayRepository().findBySingleCD(companyID, specialHolidayCD);
			}

			@Override
			public List<InterimSpecialHolidayMng> findById(String mngId) {
				return repositories.getInterimSpecialHolidayMngRepository().findById(mngId);
			}

			@Override
			public Optional<GrantDateTbl> findByCodeAndIsSpecified(String companyId, int specialHolidayCode) {
				return repositories.getGrantDateTblRepository().findByCodeAndIsSpecified(companyId, specialHolidayCode);
			}

			@Override
			public List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode,
					String grantDateCode) {
				return repositories.getGrantDateTblRepository().findElapseByGrantDateCd(companyId, specialHolidayCode,
						grantDateCode);
			}

			@Override
			public List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(employeeId, datePeriod);
			}

			@Override
			public CompensatoryLeaveComSetting find(String companyId) {
				return repositories.getCompensLeaveMng().find(companyId);
			}

			@Override
			public Optional<WorkType> findByDeprecated(String companyId, String workTypeCd) {
				return repositories.getWorkType().findByDeprecated(companyId, workTypeCd);
			}

			@Override
			public List<Integer> findBySphdSpecLeave(String cid, int sphdSpecLeaveNo) {
				return repositories.getSpecialHolidayRepository().findBySphdSpecLeave(cid, sphdSpecLeaveNo);
			}

			@Override
			public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd) {
				return repositories.getWorkType().findByPK(companyId, workTypeCd);
			}

			@Override
			public Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
					ClosureDate closureDate) {
				return repositories.getAttendanceTimeOfMonthly().find(employeeId, yearMonth, closureId, closureDate);
			}

			@Override
			public List<AnyItemOfMonthly> findByMonthlyAndClosure(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getAnyItemOfMonthly().findByMonthlyAndClosure(employeeId, yearMonth, closureId,
						closureDate);
			}

			@Override
			public Optional<AnnLeaRemNumEachMonth> findAnnLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getAnnLeaRemNumEachMonth().find(employeeId, yearMonth, closureId, closureDate);
			}

			@Override
			public Optional<RsvLeaRemNumEachMonth> findRsvLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getRsvLeaRemNumEachMonth().find(employeeId, yearMonth, closureId, closureDate);
			}

			@Override
			public Optional<AbsenceLeaveRemainData> findAbsenceLeaveRemainData(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getAbsenceLeaveRemainData().find(employeeId, yearMonth, closureId, closureDate);
			}

			@Override
			public Optional<MonthlyDayoffRemainData> findMonthDayoffRemainData(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getMonthlyDayoffRemainData().find(employeeId, yearMonth, closureId, closureDate);
			}

			@Override
			public List<SpecialHolidayRemainData> findSpecialHolidayRemainData(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return repositories.getSpecialHolidayRemainData().find(employeeId, yearMonth, closureId, closureDate);
			}

			@Override
			public List<LeaveManagementData> getBySidYmd(String cid, String sid, GeneralDate ymd, DigestionAtr state) {
				return repositories.getLeaveManaDataRepository().getBySidYmd(cid, sid, ymd, state);
			}

			@Override
			public List<CompensatoryDayOffManaData> getBySidYmd(String cid, String sid, GeneralDate ymd) {
				return repositories.getComDayOffManaDataRepository().getBySidYmd(cid, sid, ymd);
			}

			@Override
			public Optional<InterimBreakMng> getBreakManaBybreakMngId(String breakManaId) {
				return repositories.getInterimBreakDayOffMngRepository().getBreakManaBybreakMngId(breakManaId);
			}

			@Override
			public Optional<InterimDayOffMng> getDayoffById(String dayOffManaId) {
				return repositories.getInterimBreakDayOffMngRepository().getDayoffById(dayOffManaId);
			}

			@Override
			public List<InterimBreakDayOffMng> getBreakDayOffMng(String mngId, boolean breakDay,
					DataManagementAtr mngAtr) {
				return repositories.getInterimBreakDayOffMngRepository().getBreakDayOffMng(mngId, breakDay, mngAtr);
			}

			@Override
			public Optional<AffiliationInforOfDailyPerfor> findAffiliationByKey(String employeeId, GeneralDate ymd) {
				return repositories.getAffiliationInfoOfDaily().findByKey(employeeId, ymd);
			}

			@Override
			public Optional<WorkTypeOfDailyPerformance> findWorkTypeByKey(String employeeId,
					GeneralDate processingDate) {
				return repositories.getWorkTypeOfDaily().findByKey(employeeId, processingDate);
			}

			@Override
			public List<SpecialHoliday> findSpecialHolidayByCompanyId(String companyId) {
				return specialHolidayRepo.findByCompanyId(companyId);
			}

			@Override
			public List<EditStateOfMonthlyPerformance> findByClosure(String employeeId, YearMonth yearMonth,
					ClosureId closureId, ClosureDate closureDate) {
				return editStateRepo.findByClosure(employeeId, yearMonth, closureId, closureDate);
			}
		};
	}
	
}