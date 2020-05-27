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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.GetDaysForCalcAttdRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.GetDaysForCalcAttdRateImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AttendanceRateCalPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
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
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.StandardCalculation;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * 年休出勤率を計算する
 * @author shuichu_ishida
 */
@Stateless
public class CalcAnnLeaAttendanceRateImpl implements CalcAnnLeaAttendanceRate {

	/** 年休設定 */
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 社員 */
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	/** 年休社員基本情報 */
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	/** 年休付与テーブル設定 */
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	/** 勤続年数テーブル */
	@Inject
	private LengthServiceRepository lengthServiceRepo;
	/** 年休月別残数データ */
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	/** 出勤率計算用日数を取得する */
	@Inject
	private GetDaysForCalcAttdRate getDaysForCalcAttdRate;
	/** 日別実績の運用開始設定 */
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetRepo;
	
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
	@Inject
	private WorkInformationRepository workInformationOfDailyRepo;
	/*require*/
	
	/** 年休出勤率を計算する */
	@Override
	public Optional<CalYearOffWorkAttendRate> algorithm(String companyId, String employeeId,
			GeneralDate grantDate, Optional<Integer> grantNum) {

		Optional<OperationStartSetDailyPerform> operationStartSetOpt =
				this.operationStartSetRepo.findByCid(new CompanyId(companyId));
		
		return this.algorithm(companyId, employeeId, grantDate, grantNum,
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				operationStartSetOpt);
	}
	
	/** 年休出勤率を計算する */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<CalYearOffWorkAttendRate> algorithm(
			String companyId, String employeeId, GeneralDate grantDate, Optional<Integer> grantNum,
			Optional<AnnualPaidLeaveSetting> annualLeaveSetOpt,
			Optional<EmployeeImport> employeeOpt,
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoParam,
			Optional<GrantHdTblSet> grantHdTblSetParam,
			Optional<List<LengthServiceTbl>> lengthServiceTblsParam,
			Optional<OperationStartSetDailyPerform> operationStartSetParam) {
		
		val require = createRequireImpl();
		val cacheCarrier = new CacheCarrier();
		return algorithmRequire(require, cacheCarrier, companyId, employeeId, 
				grantDate, grantNum, annualLeaveSetOpt, employeeOpt, 
				annualLeaveEmpBasicInfoParam, grantHdTblSetParam, lengthServiceTblsParam, operationStartSetParam);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<CalYearOffWorkAttendRate> algorithmRequire(
			Require require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, GeneralDate grantDate, Optional<Integer> grantNum,
			Optional<AnnualPaidLeaveSetting> annualLeaveSetOpt,
			Optional<EmployeeImport> employeeOpt,
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoParam,
			Optional<GrantHdTblSet> grantHdTblSetParam,
			Optional<List<LengthServiceTbl>> lengthServiceTblsParam,
			Optional<OperationStartSetDailyPerform> operationStartSetParam) {

		// 年休設定　取得
		AnnualPaidLeaveSetting annualLeaveSet = null;
		if (annualLeaveSetOpt.isPresent()){
			annualLeaveSet = annualLeaveSetOpt.get();
		}
		else {
			annualLeaveSet = require.findAnnualPaidLeaveSettingByCompanyId(companyId);
		}
		if (annualLeaveSet == null) return Optional.empty();
		
		// 社員　取得
		EmployeeImport employee = null;
		if (employeeOpt.isPresent()){
			employee = employeeOpt.get();
		}
		else {
			employee = this.empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, employeeId);
		}
		if (employee == null) return Optional.empty();
		
		// 年休社員基本情報　取得
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt = Optional.empty();
		if (annualLeaveEmpBasicInfoParam.isPresent()){
			annualLeaveEmpBasicInfoOpt = annualLeaveEmpBasicInfoParam;
		}
		else {
			annualLeaveEmpBasicInfoOpt = require.get(employeeId);
		}
		if (!annualLeaveEmpBasicInfoOpt.isPresent()) return Optional.empty();
		val empBasicInfo = annualLeaveEmpBasicInfoOpt.get();
		val grantTableCode = empBasicInfo.getGrantRule().getGrantTableCode().v();
		
		// 年休付与テーブル設定　取得
		Optional<GrantHdTblSet> grantHdTblSetOpt = Optional.empty();
		if (grantHdTblSetParam.isPresent()){
			grantHdTblSetOpt = grantHdTblSetParam;
		}
		else {
			grantHdTblSetOpt = require.findGrantHdTblSetByCode(companyId, grantTableCode);
		}
		if (!grantHdTblSetOpt.isPresent()) return Optional.empty();
		val grantHdTblSet = grantHdTblSetOpt.get();
		
		// 勤続年数テーブル　取得
		Optional<List<LengthServiceTbl>> lengthServiceTblsOpt = Optional.empty();
		if (lengthServiceTblsParam.isPresent()){
			lengthServiceTblsOpt = lengthServiceTblsParam;
		}
		else {
			lengthServiceTblsOpt = Optional.ofNullable(require.findLengthServiceTblByCode(companyId, grantTableCode));
		}
		if (!lengthServiceTblsOpt.isPresent()) return Optional.empty();
		val lengthServiceTbls = lengthServiceTblsOpt.get();
		
		// 出勤率計算する期間を計算する
		val calcPeriodOpt = this.calcPeriod(grantDate, grantNum, employee, grantHdTblSet, lengthServiceTbls);
		if (!calcPeriodOpt.isPresent()){
			return Optional.of(new CalYearOffWorkAttendRate(0.0, 0.0, 0.0, 0.0));
		}
		val calcPeriod = calcPeriodOpt.get();

		// 年休付与計算用の日数を計算する
		CalYearOffWorkAttendRate results = this.calcDays(require, companyId, employeeId, calcPeriod,
				annualLeaveSet, empBasicInfo, operationStartSetParam);
		
		// 日数から出勤率を計算する
		results.calcAttendanceRate();
		
		// 「年休出勤率計算用日数」と「出勤率」を返す
		return Optional.of(results);
	}
	
