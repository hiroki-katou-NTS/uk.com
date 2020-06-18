package nts.uk.ctx.at.record.dom.monthly;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
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
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Getter
public class AttendanceTimeOfMonthly extends AggregateRoot implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 社員ID */
	private final String employeeId;
	/** 年月 */
	private final YearMonth yearMonth;
	/** 締めID */
	private final ClosureId closureId;
	/** 締め日付 */
	private final ClosureDate closureDate;

	/** 期間 */
	private DatePeriod datePeriod;
	/** 月の計算 */
	@Setter
	private MonthlyCalculation monthlyCalculation;
	/** 時間外超過 */
	@Setter
	private ExcessOutsideWorkOfMonthly excessOutsideWork;
	/** 縦計 */
	@Setter
	private VerticalTotalOfMonthly verticalTotal;
	/** 回数集計 */
	@Setter
	private TotalCountByPeriod totalCount;
	/** 集計日数 */
	@Setter
	private AttendanceDaysMonth aggregateDays;

	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 */
	public AttendanceTimeOfMonthly(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.datePeriod = datePeriod;
		this.monthlyCalculation = new MonthlyCalculation();
		this.excessOutsideWork = new ExcessOutsideWorkOfMonthly();
		this.verticalTotal = new VerticalTotalOfMonthly();
		this.totalCount = new TotalCountByPeriod();
		this.aggregateDays = new AttendanceDaysMonth((double)(datePeriod.start().daysTo(datePeriod.end()) + 1));
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param monthlyCalculation 月の計算
	 * @param excessOutsideWork 時間外超過
	 * @param verticalTotal 縦計
	 * @param totalCount 回数集計
	 * @param aggregateDays 集計日数
	 * @return 月別実績の勤怠時間
	 */
	public static AttendanceTimeOfMonthly of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod datePeriod,
			MonthlyCalculation monthlyCalculation,
			ExcessOutsideWorkOfMonthly excessOutsideWork,
			VerticalTotalOfMonthly verticalTotal,
			TotalCountByPeriod totalCount,
			AttendanceDaysMonth aggregateDays){
		
		val domain = new AttendanceTimeOfMonthly(employeeId, yearMonth, closureId, closureDate, datePeriod);
		domain.monthlyCalculation = monthlyCalculation;
		domain.excessOutsideWork = excessOutsideWork;
		domain.verticalTotal = verticalTotal;
		domain.totalCount = totalCount;
		domain.aggregateDays = aggregateDays;
		return domain;
	}

	/**
	 * 集計準備
	 * @param companyId 会社ID
	 * @param datePeriod 期間
	 * @param workingConditionItem 労働制
	 * @param startWeekNo 開始週NO
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @param monthlyOldDatas 集計前の月別実績データ
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void prepareAggregation(
			String companyId,
			DatePeriod datePeriod,
			WorkingConditionItem workingConditionItem,
			int startWeekNo,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalcDailys,
			MonthlyOldDatas monthlyOldDatas,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		val require = new AttendanceTimeOfMonthly.Require() {
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
			public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
				return repositories.getWorkingConditionItem().getBySidAndStandardDate(employeeId, baseDate);
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
		};

		val cacheCarrier = new CacheCarrier();

		prepareAggregationRequire(require, cacheCarrier, companyId, 
				datePeriod, workingConditionItem, startWeekNo, companySets, 
				employeeSets, monthlyCalcDailys, monthlyOldDatas, repositories);

	}
	public void prepareAggregationRequire(
			Require require,
			CacheCarrier cacheCarrier,
			String companyId,
			DatePeriod datePeriod,
			WorkingConditionItem workingConditionItem,
			int startWeekNo,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalcDailys,
			MonthlyOldDatas monthlyOldDatas,
			RepositoriesRequiredByMonthlyAggr repositories){
		this.monthlyCalculation.prepareAggregationRequire(require, cacheCarrier, companyId, this.employeeId, this.yearMonth,
				this.closureId, this.closureDate, datePeriod, workingConditionItem,
				startWeekNo, companySets, employeeSets, monthlyCalcDailys, monthlyOldDatas, repositories);
	}
	
	public static interface Require extends MonthlyCalculation.Require {

	}

	/**
	 * 等しいかどうか
	 * @param target 比較対象
	 * @return true:等しい、false:等しくない
	 */
	public boolean equals(AttendanceTimeOfMonthly target) {
		
		return (this.employeeId == target.employeeId &&
				this.yearMonth.equals(target.yearMonth) &&
				this.closureId.value == target.closureId.value &&
				this.closureDate.getClosureDay().equals(target.closureDate.getClosureDay()) &&
				this.closureDate.getLastDayOfMonth() == target.closureDate.getLastDayOfMonth());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AttendanceTimeOfMonthly target){

		GeneralDate startDate = this.datePeriod.start();
		GeneralDate endDate = this.datePeriod.end();
		if (startDate.after(target.datePeriod.start())) startDate = target.datePeriod.start();
		if (endDate.before(target.datePeriod.end())) endDate = target.datePeriod.end();
		this.datePeriod = new DatePeriod(startDate, endDate);
		
		this.monthlyCalculation.sum(target.monthlyCalculation);
		this.excessOutsideWork.sum(target.excessOutsideWork);
		this.verticalTotal.sum(target.verticalTotal);
		this.totalCount.sum(target.totalCount);
		
		this.aggregateDays = this.aggregateDays.addDays(target.aggregateDays.v());
	}
}