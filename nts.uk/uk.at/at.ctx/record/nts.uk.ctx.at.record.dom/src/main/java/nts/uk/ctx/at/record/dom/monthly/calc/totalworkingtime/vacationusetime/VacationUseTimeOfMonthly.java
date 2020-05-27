package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の休暇使用時間
 * @author shuichi_ishida
 */
@Getter
public class VacationUseTimeOfMonthly implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 年休 */
	private AnnualLeaveUseTimeOfMonthly annualLeave;
	/** 積立年休 */
	private RetentionYearlyUseTimeOfMonthly retentionYearly;
	/** 特別休暇 */
	private SpecialHolidayUseTimeOfMonthly specialHoliday;
	/** 代休 */
	private CompensatoryLeaveUseTimeOfMonthly compensatoryLeave;
	
	/**
	 * コンストラクタ
	 */
	public VacationUseTimeOfMonthly(){
		
		this.annualLeave = new AnnualLeaveUseTimeOfMonthly();
		this.retentionYearly = new RetentionYearlyUseTimeOfMonthly();
		this.specialHoliday = new SpecialHolidayUseTimeOfMonthly();
		this.compensatoryLeave = new CompensatoryLeaveUseTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param retentionYearly 積立年休
	 * @param specialHoliday 特別休暇
	 * @param compensatoryLeave 代休
	 * @return 月別実績の休暇使用時間
	 */
	public static VacationUseTimeOfMonthly of(
			AnnualLeaveUseTimeOfMonthly annualLeave,
			RetentionYearlyUseTimeOfMonthly retentionYearly,
			SpecialHolidayUseTimeOfMonthly specialHoliday,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeave){

		val domain = new VacationUseTimeOfMonthly();
		domain.annualLeave = annualLeave;
		domain.retentionYearly = retentionYearly;
		domain.specialHoliday = specialHoliday;
		domain.compensatoryLeave = compensatoryLeave;
		return domain;
	}

	@Override
	public VacationUseTimeOfMonthly clone() {
		VacationUseTimeOfMonthly cloned = new VacationUseTimeOfMonthly();
		try {
			cloned.annualLeave = this.annualLeave.clone();
			cloned.retentionYearly = this.retentionYearly.clone();
			cloned.specialHoliday = this.specialHoliday.clone();
			cloned.compensatoryLeave = this.compensatoryLeave.clone();
		}
		catch (Exception e){
			throw new RuntimeException("VacationUseTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 休暇使用時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param workInfoOfDailyMap 日別実績の勤務情報リスト
	 * @param companySets 月別集計で必要な会社別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void confirm(
			DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap,
			Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailyMap,
			MonAggrCompanySettings companySets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		val require = createRequireImpl(repositories);

		confirmRequire(require, datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap, companySets, repositories);
	}
	
	public void confirmRequire(
			Require require,
			DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap,
			Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailyMap,
			MonAggrCompanySettings companySets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 年休使用時間を確認する
		this.annualLeave.confirm(datePeriod, attendanceTimeOfDailyMap);

		// 積立年休使用時間を確認する
		this.retentionYearly.confirm(datePeriod, attendanceTimeOfDailyMap);

		// 特別休暇使用時間を確認する
		this.specialHoliday.confirm(datePeriod, attendanceTimeOfDailyMap);

		// 代休使用時間を確認する
		this.compensatoryLeave.confirmRequire(require, datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap,
				companySets, repositories);
	}
	
	/**
	 * 休暇使用時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		// 年休使用時間を集計する
		this.annualLeave.aggregate(datePeriod);
		
		// 積立年休使用時間を集計する
		this.retentionYearly.aggregate(datePeriod);
		
		// 特別年休使用時間を集計する
		this.specialHoliday.aggregate(datePeriod);
		
		// 代休使用時間を集計する
		this.compensatoryLeave.aggregate(datePeriod);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(VacationUseTimeOfMonthly target){
		
		this.annualLeave.addMinuteToUseTime(target.annualLeave.getUseTime().v());
		this.retentionYearly.addMinuteToUseTime(target.retentionYearly.getUseTime().v());
		this.specialHoliday.addMinuteToUseTime(target.specialHoliday.getUseTime().v());
		this.compensatoryLeave.addMinuteToUseTime(target.compensatoryLeave.getUseTime().v());
	}
	
	public static interface Require extends CompensatoryLeaveUseTimeOfMonthly.Require{

	}

	private Require createRequireImpl(RepositoriesRequiredByMonthlyAggr repositories) {
		return new VacationUseTimeOfMonthly.Require() {
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
			public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {
				return repositories.getPredetermineTimeSet().findByWorkTimeCode(companyId, workTimeCode);
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
		};
	}
}