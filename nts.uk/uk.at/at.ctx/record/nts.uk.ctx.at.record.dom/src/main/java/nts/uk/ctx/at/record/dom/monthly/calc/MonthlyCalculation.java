package nts.uk.ctx.at.record.dom.monthly.calc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の月�計�
 * @author shuichi_ishida
 */
@Getter
public class MonthlyCalculation {

	/** 実働時間 */
	private RegularAndIrregularTimeOfMonthly actualWorkingTime;
	/** フレヂ�ス時間 */
	private FlexTimeOfMonthly flexTime;
	/** 法定労働時�*/
	private AttendanceTimeMonth statutoryWorkingTime;
	/** 雨�時�*/
	private AggregateTotalWorkingTime aggregateTime;
	/** 総労働時�*/
	private AttendanceTimeMonth totalWorkingTime;
	/** 総拘束時�*/
	private AggregateTotalTimeSpentAtWork totalTimeSpentAtWork;
	/** 36協定時�*/
	@Setter
	private AgreementTimeOfMonthly agreementTime;

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 年�*/
	private YearMonth yearMonth;
	/** �めID */
	private ClosureId closureId;
	/** �め日�*/
	private ClosureDate closureDate;
	/** 期間 */
	private DatePeriod procPeriod;
	/** 労働条件頛� */
	private WorkingConditionItem workingConditionItem;
	/** 労働制 */
	private WorkingSystem workingSystem;
	/** 職場ID */
	private String workplaceId;
	/** 雔�コー�*/
	private String employmentCd;
	/** 退職月度がどぁ� */
	private boolean isRetireMonth;
	/** ��*/
	private Optional<Closure> closureOpt;
	/** 月別実績雨�設�*/
	private AggrSettingMonthly aggrSettingMonthly;
	/** 月次雨��法定�振替頨��*/
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 休暇�算時間設�*/
	private Optional<HolidayAddtionSet> holidayAdditionOpt;
	/** 日別実績の勤�時間リス�*/
	private Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap;
	/** 日別実績の勤務情報リス�*/
	private Map<GeneralDate, WorkInformation> workInformationOfDailyMap;
	/** 週間法定労働時�*/
	private AttendanceTimeMonth statutoryWorkingTimeWeek;
	/** 月間法定労働時�*/
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 週間所定労働時�*/
	private AttendanceTimeMonth prescribedWorkingTimeWeek;
	/** 月間所定労働時�*/
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	/** 月別実績の勤�時間　�集計前*/
	private Optional<AttendanceTimeOfMonthly> originalData;
	
	/** 年度 */
	private Year year;
	/** 管琜�間�36協定時�*/
	private AgreementTimeOfManagePeriod agreementTimeOfManagePeriod;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculation(){

		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.statutoryWorkingTime = new AttendanceTimeMonth(0);
		this.aggregateTime = new AggregateTotalWorkingTime();
		this.totalWorkingTime = new AttendanceTimeMonth(0);
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();
		this.agreementTime = new AgreementTimeOfMonthly();
		
		this.companyId = "empty";
		this.employeeId = "empty";
		this.yearMonth = new YearMonth(0);
		this.closureId = ClosureId.RegularEmployee;
		this.closureDate = new ClosureDate(0, true);
		this.procPeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.workingConditionItem = null;
		this.workingSystem = WorkingSystem.REGULAR_WORK;
		this.workplaceId = "empty";
		this.employmentCd = "empty";
		this.isRetireMonth = false;
		this.closureOpt = Optional.empty();
		this.aggrSettingMonthly = AggrSettingMonthly.of(
				new LegalAggrSetOfReg(), new LegalAggrSetOfIrg(), new AggrSettingMonthlyOfFlx());
		this.legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly("empty");
		this.holidayAdditionOpt = Optional.empty();
		this.attendanceTimeOfDailyMap = new HashMap<>();
		this.workInformationOfDailyMap = new HashMap<>();
		this.statutoryWorkingTimeWeek = new AttendanceTimeMonth(0);
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeWeek = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.originalData = null;

		this.year = new Year(0);
		this.agreementTimeOfManagePeriod = new AgreementTimeOfManagePeriod(this.employeeId, this.yearMonth);
	}