	/**
	 * 出勤率計算する期間を計算する
	 * @param grantDate 付与日
	 * @param grantNumOpt 付与回数
	 * @param employee 社員
	 * @param grantHdTblSet 年休付与テーブル設定
	 * @param lengthServiceTbls 勤続年数テーブルリスト
	 * @return 年休出勤率計算期間
	 */
	private Optional<AttendanceRateCalPeriod> calcPeriod(
			GeneralDate grantDate,
			Optional<Integer> grantNumOpt,
			EmployeeImport employee,
			GrantHdTblSet grantHdTblSet,
			List<LengthServiceTbl> lengthServiceTbls){
		
		// 計算基準をチェック
		GeneralDate calcEnd = grantDate.addDays(-1);	// 終了日を付与日の前日にする
		if (grantHdTblSet.getStandardCalculation() == StandardCalculation.WORK_CLOSURE_DATE){
			
			//*****（未）　設計が未作成。2018.7.25
		}
		
		// 入社年月日～「付与日」の間隔が1年以上かチェック
		if (employee.getEntryDate() == null) return Optional.empty();
		GeneralDate entryDate = employee.getEntryDate();
		GeneralDate calcStart = calcEnd.addDays(1).addYears(-1);		// 開始日 ← 終了日+1日の1年前
		if (employee.getEntryDate().addYears(1).after(grantDate)){
			// 1年未満
			
			boolean isOverHalfYear = false;
			if (grantNumOpt.isPresent() && lengthServiceTbls.size() > 0){
				val grantNum = grantNumOpt.get();
				
				// 「勤続年数テーブル」の勤続年月をチェック
				LengthServiceTbl lengthServiceTbl = null;
				for (val tbl : lengthServiceTbls){
					if (tbl.getGrantNum().v() == grantNum){
						lengthServiceTbl = tbl;
						break;
					}
				}
				if (lengthServiceTbl == null) lengthServiceTbl = lengthServiceTbls.get(lengthServiceTbls.size() - 1);
				Integer months = 0;		// 勤続年月（月数）
				if (lengthServiceTbl.getYear() != null) months = lengthServiceTbl.getYear().v() * 12;
				if (lengthServiceTbl.getMonth() != null) months = lengthServiceTbl.getMonth().v();
				if (months >= 7) isOverHalfYear = true;
				
				if (!isOverHalfYear){
					
					// 開始日　←　終了日+1日の6か月前
					calcStart = calcEnd.addDays(1).addMonths(-6);
				}
			}
		}
		
		// 入社日が開始日以降かチェック
		if (calcEnd.before(entryDate)) {
			// 対象期間なし
			return Optional.empty();
		}
		if (entryDate.beforeOrEquals(calcStart)){
			// 入社後期間　←　計算期間
			return Optional.of(new AttendanceRateCalPeriod(
					new DatePeriod(calcStart, calcEnd),
					new DatePeriod(calcStart, calcEnd),
					Optional.empty()));
		}
		// 計算期間を入社前期間と入社後期間に分割
		return Optional.of(new AttendanceRateCalPeriod(
				new DatePeriod(entryDate, calcEnd),
				new DatePeriod(calcStart, calcEnd),
				Optional.of(new DatePeriod(calcStart, entryDate.addDays(-1)))));
	}
	
