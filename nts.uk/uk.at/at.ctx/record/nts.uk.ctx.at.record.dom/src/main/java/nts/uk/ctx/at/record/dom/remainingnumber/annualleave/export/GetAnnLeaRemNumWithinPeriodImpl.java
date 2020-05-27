package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
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
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * 実装：期間中の年休残数を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAnnLeaRemNumWithinPeriodImpl implements GetAnnLeaRemNumWithinPeriod {

	/** 社員の取得 */
	@Inject
	private EmpEmployeeAdapter empEmployee;
	/** 年休社員基本情報 */
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	/** 年休付与テーブル設定 */
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	/** 勤続年数テーブル */
	@Inject
	private LengthServiceRepository lengthServiceRepo;
	/** 年休設定 */
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 年休付与残数データ */
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	/** 年休上限データ */
	@Inject
	private AnnLeaMaxDataRepository annLeaMaxDataRepo;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	/** 次回年休付与日を計算 */
	@Inject
	private CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;
	/** 月次処理用の暫定残数管理データを作成する */
	@Inject
	private InterimRemainOffMonthProcess interimRemOffMonth;
	/** 暫定年休管理データを作成する */
	@Inject
	private CreateInterimAnnualMngData createInterimAnnual;
	/** 暫定残数管理データ */
	@Inject
	private InterimRemainRepository interimRemainRepo;
	/** 暫定年休管理データ */
	@Inject
	private TmpAnnualHolidayMngRepository tmpAnnualLeaveMng;
	/** 月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	/** 期間中の年休残数を取得 */
	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	/** 締め状態管理 */
	@Inject
	private ClosureStatusManagementRepository closureSttMngRepo;
	/** 年休出勤率を計算する */
	@Inject
	private CalcAnnLeaAttendanceRate calcAnnLeaAttendanceRate;
	/** 年休付与テーブル */
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	/** 日別実績の運用開始設定 */
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetRepo;
	/** 年休付与残数履歴データ */
	@Inject
	private AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo;
	
	/*require用*/
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
	@Inject
	private WorkInformationRepository workInformationOfDailyRepo;
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ClosureRepository closureRepository;
	/*require用*/
	
	/** 期間中の年休残数を取得 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<AggrResultOfAnnualLeave> algorithm(
			String companyId, String employeeId, DatePeriod aggrPeriod, InterimRemainMngMode mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWriteOpt, Optional<List<TmpAnnualLeaveMngWork>> forOverWriteListOpt,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt, Optional<Boolean> noCheckStartDate) {

		GetAnnLeaRemNumWithinPeriodProc proc = this.createProc();
		return proc.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate,
				isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt, noCheckStartDate);
	}

	/** 期間中の年休残数を取得　（月別集計用） */
	@Override
	public Optional<AggrResultOfAnnualLeave> algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWriteOpt,
			Optional<List<TmpAnnualLeaveMngWork>> forOverWriteListOpt,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt,
			boolean noCheckStartDate,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		val require = createRequireImpl();
		val cacheCarrier = new CacheCarrier();
		return algorithmRequire(require, cacheCarrier, companyId, employeeId, 
				aggrPeriod, mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, 
				isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt, noCheckStartDate, 
				companySets, employeeSets, monthlyCalcDailys);
	}

	@Override
	public Optional<AggrResultOfAnnualLeave> algorithmRequire(Require require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWriteOpt,
			Optional<List<TmpAnnualLeaveMngWork>> forOverWriteListOpt,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt,
			boolean noCheckStartDate,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {

		GetAnnLeaRemNumWithinPeriodProc proc = this.createProc();
		return proc.algorithmRequire(require, cacheCarrier, companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate,
				isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt,
				noCheckStartDate, companySets, employeeSets, monthlyCalcDailys);
	}

	/** 期間中の年休残数を取得 */
	@Override
	public Optional<AggrResultOfAnnualLeave> algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWriteOpt,
			Optional<List<TmpAnnualLeaveMngWork>> forOverWriteListOpt, Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt,
			Optional<Boolean> noCheckStartDate,
			Optional<Boolean> isOutShortRemain,
			Optional<Boolean> aggrPastMonthMode, Optional<YearMonth> yearMonth) {

		GetAnnLeaRemNumWithinPeriodProc proc = this.createProc();
		return proc.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate,
				isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt, noCheckStartDate,
				isOutShortRemain, aggrPastMonthMode, yearMonth);
	}
	
	/** 期間中の年休残数を取得　（月別集計用） */
	@Override
	public Optional<AggrResultOfAnnualLeave> algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWriteOpt,
			Optional<List<TmpAnnualLeaveMngWork>> forOverWriteListOpt, Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt,
			boolean noCheckStartDate,
			Optional<Boolean> isOutShortRemain,
			Optional<Boolean> aggrPastMonthMode, Optional<YearMonth> yearMonth,
			Optional<MonAggrCompanySettings> companySets, Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {

		GetAnnLeaRemNumWithinPeriodProc proc = this.createProc();
		return proc.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate,
				isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt,
				noCheckStartDate, isOutShortRemain, aggrPastMonthMode, yearMonth,
				companySets, employeeSets, monthlyCalcDailys);
	}
	
	@Override
	public GetAnnLeaRemNumWithinPeriodProc createProc() {
		GetAnnLeaRemNumWithinPeriodProc proc = new GetAnnLeaRemNumWithinPeriodProc(
				this.empEmployee,
				this.annLeaEmpBasicInfoRepo,
				this.yearHolidayRepo,
				this.lengthServiceRepo,
				this.annualPaidLeaveSet,
				this.annLeaGrantRemDataRepo,
				this.annLeaMaxDataRepo,
				this.getClosureStartForEmployee,
				this.calcNextAnnualLeaveGrantDate,
				this.interimRemOffMonth,
				this.createInterimAnnual,
				this.interimRemainRepo,
				this.tmpAnnualLeaveMng,
				this.attendanceTimeOfMonthlyRepo,
				this.getAnnLeaRemNumWithinPeriod,
				this.closureSttMngRepo,
				this.calcAnnLeaAttendanceRate,
				this.grantYearHolidayRepo,
				this.operationStartSetRepo,
				this.annualLeaveRemainHistRepo);
		return proc;
	}
	
	public static interface Require extends GetAnnLeaRemNumWithinPeriodProc.Require{

	}

	private Require createRequireImpl() {
		return new GetAnnLeaRemNumWithinPeriodImpl.Require() {
			@Override
			public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
				return annLeaEmpBasicInfoRepo.get(employeeId);
			}
			@Override
			public List<LengthServiceTbl> findLengthServiceTblByCode(String companyId, String yearHolidayCode) {
				return lengthServiceRepo.findByCode(companyId, yearHolidayCode);
			}
			@Override
			public Optional<GrantHdTblSet> findGrantHdTblSetByCode(String companyId, String yearHolidayCode) {
				return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
			}
			@Override
			public AnnualPaidLeaveSetting findAnnualPaidLeaveSettingByCompanyId(String companyId) {
				return annualPaidLeaveSet.findByCompanyId(companyId);
			}
			@Override
			public List<AnnLeaRemNumEachMonth> findByClosurePeriod(String employeeId, DatePeriod closurePeriod) {
				return annLeaRemNumEachMonthRepo.findByClosurePeriod(employeeId, closurePeriod);
			}
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
			public Optional<AnnualLeaveEmpBasicInfo> getAnnualLeaveEmpBasicInfo(String employeeId) {
				return annLeaEmpBasicInfoRepo.get(employeeId);
			}
			@Override
			public List<AnnualLeaveGrantRemainingData> findNotExp(String employeeId) {
				return annLeaGrantRemDataRepo.findNotExp(employeeId);
			}
			@Override
			public Optional<OperationStartSetDailyPerform> findByCid(CompanyId companyId) {
				return operationStartSetRepo.findByCid(companyId);
			}
			@Override
			public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId) {
				return closureSttMngRepo.getLatestByEmpId(employeeId);
			}
			@Override
			public Optional<AnnualLeaveMaxData> getAnnualLeaveMaxData(String employeeId) {
				return annLeaMaxDataRepo.get(employeeId);
			}
			@Override
			public Optional<GrantHdTbl> findGrantHdTbl(String companyId, int conditionNo, String yearHolidayCode,int grantNum) {
				return grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum);
			}
			@Override
			public Optional<GrantHdTbl> find(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
				return grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum);
			}
			@Override
			public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
				return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
			}
			@Override
			public List<Closure> findAllUse(String companyId) {
				return closureRepository.findAllUse(companyId);
			}
			@Override
			public List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return workInformationOfDailyRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
			}
			@Override
			public Optional<Closure> findClosureById(String companyId, int closureId) {
				return closureRepository.findById(companyId, closureId);
			}
		};
	}
}