package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerRepository;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.pererror.CreatePerErrorsFromLeaveErrors;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.GetDaysForCalcAttdRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateInterimAnnualMngData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.service.RemainNumberCreateInformation;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
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
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
@Stateless
public class AggregateMonthlyRecordServiceImpl implements AggregateMonthlyRecordService {

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** 月次処理用の暫定残数管理データを作成する */
	@Inject
	private InterimRemainOffMonthProcess interimRemOffMonth;
	/** 残数作成元情報を作成する */
	@Inject
	private RemainNumberCreateInformation remNumCreateInfo;
	/** 指定期間の暫定残数管理データを作成する */
	@Inject
	private InterimRemainOffPeriodCreateData periodCreateData;
	/** 暫定年休管理データを作成する */
	@Inject
	private CreateInterimAnnualMngData createInterimAnnual;
	/** 期間中の年休積休残数を取得 */
	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod;
	/** 期間内の振出振休残数を取得する */
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absenceRecruitMng;
	/** 期間内の休出代休残数を取得する */
	@Inject
	private BreakDayOffMngInPeriodQuery breakDayoffMng;
	/** 出勤率計算用日数を取得する */
	@Inject
	private GetDaysForCalcAttdRate getDaysForCalcAttdRate;
	/** 特別休暇 */
	@Inject
	private SpecialHolidayRepository specialHolidayRepo;
	/** 期間内の特別休暇残を集計する */
	@Inject
	private SpecialLeaveManagementService specialLeaveMng;
	/** 休暇残数エラーから月別残数エラー一覧を作成する */
	@Inject
	private CreatePerErrorsFromLeaveErrors createPerErrorFromLeaveErrors;
	/** 月別実績の編集状態 */
	@Inject
	private EditStateOfMonthlyPerRepository editStateRepo;
	
	/** 並列処理用 */
	@Resource
	private ManagedExecutorService executorService;
	
	/** 集計処理　（アルゴリズム） */
	@Override
	public AggregateMonthlyRecordValue aggregate(
			String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult,
			Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork) {
		
		AggregateMonthlyRecordServiceProc proc = new AggregateMonthlyRecordServiceProc(
				this.repositories,
				this.interimRemOffMonth,
				this.remNumCreateInfo,
				this.periodCreateData,
				this.createInterimAnnual,
				this.getAnnAndRsvRemNumWithinPeriod,
				this.absenceRecruitMng,
				this.breakDayoffMng,
				this.getDaysForCalcAttdRate,
				this.specialHolidayRepo,
				this.specialLeaveMng,
				this.createPerErrorFromLeaveErrors,
				this.editStateRepo,
				this.executorService);
		
		return proc.aggregate(companyId, employeeId, yearMonth, closureId, closureDate,
				datePeriod, prevAggrResult, prevAbsRecResultOpt, prevBreakDayOffResultOpt, prevSpecialLeaveResultMap,
				companySets, employeeSets, dailyWorks, monthlyWork);
	}
	
	/** 集計処理　（アルゴリズム） */
	@Override
	public AggregateMonthlyRecordValue aggregate(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult, Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork,
			Boolean remainingProcAtr) {
		
		val require = createRequireImpl();
		val cacheCarrier = new CacheCarrier();
		return aggregateRequire(require, cacheCarrier, companyId, employeeId, yearMonth, closureId, closureDate, 
				datePeriod, prevAggrResult, prevAbsRecResultOpt, prevBreakDayOffResultOpt, prevSpecialLeaveResultMap, 
				companySets, employeeSets, dailyWorks, monthlyWork, remainingProcAtr);
	}
	@Override
	public AggregateMonthlyRecordValue aggregateRequire(Require require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult, Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork,
			Boolean remainingProcAtr) {
		
		AggregateMonthlyRecordServiceProc proc = new AggregateMonthlyRecordServiceProc(
				this.repositories,
				this.interimRemOffMonth,
				this.remNumCreateInfo,
				this.periodCreateData,
				this.createInterimAnnual,
				this.getAnnAndRsvRemNumWithinPeriod,
				this.absenceRecruitMng,
				this.breakDayoffMng,
				this.getDaysForCalcAttdRate,
				this.specialHolidayRepo,
				this.specialLeaveMng,
				this.createPerErrorFromLeaveErrors,
				this.editStateRepo,
				this.executorService);
		
		return proc.aggregate(require, cacheCarrier, companyId, employeeId, yearMonth, closureId, closureDate,
				datePeriod, prevAggrResult, prevAbsRecResultOpt, prevBreakDayOffResultOpt, prevSpecialLeaveResultMap,
				companySets, employeeSets, dailyWorks, monthlyWork, remainingProcAtr);
	}

	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> mapInterimRemainData(String cid, String sid,
			DatePeriod datePeriod) {
		AggregateMonthlyRecordServiceProc proc = new AggregateMonthlyRecordServiceProc(
				this.repositories,
				this.interimRemOffMonth,
				this.remNumCreateInfo,
				this.periodCreateData,
				this.createInterimAnnual,
				this.getAnnAndRsvRemNumWithinPeriod,
				this.absenceRecruitMng,
				this.breakDayoffMng,
				this.getDaysForCalcAttdRate,
				this.specialHolidayRepo,
				this.specialLeaveMng,
				this.createPerErrorFromLeaveErrors,
				this.editStateRepo,
				this.executorService);
		proc.setMonthlyCalculatingDailys(MonthlyCalculatingDailys.loadData(
				sid, datePeriod, Optional.empty(), this.repositories));
		proc.setCompanyId(cid);
		proc.setEmployeeId(sid);
		Optional<ComSubstVacation> absSettingOpt = repositories.getSubstVacationMng().findById(cid);
		CompensatoryLeaveComSetting dayOffSetting = repositories.getCompensLeaveMng().find(cid);
		MonAggrCompanySettings comSetting = new MonAggrCompanySettings();
		comSetting.setAbsSettingOpt(absSettingOpt);
		comSetting.setDayOffSetting(dayOffSetting);
		proc.setCompanySets(comSetting);
		proc.createDailyInterimRemainMngs(datePeriod);
		return proc.getDailyInterimRemainMngs();
	}	
	