	/**
	 * 年休付与計算用の日数を計算する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param calcPeriod 年休出勤率計算期間
	 * @param annualPaidLeaveSet 年休設定
	 * @param empBasicInfo 年休社員基本情報
	 * @param operationStartSetOpt 日別実績の運用開始設定
	 * @return 年休出勤率計算結果
	 */
	private CalYearOffWorkAttendRate calcDays(
			Require require,
			String companyId,
			String employeeId,
			AttendanceRateCalPeriod calcPeriod,
			AnnualPaidLeaveSetting annualPaidLeaveSet,
			AnnualLeaveEmpBasicInfo empBasicInfo,
			Optional<OperationStartSetDailyPerform> operationStartSetOpt){

		double prescribedDays = 0.0;
		double workingDays = 0.0;
		double deductedDays = 0.0;
		
		// 処理年月日　←　年休出勤率計算期間．入社後期間．開始日
		DatePeriod afterPeriod = calcPeriod.getAfterJoinCompany();
		GeneralDate procDate = afterPeriod.start();

		// 日別実績の運用開始日を使用するかチェックする
		boolean isUseOperationStart = false;
		if (operationStartSetOpt.isPresent()){
			val operationStartOpt = operationStartSetOpt.get().getOperateStartDateDailyPerform();
			if (operationStartOpt.isPresent()){
				GeneralDate operationStart = operationStartOpt.get();
				if (procDate.before(operationStart)){
					procDate = operationStart;
					isUseOperationStart = true;
				}
			}
		}
		
		// 「年休月別残数データ」を取得
		val remainNums = require.findByClosurePeriod(employeeId, afterPeriod);
		for (val remainNum : remainNums){
			val attendanceRateDays = remainNum.getAttendanceRateDays();
			
			// 処理年月日と処理中の「年休月別残数データ．締め期間」を比較
			if (procDate.before(remainNum.getClosurePeriod().start())){
				
				// 足りない期間を日別実績から計算
				val shortageDays = this.getDaysForCalcAttdRate.algorithm(require, companyId, employeeId,
						new DatePeriod(procDate, remainNum.getClosurePeriod().start().addDays(-1)));
				prescribedDays += shortageDays.getPrescribedDays();
				workingDays += shortageDays.getWorkingDays();
				deductedDays += shortageDays.getDeductedDays();
				
				// 年休月別残数データから日数を取得
				prescribedDays += attendanceRateDays.getPrescribedDays().v();
				workingDays += attendanceRateDays.getWorkingDays().v();
				deductedDays += attendanceRateDays.getDeductedDays().v();
			}
			if (procDate.equals(remainNum.getClosurePeriod().start())){
				
				// 年休月別残数データから日数を取得
				prescribedDays += attendanceRateDays.getPrescribedDays().v();
				workingDays += attendanceRateDays.getWorkingDays().v();
				deductedDays += attendanceRateDays.getDeductedDays().v();
			}
			
			// 処理年月日　←　年休月別残数データ．締め期間．終了日の翌日
			procDate = remainNum.getClosurePeriod().end().addDays(1);
		}
		
		// 期間全て計算できたかチェック
		if (!procDate.addDays(-1).equals(afterPeriod.end())){
			
			// 足りない期間を日別実績から計算
			val shortageDays = this.getDaysForCalcAttdRate.algorithm(require, companyId, employeeId,
					new DatePeriod(procDate, calcPeriod.getAfterJoinCompany().end()));
			prescribedDays += shortageDays.getPrescribedDays();
			workingDays += shortageDays.getWorkingDays();
			deductedDays += shortageDays.getDeductedDays();
		}
		
		// 「運用開始日使用フラグ」をチェック
		if (isUseOperationStart){
			
			// 導入前労働日数を労働日数に加算
			if (empBasicInfo.getWorkingDayBeforeIntroduction().isPresent()){
				workingDays += empBasicInfo.getWorkingDayBeforeIntroduction().get().v().doubleValue();
			}
		}
		
		// 年休社員基本情報．年間所定労働日数をチェック
		Double prescribedDaysYear = 0.0;
		if (empBasicInfo.getWorkingDaysPerYear().isPresent()){
			prescribedDaysYear = empBasicInfo.getWorkingDaysPerYear().get().v().doubleValue();
		}
		else {
			// 「年休設定」を取得する　→　年間所定労働日数にセット
			prescribedDaysYear = annualPaidLeaveSet.getManageAnnualSetting().getYearlyOfNumberDays().v();
		}
		
		// 年間所定労働日数から期間分の所定日数を計算する
		Integer periodDays = calcPeriod.getCal().start().daysTo(calcPeriod.getCal().end().addDays(1));
		if (periodDays < 365){
			Double propotionalDays = prescribedDaysYear / 365.0 * periodDays.doubleValue();
			Integer intPropoDays = propotionalDays.intValue();
			prescribedDays = intPropoDays.doubleValue();
		}
		else {
			prescribedDays = prescribedDaysYear.doubleValue();
		}
		
		// 入社前期間の出勤扱い日数を計算する
		double daysToWork = 0.0;
		if (calcPeriod.getBeforeJoinCompany().isPresent()){
			val beforePeriod = calcPeriod.getBeforeJoinCompany().get();
			
			// 入社前期間の日数を計算
			Integer beforeDays = beforePeriod.start().daysTo(beforePeriod.end().addDays(1));
			if (beforeDays > 0){
				
				// 年間所定労働日数を入社前期間だけ按分する　→　按分した日数を返す
				Double propotionalDays = prescribedDaysYear / 365.0 * beforeDays.doubleValue();
				Integer intPropoDays = propotionalDays.intValue();
				daysToWork = intPropoDays.doubleValue();
			}
		}
		
		// 「入社前期間の出勤扱い日数」を労働日数に加算
		workingDays += daysToWork;
		
		// 年休出勤率計算用日数を返す
		return new CalYearOffWorkAttendRate(0.0, prescribedDays, workingDays, deductedDays);
	}
	
