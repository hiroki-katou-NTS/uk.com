package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriodParam;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.CalcDeadlineForGrantDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.GetUpperLimitSetting;
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
 * 実装：期間中の年休積休残数を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAnnAndRsvRemNumWithinPeriodImpl implements GetAnnAndRsvRemNumWithinPeriod {

	/** 期間中の年休残数を取得 */
	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	/** 期間中の積立年休残数を取得 */
	@Inject
	private GetRsvLeaRemNumWithinPeriod getRsvLeaRemNumWithinPeriod;
	/** 締め状態管理 */
	@Inject
	private ClosureStatusManagementRepository closureSttMngRepo;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	
	/*require*/
	@Inject
	private InterimRemainRepository interimRemainRepo;
	@Inject
	private TmpResereLeaveMngRepository tmpReserveLeaveMng;
	@Inject
	private GetUpperLimitSetting getUpperLimitSetting;
	@Inject
	private CalcDeadlineForGrantDate calcDeadlineForGrantDate;
	@Inject
	private ShareEmploymentAdapter employmentAdapter;
	@Inject
	private EmploymentSettingRepository employmentSetRepo;
	@Inject
	private EmpEmployeeAdapter empEmployee;
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	@Inject
	private RervLeaGrantRemDataRepository rsvLeaGrantRemDataRepo;
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
	private GrantYearHolidayRepository grantYearHolidayRepo;
	@Inject
	private AnnLeaMaxDataRepository annLeaMaxDataRepo;
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	@Inject
	private LengthServiceRepository lengthServiceRepo;
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ClosureRepository closureRepository;	
	@Inject
	private RetentionYearlySettingRepository retentionYearlySetRepo;
	/*require用*/
	
	/** 期間中の年休積休残数を取得 */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave) {
		
		return this.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
				tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
				Optional.empty(), Optional.empty(), prevAnnualLeave, prevReserveLeave,
				Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	/** 期間中の年休積休残数を取得　（月次集計用） */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		val require = createRequireImpl();

		val cacheCarrier = new CacheCarrier();

		return algorithmRequire(require, cacheCarrier, companyId, employeeId, aggrPeriod, mode, 
				criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite, tempAnnDataforOverWriteList, 
				tempRsvDataforOverWriteList, isOutputForShortage, noCheckStartDate, prevAnnualLeave, 
				prevReserveLeave, companySets, employeeSets, monthlyCalcDailys);
	}
	@Override
	public AggrResultOfAnnAndRsvLeave algorithmRequire(Require require, CacheCarrier cacheCarrier, String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		AggrResultOfAnnAndRsvLeave aggrResult = new AggrResultOfAnnAndRsvLeave();

		// 集計開始日までの年休積立年休を取得
		{
			boolean isChangeParam = false;
			
			// 集計開始日時点の前回年休の集計結果が存在するかチェック
			boolean isExistPrevAnnual = false;
			if (prevAnnualLeave.isPresent()){
				if (prevAnnualLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
					isExistPrevAnnual = true;
				}
			}
			boolean isExistPrevReserve = false;
			if (isExistPrevAnnual){
				
				// 集計開始日時点の前回の積立年休の集計結果が存在するかチェック
				if (prevReserveLeave.isPresent()){
					if (prevReserveLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
						isExistPrevReserve = true;
					}
				}
			}
			if (!isExistPrevAnnual && !isExistPrevReserve){
				
				// 「集計開始日を締め開始日とする」をチェック
				boolean isCheck = false;
				if (noCheckStartDate.isPresent()) if (noCheckStartDate.get()) isCheck = true;
				if (!isCheck) isChangeParam = true;
			}

			if (isChangeParam){
				
				// 休暇残数を計算する締め開始日を取得する
				GeneralDate closureStart = null;	// 締め開始日
				{
					// 最新の締め終了日翌日を取得する
					Optional<ClosureStatusManagement> sttMng = require.getLatestByEmpId(employeeId);
					if (sttMng.isPresent()){
						closureStart = sttMng.get().getPeriod().end().addDays(1);
					}
					else {
						
						//　社員に対応する締め開始日を取得する
						val closureStartOpt = this.getClosureStartForEmployee.algorithmRequire(require, cacheCarrier, employeeId);
						if (closureStartOpt.isPresent()) closureStart = closureStartOpt.get();
					}
				}
				
				if (closureStart != null){
					if (closureStart.equals(aggrPeriod.start())){
						
						// 「集計開始日を締め開始日にする」をtrueにする
						noCheckStartDate = Optional.of(true);
					}
					if (closureStart.before(aggrPeriod.start())){
						
						// 「期間中の年休積立年休残数を取得」を実行する
						val prevResult = this.algorithmRequire(require, cacheCarrier, companyId, employeeId,
								new DatePeriod(closureStart, aggrPeriod.start().addDays(-1)),
								mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
								tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
								isOutputForShortage, Optional.of(true), Optional.empty(), Optional.empty(),
								companySets, employeeSets, Optional.empty());
						
						// 受け取った結果をパラメータに反映する
						noCheckStartDate = Optional.of(false);
						prevAnnualLeave = prevResult.getAnnualLeave();
						prevReserveLeave = prevResult.getReserveLeave();
					}
				}
			}
		}
		
		// 期間中の年休残数を取得
		Boolean isNoCheckStartDate = false;
		if (noCheckStartDate.isPresent()) isNoCheckStartDate = noCheckStartDate.get();
		val aggrResultOfAnnualOpt = this.getAnnLeaRemNumWithinPeriod.algorithmRequire(require, cacheCarrier, companyId, employeeId, aggrPeriod,
					mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
					tempAnnDataforOverWriteList, prevAnnualLeave, isNoCheckStartDate,
					companySets, employeeSets, monthlyCalcDailys);

		// 「年休積立年休の集計結果．年休」　←　受け取った「年休の集計結果」
		aggrResult.setAnnualLeave(aggrResultOfAnnualOpt);
		
		// 期間中の積立年休残数を取得
		List<AnnualLeaveInfo> lapsedAnnualLeaveInfos = new ArrayList<>();
		if (aggrResultOfAnnualOpt.isPresent()){
			if (aggrResultOfAnnualOpt.get().getLapsed().isPresent()){
				lapsedAnnualLeaveInfos = aggrResultOfAnnualOpt.get().getLapsed().get();
			}
		}
		GetRsvLeaRemNumWithinPeriodParam rsvParam = new GetRsvLeaRemNumWithinPeriodParam(
				companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, lapsedAnnualLeaveInfos, isOverWrite, tempRsvDataforOverWriteList,
				isOutputForShortage, noCheckStartDate, prevReserveLeave);
		val aggrResultOfreserveOpt = this.getRsvLeaRemNumWithinPeriod.algorithmRequire(require, cacheCarrier,
				rsvParam, companySets, monthlyCalcDailys);
		
		// 「年休積立年休の集計結果．積休」　←　受け取った「積立年休の集計結果」
		aggrResult.setReserveLeave(aggrResultOfreserveOpt);
		
		// 「年休積立年休の集計結果」を返す
		return aggrResult;
	}

	@Override
	public AggrResultOfAnnAndRsvLeave getRemainAnnRscByPeriod(String cID, String sID, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate, 
			Optional<Boolean> isOverWrite, Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList, Optional<Boolean> isOutputForShortage,
			Optional<Boolean> noCheckStartDate, Optional<AggrResultOfAnnualLeave> prevAnnualLeave,
			Optional<AggrResultOfReserveLeave> prevReserveLeave, Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets, Optional<MonthlyCalculatingDailys> monthlyCalcDailys,
			Optional<ClosureStatusManagement> sttMng, Optional<GeneralDate> clsStrOpt) {
		AggrResultOfAnnAndRsvLeave aggrResult = new AggrResultOfAnnAndRsvLeave();
			// 集計開始日までの年休積立年休を取得
			{
				boolean isChangeParam = false;
				// 集計開始日時点の前回年休の集計結果が存在するかチェック
				boolean isExistPrevAnnual = false;
				if (prevAnnualLeave.isPresent()){
					if (prevAnnualLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
						isExistPrevAnnual = true;
					}
				}
				boolean isExistPrevReserve = false;
				if (isExistPrevAnnual){
					// 集計開始日時点の前回の積立年休の集計結果が存在するかチェック
					if (prevReserveLeave.isPresent()){
						if (prevReserveLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
							isExistPrevReserve = true;
						}
					}
				}
				if (!isExistPrevAnnual && !isExistPrevReserve){
					// 「集計開始日を締め開始日とする」をチェック
					boolean isCheck = false;
					if (noCheckStartDate.isPresent()) if (noCheckStartDate.get()) isCheck = true;
					if (!isCheck) isChangeParam = true;
				}
	
				if (isChangeParam){
					// 休暇残数を計算する締め開始日を取得する
					GeneralDate closureStart = null;	// 締め開始日
					{
						if (sttMng.isPresent()){
							closureStart = sttMng.get().getPeriod().end().addDays(1);
						}else {
							if (clsStrOpt.isPresent()) closureStart = clsStrOpt.get();
						}
					}
					if (closureStart != null){
						if (closureStart.equals(aggrPeriod.start())){
							
							// 「集計開始日を締め開始日にする」をtrueにする
							noCheckStartDate = Optional.of(true);
						}
						if (closureStart.before(aggrPeriod.start())){
							// 「期間中の年休積立年休残数を取得」を実行する
							val prevResult = this.getRemainAnnRscByPeriod(cID, sID,
									new DatePeriod(closureStart, aggrPeriod.start().addDays(-1)),
									mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
									tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
									isOutputForShortage, Optional.of(true), Optional.empty(), Optional.empty(),
									companySets, employeeSets, Optional.empty(), sttMng, clsStrOpt);
							
							// 受け取った結果をパラメータに反映する
							noCheckStartDate = Optional.of(false);
							prevAnnualLeave = prevResult.getAnnualLeave();
							prevReserveLeave = prevResult.getReserveLeave();
						}
					}
				}
			}
			
			// 期間中の年休残数を取得
			Boolean isNoCheckStartDate = false;
			if (noCheckStartDate.isPresent()) isNoCheckStartDate = noCheckStartDate.get();
			val aggrResultOfAnnualOpt = this.getAnnLeaRemNumWithinPeriod.algorithm(cID, sID, aggrPeriod,
						mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
						tempAnnDataforOverWriteList, prevAnnualLeave, isNoCheckStartDate,
						companySets, employeeSets, monthlyCalcDailys);
	
			// 「年休積立年休の集計結果．年休」　←　受け取った「年休の集計結果」
			aggrResult.setAnnualLeave(aggrResultOfAnnualOpt);
			
			// 期間中の積立年休残数を取得
			List<AnnualLeaveInfo> lapsedAnnualLeaveInfos = new ArrayList<>();
			if (aggrResultOfAnnualOpt.isPresent()){
				if (aggrResultOfAnnualOpt.get().getLapsed().isPresent()){
					lapsedAnnualLeaveInfos = aggrResultOfAnnualOpt.get().getLapsed().get();
				}
			}
			GetRsvLeaRemNumWithinPeriodParam rsvParam = new GetRsvLeaRemNumWithinPeriodParam(
					cID, sID, aggrPeriod, mode, criteriaDate,
					isGetNextMonthData, lapsedAnnualLeaveInfos, isOverWrite, tempRsvDataforOverWriteList,
					isOutputForShortage, noCheckStartDate, prevReserveLeave);
			val aggrResultOfreserveOpt = this.getRsvLeaRemNumWithinPeriod.algorithm(
					rsvParam, companySets, monthlyCalcDailys);
			
			// 「年休積立年休の集計結果．積休」　←　受け取った「積立年休の集計結果」
			aggrResult.setReserveLeave(aggrResultOfreserveOpt);
			
			// 「年休積立年休の集計結果」を返す
		return aggrResult;
	}
	
	public static interface Require extends GetAnnLeaRemNumWithinPeriodImpl.Require, 
		GetRsvLeaRemNumWithinPeriodImpl.Require{
		//this.closureSttMngRepo.getLatestByEmpId(employeeId);
		Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId);
	
	}
	
	private Require createRequireImpl() {
		return new GetAnnAndRsvRemNumWithinPeriodImpl.Require() {
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
			public List<ReserveLeaveGrantRemainingData> findNotExp(String employeeId, String cId) {
				return rsvLeaGrantRemDataRepo.findNotExp(employeeId, null);
			}
			@Override
			public List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType) {
				return interimRemainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.FUNDINGANNUAL);
			}
			@Override
			public Optional<TmpResereLeaveMng> getById(String resereMngId) {
				return tmpReserveLeaveMng.getById(resereMngId);
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
			public Optional<RetentionYearlySetting> findRetentionYearlySettingByCompanyId(String companyId) {
				return retentionYearlySetRepo.findByCompanyId(companyId);
			}
			@Override
			public Optional<GrantHdTbl> find(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
				return grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum);
			}
			@Override
			public List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId,
			DatePeriod datePeriod) {
				return workInformationOfDailyRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
			}
			@Override
			public Optional<EmptYearlyRetentionSetting> findEmptYearlyRetentionSetting(String companyId, String employmentCode) {
				return employmentSetRepo.find(companyId, employmentCode);
			}
			@Override
			public Optional<Closure> findClosureById(String companyId, int closureId) {
				return closureRepository.findById(companyId, closureId);
			}
		};
	}
}