	/**
	 * ファクトリー
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレヂ�ス時間
	 * @param statutoryWorkingTime 法定労働時�
	 * @param aggregateTime 雨�時�
	 * @param totalWorkingTime 総労働時�
	 * @param totalTimeSpentAtWork 総拘束時�
	 * @param agreementTime 36協定時�
	 * @return 月別実績の月�計�
	 */
	public static MonthlyCalculation of(
			RegularAndIrregularTimeOfMonthly actualWorkingTime,
			FlexTimeOfMonthly flexTime,
			AttendanceTimeMonth statutoryWorkingTime,
			AggregateTotalWorkingTime aggregateTime,
			AttendanceTimeMonth totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalTimeSpentAtWork,
			AgreementTimeOfMonthly agreementTime){
		
		val domain = new MonthlyCalculation();
		domain.actualWorkingTime = actualWorkingTime;
		domain.flexTime = flexTime;
		domain.statutoryWorkingTime = statutoryWorkingTime;
		domain.aggregateTime = aggregateTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalTimeSpentAtWork = totalTimeSpentAtWork;
		domain.agreementTime = agreementTime;
		return domain;
	}
	
	/**
	 * 雨�準備
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年�
	 * @param closureId �めID
	 * @param closureDate �め日�
	 * @param procPeriod 期間
	 * @param workingConditionItem 労働条件頛�
	 * @param attendanceTimeOfDailysOpt 日別実績の勤�時間リス�
	 * @param repositories 月次雨�が忦�とするリポジトリ
	 */
	public void prepareAggregation(
			String companyId, String employeeId,YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			DatePeriod procPeriod, WorkingConditionItem workingConditionItem,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailysOpt,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.procPeriod = procPeriod;
		this.workingConditionItem = workingConditionItem;
		this.workingSystem = workingConditionItem.getLaborSystem();
		
		// 社員を取得す�
		EmployeeImport employee = repositories.getEmpEmployee().findByEmpId(employeeId);
		if (employee == null){
			String errMsg = "社員�タが見つかりません。　社員ID + employeeId;
			throw new BusinessException(new RawErrorMessage(errMsg));
		}
		
		// 退職月か確認する　�変形労働勤務�月単位集計：精算月判定に利用
		this.isRetireMonth = false;
		if (procPeriod.contains(employee.getRetiredDate())) this.isRetireMonth = true;
		
		// 期間終亗�時点の職場コードを取得す�
		val affWorkplaceOpt = repositories.getAffWorkplace().findBySid(employeeId, procPeriod.end());
		if (affWorkplaceOpt.isPresent()){
			this.workplaceId = affWorkplaceOpt.get().getWorkplaceId();
		}
		
		// 期間終亗�時点の雔�コードを取得す�
		val syEmploymentOpt = repositories.getSyEmployment().findByEmployeeId(
				companyId, employeeId, procPeriod.end());
		if (syEmploymentOpt.isPresent()){
			this.employmentCd = syEmploymentOpt.get().getEmploymentCode();
		}
		
		// 「�め」　取�
		this.closureOpt = repositories.getClosure().findById(companyId, closureId.value);
		if (closureOpt.isPresent()){
			val closure = closureOpt.get();
			if (closure.getUseClassification() != UseClassification.UseClass_Use) this.closureOpt = Optional.empty();
		}
		
		// 月別実績雨�設定　取得　�基準：期間終亗��　�設定確認不可時�、空設定を適用
		val aggrSettingMonthlyOpt = repositories.getAggrSettingMonthly().get(
				companyId, this.workplaceId, this.employmentCd, employeeId);
		if (aggrSettingMonthlyOpt.isPresent()){
			this.aggrSettingMonthly = aggrSettingMonthlyOpt.get();
		}
		
		// 法定�振替頨�定　取�
		val legalTransferOrderSetOpt = repositories.getLegalTransferOrderSetOfAggrMonthly().find(companyId);
		this.legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly(companyId);
		if (legalTransferOrderSetOpt.isPresent()){
			legalTransferOrderSet = legalTransferOrderSetOpt.get();
		}

		// 休暇�算時間設定　取�
		this.holidayAdditionOpt = repositories.getHolidayAddition().findByCId(companyId);
		
		// 通常の取得期間を　開始日-6日�終亗�　とする　�開始週の雨��ため
		DatePeriod findPeriod = new DatePeriod(procPeriod.start().addDays(-6), procPeriod.end());

		// 日別実績の勤�時間　取�
		if (attendanceTimeOfDailysOpt.isPresent()){
			for (val attendanceTimeOfDaily : attendanceTimeOfDailysOpt.get()){
				this.attendanceTimeOfDailyMap.putIfAbsent(attendanceTimeOfDaily.getYmd(), attendanceTimeOfDaily);
			}
			// 日別実績を渡された時は、渡された期間を適用する
			findPeriod = procPeriod;
		}
		else {
			val attendanceTimeOfDailys =
					repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
			for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
				this.attendanceTimeOfDailyMap.putIfAbsent(attendanceTimeOfDaily.getYmd(), attendanceTimeOfDaily);
			}
		}
		
