package nts.uk.ctx.at.record.dom.remainingnumber.annualleave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 実装：出勤率計算用日数を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetDaysForCalcAttdRateImpl implements GetDaysForCalcAttdRate {

	/** 日別実績の勤務情報の取得 */
	@Inject
	private WorkInformationRepository workInformationOfDailyRepo;
	/** 勤務情報の取得 */
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	/*require*/
	@Inject
	private WkpTransLaborTimeRepository wkpTransLaborTime;
	@Inject
	private WkpRegularLaborTimeRepository wkpRegularLaborTime;
	@Inject
	private EmpTransWorkTimeRepository empTransWorkTime;
	@Inject
	private EmpRegularWorkTimeRepository empRegularWorkTime;
	@Inject
	private WorkTypeRepository workType;
	@Inject
	private PredetemineTimeSettingRepository predetermineTimeSet;
	@Inject
	public WorkTimeSettingRepository workTimeSet;
	@Inject
	public FixedWorkSettingRepository fixedWorkSet;
	@Inject
	public FlowWorkSettingRepository flowWorkSet;
	@Inject
	public DiffTimeWorkSettingRepository diffWorkSet;
	@Inject
	public FlexWorkSettingRepository flexWorkSet;
	/*require*/

	private static Object NullObject = new Object();
	
	/** 日別実績から出勤率計算用日数を取得 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CalYearOffWorkAttendRate algorithm(Require require, String companyId, String employeeId, DatePeriod period) {
		
		double prescribedDays = 0.0;
		double workingDays = 0.0;
		double deductedDays = 0.0;
		
		Map<String, Object> workTypes = new HashMap<>();
		
		// 「日別実績の勤務情報」を取得
		val workInfos = require.findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(employeeId, period);
		for (val workInfo : workInfos){
			if (workInfo.getRecordInfo() == null) continue;
			if (workInfo.getRecordInfo().getWorkTypeCode() == null) continue;
			val workTypeCode = workInfo.getRecordInfo().getWorkTypeCode().v();
		
			// 「勤務種類」を取得
			WorkType workType = null;
			if (workTypes.containsKey(workTypeCode)){
				if (workTypes.get(workTypeCode) != NullObject) workType = (WorkType)workTypes.get(workTypeCode);
			}
			else {
				val workTypeOpt = require.findByPK(companyId, workTypeCode);
				if (workTypeOpt.isPresent()){
					workType = workTypeOpt.get();
					workTypes.putIfAbsent(workTypeCode, workType);
				}
				else {
					workTypes.putIfAbsent(workTypeCode, NullObject);
				}
			}
			if (workType == null) continue;
			
			// 勤務種類を判断してカウント数を取得
			WorkTypeDaysCountTable daysCount = new WorkTypeDaysCountTable(
					workType, new VacationAddSet(), Optional.empty());
			
			// 所定日数に取得したカウント数を加算
			prescribedDays += daysCount.getPredetermineDays().v();
			
			// 出勤率の計算方法を取得
			switch (workType.getCalculateMethod()){
			case DO_NOT_GO_TO_WORK:
				break;
			case MAKE_ATTENDANCE_DAY:
				// 労働日数に1日加算
				workingDays += 1.0;
				break;
			case EXCLUDE_FROM_WORK_DAY:
				// 控除日数に1日加算
				deductedDays += 1.0;
				break;
			case TIME_DIGEST_VACATION:
				//*****（未）　設計保留中。2018.7.26
				break;
			}
		}
		
		return new CalYearOffWorkAttendRate(0.0, prescribedDays, workingDays, deductedDays);
	}
	
	/** 日別実績から出勤率計算用日数を取得　（月別集計用） */
	@Override
	public CalYearOffWorkAttendRate algorithm(Require require, String companyId, String employeeId, DatePeriod period,
			MonAggrCompanySettings companySets, MonthlyCalculatingDailys monthlyCalcDailys,
			RepositoriesRequiredByMonthlyAggr repositories) {
		
		double prescribedDays = 0.0;
		double workingDays = 0.0;
		double deductedDays = 0.0;
		
		// 「日別実績の勤務情報」を取得
		val workInfos = monthlyCalcDailys.getWorkInfoOfDailyMap();
		for (val workInfo : workInfos.values()){
			if (!period.contains(workInfo.getYmd())) continue;
			if (workInfo.getRecordInfo() == null) continue;
			if (workInfo.getRecordInfo().getWorkTypeCode() == null) continue;
			val workTypeCode = workInfo.getRecordInfo().getWorkTypeCode().v();
		
			// 「勤務種類」を取得
			WorkType workType = companySets.getWorkTypeMapRequire(require, workTypeCode, repositories);
			if (workType == null) continue;
			
			// 勤務種類を判断してカウント数を取得
			WorkTypeDaysCountTable daysCount = new WorkTypeDaysCountTable(
					workType, new VacationAddSet(), Optional.empty());
			
			// 所定日数に取得したカウント数を加算
			prescribedDays += daysCount.getPredetermineDays().v();
			
			// 出勤率の計算方法を取得
			switch (workType.getCalculateMethod()){
			case DO_NOT_GO_TO_WORK:
				break;
			case MAKE_ATTENDANCE_DAY:
				// 労働日数に1日加算
				workingDays += 1.0;
				break;
			case EXCLUDE_FROM_WORK_DAY:
				// 控除日数に1日加算
				deductedDays += 1.0;
				break;
			case TIME_DIGEST_VACATION:
				//*****（未）　設計保留中。2018.7.26
				break;
			}
		}
		
		return new CalYearOffWorkAttendRate(0.0, prescribedDays, workingDays, deductedDays);
	}
	
	public static interface Require extends MonAggrCompanySettings.Require{
//		this.workInformationOfDailyRepo.findByPeriodOrderByYmd(employeeId, period);
		List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);
//		this.workTypeRepo.findByPK(companyId, workTypeCode);
		Optional<WorkType> findByPK(String companyId, String workTypeCd);
	}

	private Require createRequireImpl() {
		return new GetDaysForCalcAttdRateImpl.Require() {
			@Override
			public Optional<WkpTransLaborTime> findWkpTransLaborTime(String cid, String wkpId) {
				return wkpTransLaborTime.find(cid, wkpId);
			}
			@Override
			public Optional<WkpRegularLaborTime> findWkpRegularLaborTime(String cid, String wkpId) {
				return wkpRegularLaborTime.find(cid, wkpId);
			}
			@Override
			public Optional<EmpTransLaborTime> findEmpTransLaborTime(String cid, String emplId) {
				return empTransWorkTime.find(cid, emplId);
			}
			@Override
			public Optional<EmpRegularLaborTime> findEmpRegularLaborTimeById(String cid, String employmentCode) {
				return empRegularWorkTime.findById(cid, employmentCode);
			}
			@Override
			public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
				return workType.findByPK(companyId, workTypeCd);
			}
			@Override
			public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {
				return predetermineTimeSet.findByWorkTimeCode(companyId, workTimeCode);
			}
			@Override
			public Optional<WorkTimeSetting> findWorkTimeSettingByCode(String companyId, String workTimeCode) {
				return workTimeSet.findByCode(companyId, workTimeCode);
			}
			@Override
			public Optional<FlowWorkSetting> findFlowWorkSetting(String companyId, String workTimeCode) {
				return flowWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public Optional<FlexWorkSetting> findFlexWorkSetting(String companyId, String workTimeCode) {
				return flexWorkSet.find(companyId, workTimeCode);
			}
			@Override
			public Optional<FixedWorkSetting> findFixedWorkSettingByKey(String companyId, String workTimeCode) {
				return fixedWorkSet.findByKey(companyId, workTimeCode);
			}
			@Override
			public Optional<DiffTimeWorkSetting> findDiffTimeWorkSetting(String companyId, String workTimeCode) {
				return diffWorkSet.find(companyId, workTimeCode);
			}

			@Override
			public List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
				return workInformationOfDailyRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
			}
		};
	}
}