	public static interface Require extends AggregateMonthlyRecordServiceProc.Require{

	}

	private Require createRequireImpl() {
		return new AggregateMonthlyRecordServiceImpl.Require() {
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
			public List<WorkInfoOfDailyPerformance> findWorkInfoByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
				return repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(employeeId, datePeriod);
			}
			@Override
			public List<TimeLeavingOfDailyPerformance> findTimeLeavingbyPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return repositories.getTimeLeavingOfDaily().findbyPeriodOrderByYmd(employeeId, datePeriod);
			}
			@Override
			public List<TemporaryTimeOfDailyPerformance> findTemporarybyPeriodOrderByYmd(String employeeId,DatePeriod datePeriod) {
				return repositories.getTemporaryTimeOfDaily().findbyPeriodOrderByYmd(employeeId, datePeriod);
			}
			@Override
			public List<SpecificDateAttrOfDailyPerfor> findSpecificDateByPeriodOrderByYmd(String employeeId,DatePeriod datePeriod) {
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
			public List<EmployeeDailyPerError> findEmployeeDailyPerErrorByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
				return repositories.getEmployeeDailyError().findByPeriodOrderByYmd(employeeId, datePeriod);
			}
			@Override
			public List<AttendanceTimeOfDailyPerformance> findAttendanceTimeByPeriodOrderByYmd(String employeeId,DatePeriod datePeriod) {
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
				return repositories.getWkpDeforLaborMonthActCalSetRepository().find(cid, wkpId);
			}
			@Override
			public Optional<EmpDeforLaborMonthActCalSet> findEmpDeforLaborMonthActCalSet(String cid, String empCode) {
				return repositories.getEmpDeforLaborMonthActCalSetRepository().find(cid, empCode);
			}
			@Override
			public Optional<WkpRegulaMonthActCalSet> findWkpRegulaMonthActCalSet(String cid, String wkpId) {
				return repositories.getWkpRegulaMonthActCalSetRepository().find(cid, wkpId);
			}
			@Override
			public Optional<EmpRegulaMonthActCalSet> findEmpRegulaMonthActCalSet(String cid, String empCode) {
				return repositories.getEmpRegulaMonthActCalSetRepository().find(cid, empCode);
			}
			@Override
			public List<WorkingConditionItem> getBySidAndPeriodOrderByStrD(String employeeId, DatePeriod datePeriod) {
				return repositories.getWorkingConditionItem().getBySidAndPeriodOrderByStrD(employeeId, datePeriod);
			}
			@Override
			public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId,YearMonth yearMonth) {
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
			public BasicAgreementSetting getBasicSet(String companyId, String employeeId, GeneralDate criteriaDate,WorkingSystem workingSystem) {
				return repositories.getAgreementDomainService().getBasicSet(companyId, employeeId, criteriaDate, workingSystem);
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
			public List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType) {
				return repositories.getInterimRemainRepository().getRemainBySidPriod(employeeId, dateData, remainType);
			}
			@Override
			public List<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
				return repositories.getInterimRecAbasMngRepository().getRecOrAbsMng(interimId, false, mngAtr);
			}
			@Override
			public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd,
					double unOffseDays) {
				return repositories.getSubstitutionOfHDManaDataRepository().getByYmdUnOffset(cid, sid, ymd, unOffseDays);
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
			public Optional<GrantHdTbl> findGrantHdTbl(String companyId, int conditionNo, String yearHolidayCode,int grantNum) {
				return repositories.getGrantYearHolidayRepository().find(companyId, conditionNo, yearHolidayCode, grantNum);
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
				return repositories.getGrantYearHolidayRepository().find(companyId, conditionNo, yearHolidayCode, grantNum);
			}
			@Override
			public Optional<EmptYearlyRetentionSetting> findEmptYearlyRetentionSetting(String companyId, String employmentCode) {
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
				return repositories.getSpecialLeaveGrantRepository().getByPeriodStatus(sid,specialLeaveCode,expirationStatus,grantDate,deadlineDate);
			}
			@Override
			public List<SpecialLeaveGrantRemainingData> getByNextDate(String sid, int speCode, DatePeriod datePriod,
					GeneralDate startDate, LeaveExpirationStatus expirationStatus) {
				return repositories.getSpecialLeaveGrantRepository().getByNextDate(sid, speCode,datePriod, startDate, expirationStatus);
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
			public List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode, String grantDateCode) {
				return repositories.getGrantDateTblRepository().findElapseByGrantDateCd(companyId, specialHolidayCode, grantDateCode);
			}
			@Override
			public List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
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
				return repositories.getAnyItemOfMonthly().findByMonthlyAndClosure(employeeId, yearMonth, closureId, closureDate);
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