	public static interface Require extends GetDaysForCalcAttdRateImpl.Require{
//		this.annualPaidLeaveSet.findByCompanyId(companyId);
		AnnualPaidLeaveSetting findAnnualPaidLeaveSettingByCompanyId(String companyId);
//		this.annLeaEmpBasicInfoRepo.get(employeeId);
		Optional<AnnualLeaveEmpBasicInfo> get(String employeeId);
//		this.yearHolidayRepo.findByCode(companyId, grantTableCode);
		Optional<GrantHdTblSet> findGrantHdTblSetByCode(String companyId, String yearHolidayCode);
//		this.lengthServiceRepo.findByCode(companyId, grantTableCode)
		List<LengthServiceTbl> findLengthServiceTblByCode(String companyId, String yearHolidayCode);
//		this.annLeaRemNumEachMonthRepo.findByClosurePeriod(employeeId, afterPeriod);
		List<AnnLeaRemNumEachMonth> findByClosurePeriod(String employeeId, DatePeriod closurePeriod);
	}

	private Require createRequireImpl() {
		return new CalcAnnLeaAttendanceRateImpl.Require() {
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
			public List<WorkInfoOfDailyPerformance> findWorkInfoOfDailyPerformanceByPeriodOrderByYmd(String employeeId,
					DatePeriod datePeriod) {
				return workInformationOfDailyRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
			}
		};
	}
}