		// 日別実績の勤務情報　取�
		val workInformationOfDailys =
				repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
		for (val workInformationOfDaily : workInformationOfDailys){
			val workInfo = workInformationOfDaily.getRecordInfo();
			this.workInformationOfDailyMap.putIfAbsent(workInformationOfDaily.getYmd(), workInfo);
		}

		// 週間、月間法定�所定労働時間　取�
		//*****�未�　日次での実製�置を確認して、合わせて実裁�る�
		//*****�未�　参耼�日次用�。このクラスか、別のクラスに、月・週用のメソッ�を追�。仮に0設定�
		//*****�未�　フレヂ�スの場合、労働制を判断して、Month側�けに対象時間を�れる�
		/*
		repositories.getGetOfStatutoryWorkTime().getDailyTimeFromStaturoyWorkTime(WorkingSystem.RegularWork,
				companyId, workplaceId, employmentCd, employeeId, datePeriod.end());
		*/
		this.statutoryWorkingTimeWeek = new AttendanceTimeMonth(40 * 60);
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(160 * 60);
		this.prescribedWorkingTimeWeek = new AttendanceTimeMonth(40 * 60);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(160 * 60);
		
		// 月別実績の勤�時間　既存データ　取�
		this.originalData = repositories.getAttendanceTimeOfMonthly().find(
				employeeId, yearMonth, closureId, closureDate);
		
