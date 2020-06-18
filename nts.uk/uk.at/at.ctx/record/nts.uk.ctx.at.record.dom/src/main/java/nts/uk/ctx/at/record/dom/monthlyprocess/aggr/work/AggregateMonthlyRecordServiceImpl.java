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
}