		// 年度　設�
		this.year = new Year(this.yearMonth.year());
	}
	
	/**
	 * 履歴ごとに月別実績を集計す�
	 * @param aggrPeriod 雨�期�
	 * @param aggrAtr 雨�区�
	 * @param annualLeaveDeductDays 年休控除日数
	 * @param absenceDeductTime �勤控除時間
	 * @param repositories 月次雨�が忦�とするリポジトリ
	 */
	public void aggregate(DatePeriod aggrPeriod, MonthlyAggregateAtr aggrAtr,
			Optional<AttendanceDaysMonth> annualLeaveDeductDays,
			Optional<AttendanceTimeMonth> absenceDeductTime,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 雨�結果　初期�
		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.statutoryWorkingTime = new AttendanceTimeMonth(0);
		this.aggregateTime = new AggregateTotalWorkingTime();
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();

		// 既存データの復�
		this.restoreOriginalData(annualLeaveDeductDays, absenceDeductTime);

		// 不正呼び出し�時〛�計しな�
		if (this.workingConditionItem == null) return;
		
		// 共有雮を集計す�
		this.aggregateTime.aggregateSharedItem(aggrPeriod, this.attendanceTimeOfDailyMap);
		
		// 通常勤務　or　変形労働　の�
		if (this.workingSystem == WorkingSystem.REGULAR_WORK ||
				this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働勤務�月別実績を集計す�
			val aggrValue = this.actualWorkingTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, aggrPeriod, this.workingSystem, this.closureOpt, aggrAtr,
					this.aggrSettingMonthly, this.legalTransferOrderSet, this.holidayAdditionOpt,
					this.attendanceTimeOfDailyMap, this.workInformationOfDailyMap,
					this.statutoryWorkingTimeWeek, this.aggregateTime, null, repositories);
			this.aggregateTime = aggrValue.getAggregateTotalWorkingTime();
			
			// 通常・変形労働勤務�月単位�時間を集計す�
			this.actualWorkingTime.aggregateMonthlyHours(this.companyId, this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, aggrPeriod, this.workingSystem,
					aggrAtr, this.isRetireMonth, this.workplaceId, this.employmentCd,
					this.aggrSettingMonthly, this.holidayAdditionOpt, this.aggregateTime,
					this.statutoryWorkingTimeMonth, repositories);
		}
		// フレヂ�ス時間勤務　の�
		else if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレヂ�ス雨�方法を取得す�
			val aggrSetOfFlex = this.aggrSettingMonthly.getFlexWork();
			val flexAggrMethod = aggrSetOfFlex.getAggregateMethod();

			// フレヂ�ス勤務�月別実績を集計す�
			val aggrValue = this.flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, aggrPeriod, this.workingSystem, aggrAtr, flexAggrMethod,
					aggrSetOfFlex, this.attendanceTimeOfDailyMap, this.aggregateTime, null,
					this.prescribedWorkingTimeMonth, this.statutoryWorkingTimeMonth, repositories);
			this.aggregateTime = aggrValue.getAggregateTotalWorkingTime();
			
			// フレヂ�ス勤務�月単位�時間を集計す�
			this.flexTime.aggregateMonthlyHours(this.companyId, this.employeeId,
					this.yearMonth, aggrPeriod, flexAggrMethod, this.workingConditionItem,
					this.workplaceId, this.employmentCd, aggrSetOfFlex, this.holidayAdditionOpt,
					this.aggregateTime, this.prescribedWorkingTimeMonth, this.statutoryWorkingTimeMonth,
					repositories);
		}

		// 実働時間の雨
		this.aggregateTime.aggregateActualWorkingTime(aggrPeriod, this.workingSystem,
				this.actualWorkingTime, this.flexTime);
		
		// 総労働時間を計�
		this.calcTotalWorkingTime();
		
		// 管琜�間�36協定時間�作�
		this.agreementTimeOfManagePeriod = new AgreementTimeOfManagePeriod(this.employeeId, this.yearMonth);
		this.agreementTimeOfManagePeriod.aggregate(this.companyId, this.year, aggrPeriod.end(),
				aggrAtr, this, repositories);
		
		// 月別実績の36協定へ値を移�
		this.agreementTime = this.agreementTimeOfManagePeriod.getAgreementTime();
	}
	
	/**
	 * 既存データの復�
	 * @param annualDeductDays 年休控除日数
	 * @param absenceDeductTime �勤控除時間
	 */
	private void restoreOriginalData(
			Optional<AttendanceDaysMonth> annualDeductDays,
			Optional<AttendanceTimeMonth> absenceDeductTime){
		
		// 年休控除日数・�勤控除時間
		AttendanceDaysMonth applyAnnualDeductDays = new AttendanceDaysMonth(0.0);
		AttendanceTimeMonth applyAbsenceDeductTime = new AttendanceTimeMonth(0);
		if (annualDeductDays.isPresent() || absenceDeductTime.isPresent()){
			if (annualDeductDays.isPresent()) applyAnnualDeductDays = annualDeductDays.get();
			if (absenceDeductTime.isPresent()) applyAbsenceDeductTime = absenceDeductTime.get();
		}
		else if (this.originalData.isPresent()){
			val monthlyCalculation = this.originalData.get().getMonthlyCalculation();
			val flexShortDeductTime = monthlyCalculation.getFlexTime().getFlexShortDeductTime();
			applyAnnualDeductDays = flexShortDeductTime.getAnnualLeaveDeductDays();
			applyAbsenceDeductTime = flexShortDeductTime.getAbsenceDeductTime();
		}
		this.flexTime.getFlexShortDeductTime().setAnnualLeaveDeductDays(applyAnnualDeductDays);
		this.flexTime.getFlexShortDeductTime().setAbsenceDeductTime(applyAbsenceDeductTime);
	}
	
	/**
	 * 総労働時間�計�
	 * @param datePeriod 期間
	 */
	private void calcTotalWorkingTime(){

		this.totalWorkingTime = new AttendanceTimeMonth(this.aggregateTime.getTotalWorkingTargetTime().v() +
				this.actualWorkingTime.getTotalWorkingTargetTime().v() +
				this.flexTime.getTotalWorkingTargetTime().v());
	}
	
	/**
	 * 36協定時間�雨
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年�
	 * @param closureId �めID
	 * @param closureDate �め日�
	 * @param procPeriod 期間
	 * @param workingConditionItem 労働条件頛�
	 * @param isRetireMonth 退職月度かどぁ�
	 * @param attendanceTimeOfDailysOpt 日別実績の勤�時間リス�
	 * @param repositories 月次雨�が忦�とするリポジトリ
	 */
	public Optional<AgreementTimeOfManagePeriod> aggregateAgreementTime(
			String companyId, String employeeId,YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			DatePeriod procPeriod, WorkingConditionItem workingConditionItem,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailysOpt,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 36協定運用設定を取�
		val agreementOperationSetOpt = repositories.getAgreementOperationSet().find(companyId);
		if (!agreementOperationSetOpt.isPresent()) return Optional.empty();
		val agreementOperationSet = agreementOperationSetOpt.get();
		
		// 雨�期間を取�
		val aggrPeriod = agreementOperationSet.getAggregatePeriod(procPeriod);
		
		// 履歴ごとに月別実績を集計す�
		this.prepareAggregation(companyId, employeeId, aggrPeriod.getYearMonth(), closureId, closureDate,
				aggrPeriod.getPeriod(), workingConditionItem, attendanceTimeOfDailysOpt, repositories);
		this.year = aggrPeriod.getYear();
		this.aggregate(aggrPeriod.getPeriod(), MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
				Optional.empty(), Optional.empty(), repositories);

		// 管琙�間�36協定時間を返す
		return Optional.of(this.agreementTimeOfManagePeriod);
	}
	
	/**
	 * 勤�頛�IDに対応する時間を取得する　�丸め�琻�き
	 * @param attendanceItemId 勤�頛�ID
	 * @param roundingSet 月別実績の丸め設�
	 * @return 勤�月間時間
	 */
	public AttendanceTimeMonth getTimeOfAttendanceItemId(int attendanceItemId, RoundingSetOfMonthly roundingSet){

		AttendanceTimeMonth notExistTime = new AttendanceTimeMonth(0);

		val overTimeMap = this.aggregateTime.getOverTime().getAggregateOverTimeMap();
		val hdwkTimeMap = this.aggregateTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
		// 就業時間
		if (attendanceItemId == AttendanceItemOfMonthly.WORK_TIME.value){
			val workTime = this.aggregateTime.getWorkTime().getWorkTime();
			return roundingSet.itemRound(attendanceItemId, workTime);
		}
		
		// 残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getTime());
		}
		
		// 計算残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getCalcTime());
		}
		
		// 振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getTime());
		}
		
		// 計算振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getCalcTime());
		}
		
		// 休�時間
		if (attendanceItemId >= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getTime());
		}
		
		// 計算休�時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getCalcTime());
		}
		
		// 振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getTime());
		}
		
		// 計算振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getCalcTime());
		}
		
		// フレヂ�ス趁�時間
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value){
			val flexExcessTime = this.flexTime.getFlexExcessTime();
			return roundingSet.itemRound(attendanceItemId, flexExcessTime);
		}
		
		// 所定�割増時�
		if (attendanceItemId == AttendanceItemOfMonthly.WITHIN_PRESCRIBED_PREMIUM_TIME.value){
			val withinPrescribedPremiumTime = this.aggregateTime.getWorkTime().getWithinPrescribedPremiumTime();
			return roundingSet.itemRound(attendanceItemId, withinPrescribedPremiumTime);
		}
		
		// 週割増合計時�
		if (attendanceItemId == AttendanceItemOfMonthly.WEEKLY_TOTAL_PREMIUM_TIME.value){
			val weeklyTotalPremiumTime = this.actualWorkingTime.getWeeklyTotalPremiumTime();
			return roundingSet.itemRound(attendanceItemId, weeklyTotalPremiumTime);
		}
		
		// 月割増合計時�
		if (attendanceItemId == AttendanceItemOfMonthly.MONTHLY_TOTAL_PREMIUM_TIME.value){
			val monthlyTotalPremiumTime = this.actualWorkingTime.getMonthlyTotalPremiumTime();
			return roundingSet.itemRound(attendanceItemId, monthlyTotalPremiumTime);
		}
		
		return notExistTime;
	}
}
