package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.GetWorkTimezoneCommonSet;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateMonthlyValue;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.ProcAtrHolidayWorkAndTransfer;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.ProcAtrOverTimeAndTransfer;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 時間外超過管理
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideWorkMng {

	/** 月次明細 */
	private MonthlyDetail monthlyDetail;
	/** 時間外超過明細 */
	private ExcessOutsideWorkDetail excessOutsideWorkDetail;
	/** 時間外超過 */
	private ExcessOutsideWorkOfMonthly excessOutsideWork;
	
	/** 月別実績の月の計算 */
	private MonthlyCalculation monthlyCalculation;
	/** 会社ID */
	private final String companyId;
	/** 社員ID */
	private final String employeeId;
	/** 年月 */
	private final YearMonth yearMonth;
	/** 締めID */
	private final ClosureId closureId;
	/** 締め日付 */
	private final ClosureDate closureDate;
	/** 期間 */
	private final DatePeriod datePeriod;
	/** 労働制 */
	private final WorkingSystem workingSystem;
	/** 職場ID */
	private String workplaceId;
	/** 雇用コード */
	private String employmentCd;
	/** 退職月度がどうか */
	private boolean isRetireMonth;
	/** 月別実績集計設定 */
	private AggrSettingMonthly aggrSettingMonthly;
	/** 月次集計の法定内振替順設定 */
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 休暇加算時間設定 */
	private Optional<HolidayAddtion> holidayAdditionOpt;
	/** 日別実績の勤怠時間リスト */
	private Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap;
	/** 日別実績の勤務情報リスト */
	private Map<GeneralDate, WorkInformation> workInformationOfDailyMap;
	/** 週間法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTimeWeek;
	/** 月間法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 週間所定労働時間 */
	private AttendanceTimeMonth prescribedWorkingTimeWeek;
	/** 月間所定労働時間 */
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	
	/** 時間外超過設定 */
	private Optional<OutsideOTSetting> outsideOTSetOpt;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param monthlyCalculation 月別実績の月の計算
	 */
	public ExcessOutsideWorkMng(MonthlyCalculation monthlyCalculation){
		
		this.monthlyDetail = new MonthlyDetail();
		this.excessOutsideWorkDetail = new ExcessOutsideWorkDetail();
		this.excessOutsideWork = new ExcessOutsideWorkOfMonthly();
		
		this.monthlyCalculation = monthlyCalculation;
		this.companyId = monthlyCalculation.getCompanyId();
		this.employeeId = monthlyCalculation.getEmployeeId();
		this.yearMonth = monthlyCalculation.getYearMonth();
		this.closureId = monthlyCalculation.getClosureId();
		this.closureDate = monthlyCalculation.getClosureDate();
		this.datePeriod = monthlyCalculation.getDatePeriod();
		this.workingSystem = monthlyCalculation.getWorkingSystem();
		this.workplaceId = monthlyCalculation.getWorkplaceId();
		this.employmentCd = monthlyCalculation.getEmploymentCd();
		this.isRetireMonth = monthlyCalculation.isRetireMonth();
		this.aggrSettingMonthly = monthlyCalculation.getAggrSettingMonthly();
		this.legalTransferOrderSet = monthlyCalculation.getLegalTransferOrderSet();
		this.holidayAdditionOpt = monthlyCalculation.getHolidayAdditionOpt();
		this.attendanceTimeOfDailyMap = monthlyCalculation.getAttendanceTimeOfDailyMap();
		this.workInformationOfDailyMap = monthlyCalculation.getWorkInformationOfDailyMap();
		this.statutoryWorkingTimeWeek = monthlyCalculation.getStatutoryWorkingTimeWeek();
		this.statutoryWorkingTimeMonth = monthlyCalculation.getStatutoryWorkingTimeMonth();
		this.prescribedWorkingTimeWeek = monthlyCalculation.getPrescribedWorkingTimeWeek();
		this.prescribedWorkingTimeMonth = monthlyCalculation.getPrescribedWorkingTimeMonth();
		
		this.outsideOTSetOpt = Optional.empty();
	}
	
	/**
	 * 集計
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(RepositoriesRequiredByMonthlyAggr repositories){

		// 時間外超過設定　取得
		this.outsideOTSetOpt = repositories.getOutsideOTSet().findById(this.companyId);
		
		// 労働制を確認する
		if (this.workingSystem == WorkingSystem.REGULAR_WORK || this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の時間外超過を集計する
			this.aggregateExcessOutsideWork(null, repositories);
		}
		if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス集計方法を確認する
			val flexAggregateMethod = this.aggrSettingMonthly.getFlexWork().getAggregateMethod();
			if (flexAggregateMethod == FlexAggregateMethod.PRINCIPLE){
				
				// 原則集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(FlexAggregateMethod.PRINCIPLE, repositories);
			}
			if (flexAggregateMethod == FlexAggregateMethod.FOR_CONVENIENCE){
				
				// 便宜上集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(FlexAggregateMethod.FOR_CONVENIENCE, repositories);

				// 原則集計で時間外超過を集計する
				//this.aggregateExcessOutsideWork(FlexAggregateMethod.PRINCIPLE, repositories);
				
				// 原則集計と便宜上集計の結果を比較する
				//*****（未）　設計が保留中。比較する場合、どのクラスを比較するかで、原則・便宜上の各計算結果の保持方法の検討要。
			}
		}
		
		// 超休精算
	}
	
	/**
	 * 時間外超過を集計する
	 * @param flexAggregateMethod フレックス集計方法
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateExcessOutsideWork(
			FlexAggregateMethod flexAggregateMethod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 時間外超過設定を確認する
		OutsideOTCalMed calcMethod = OutsideOTCalMed.DECISION_AFTER;
		if (this.outsideOTSetOpt.isPresent()) calcMethod = outsideOTSetOpt.get().getCalculationMethod();
		if (calcMethod == OutsideOTCalMed.DECISION_AFTER){
			
			// 集計後に求める
			this.askAfterAggregate(flexAggregateMethod, repositories);
		}
		if (calcMethod == OutsideOTCalMed.TIME_SERIES){
			
			// 時系列で求める
			this.askbyTimeSeries(flexAggregateMethod, repositories);
		}
	}
	
	/**
	 * 集計後に求める
	 * @param flexAggregateMethod フレックス集計方法
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void askAfterAggregate(
			FlexAggregateMethod flexAggregateMethod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計結果　初期化
		val regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		val flexTime = new FlexTimeOfMonthly();
		val aggregateTotalWorkingTime = new AggregateTotalWorkingTime();
		AggregateMonthlyValue aggrValue = null;
		
		// 共有項目を集計する
		aggregateTotalWorkingTime.aggregateSharedItem(this.datePeriod, this.attendanceTimeOfDailyMap);
		
		// 労働制を確認する
		if (workingSystem == WorkingSystem.REGULAR_WORK || workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の月別実績を集計する
			aggrValue = regAndIrgTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.aggrSettingMonthly, this.legalTransferOrderSet, this.holidayAdditionOpt,
					this.attendanceTimeOfDailyMap, this.workInformationOfDailyMap, this.statutoryWorkingTimeWeek,
					aggregateTotalWorkingTime, this, repositories);
			
			// 通常・変形労働時間勤務の月単位の時間を集計する
			regAndIrgTime.aggregateMonthlyHours(
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.isRetireMonth, this.workplaceId, this.employmentCd,
					this.aggrSettingMonthly, this.holidayAdditionOpt,
					aggrValue.getAggregateTotalWorkingTime(),
					this.statutoryWorkingTimeMonth, repositories);
		}
		if (workingSystem == WorkingSystem.FLEX_TIME_WORK){
			val aggrSetOfFlex = aggrSettingMonthly.getFlexWork();
			
			// フレックス勤務の月別実績を集計する
			aggrValue = flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					flexAggregateMethod, aggrSetOfFlex, this.attendanceTimeOfDailyMap,
					aggregateTotalWorkingTime, this, repositories);
			
			// フレックス勤務の月単位の時間を集計する
			flexTime.aggregateMonthlyHours(this.companyId, this.employeeId, this.yearMonth, this.datePeriod,
					flexAggregateMethod, this.workplaceId, this.employmentCd, aggrSetOfFlex, this.holidayAdditionOpt,
					aggrValue.getAggregateTotalWorkingTime(),
					this.prescribedWorkingTimeMonth, this.statutoryWorkingTimeMonth, repositories);
		}
		
		if (aggrValue != null){
			
			// 実働時間の集計
			aggrValue.getAggregateTotalWorkingTime().aggregateActualWorkingTime(
					this.datePeriod, this.workingSystem, regAndIrgTime, flexTime);
			
			// 時間外超過明細．丸め後合計時間に移送する
			
			// 月別実績の時間外超過に移送する
			
			// 時間外超過内訳に割り当てる
		
		}
	}
	
	/**
	 * 時系列で求める
	 * @param flexAggregateMethod フレックス集計方法
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void askbyTimeSeries(
			FlexAggregateMethod flexAggregateMethod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計結果　初期化
		val regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		val flexTime = new FlexTimeOfMonthly();
		val aggregateTotalWorkingTime = new AggregateTotalWorkingTime();
		AggregateMonthlyValue aggrValue = null;
		
		// 共有項目を集計する
		aggregateTotalWorkingTime.aggregateSharedItem(this.datePeriod, this.attendanceTimeOfDailyMap);
		
		// 労働制を確認する
		if (this.workingSystem == WorkingSystem.REGULAR_WORK ||
			this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の月別実績を集計する
			aggrValue = regAndIrgTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.aggrSettingMonthly, this.legalTransferOrderSet, this.holidayAdditionOpt,
					this.attendanceTimeOfDailyMap, this.workInformationOfDailyMap, this.statutoryWorkingTimeWeek,
					aggregateTotalWorkingTime, this, repositories);
			
			// 通常・変形労働時間勤務の月単位の時間を集計する
			regAndIrgTime.aggregateMonthlyHours(
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.isRetireMonth, this.workplaceId, this.employmentCd,
					this.aggrSettingMonthly, this.holidayAdditionOpt,
					aggrValue.getAggregateTotalWorkingTime(),
					this.statutoryWorkingTimeMonth, repositories);
			
			// 通常・変形労働勤務の逆時系列割り当て
			
		}
		if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			val aggrSetOfFlex = this.aggrSettingMonthly.getFlexWork();
			
			// フレックス勤務の月別実績を集計する
			aggrValue = flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					flexAggregateMethod, aggrSetOfFlex, this.attendanceTimeOfDailyMap,
					aggregateTotalWorkingTime, this, repositories);
			
			// フレックス勤務の月単位の時間を集計する
			flexTime.aggregateMonthlyHours(this.companyId, this.employeeId, this.yearMonth, this.datePeriod,
					flexAggregateMethod, this.workplaceId, this.employmentCd, aggrSetOfFlex, this.holidayAdditionOpt,
					aggrValue.getAggregateTotalWorkingTime(),
					this.prescribedWorkingTimeMonth, this.statutoryWorkingTimeMonth, repositories);
			
			// フレックス時間勤務の逆時系列割り当て
			
		}
		
		if (aggrValue != null){
		
			// 丸め時間を割り当てる
			
			// 時間外超過内訳に割り当てる
			
		}
	}
	
	/**
	 * 週割増時間を逆時系列で割り当てる
	 * @param weekPermiumProcPeriod 週割増処理期間
	 * @param weekPremiumTime 週単位の週割増時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void assignWeeklyPremiumTimeByReverseTimeSeries(
			DatePeriod weekPermiumProcPeriod,
			AttendanceTimeMonth weekPremiumTime,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 「週割増・月割増を求める」を取得する
		ExcessOutsideTimeSet excessOutsideTimeSet = new ExcessOutsideTimeSet();
		if (this.workingSystem == WorkingSystem.REGULAR_WORK){
			val aggrSetOfReg = this.aggrSettingMonthly.getRegularWork();
			excessOutsideTimeSet = aggrSetOfReg.getExcessOutsideTimeSet();
		}
		if (this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			val aggrSetOfIrg = this.aggrSettingMonthly.getIrregularWork();
			excessOutsideTimeSet = aggrSetOfIrg.getExcessOutsideTimeSet();
		}
		if (!excessOutsideTimeSet.isAskPremium()) return;
		
		// 「時間外超過設定」を確認する
		if (!this.outsideOTSetOpt.isPresent()) return;
		if (this.outsideOTSetOpt.get().getCalculationMethod() != OutsideOTCalMed.TIME_SERIES) return;
		
		// 「週単位の週割増時間」を「月別実績の時間外超過」に加算する
		this.excessOutsideWork.addWeeklyTotalPremiumTime(weekPremiumTime.v());
		
		// 「期間．終了日」を処理日にする
		GeneralDate procDate = weekPermiumProcPeriod.end();
		while (procDate.afterOrEquals(weekPermiumProcPeriod.start())){
			
			// 週割増時間を日単位で割り当てる
			this.monthlyDetail.assignWeeklyPremiumTimeByDayUnit(procDate, weekPremiumTime,
					aggregateTotalWorkingTime, this.workInformationOfDailyMap, this, repositories);
			
			// 処理日を更新する
			procDate = procDate.addDays(-1);
		}
	}
	
	/**
	 * 残業・振替の処理順序を取得する（逆時系列用）
	 * @param workInfo 勤務情報
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 残業振替区分リスト（処理順）
	 */
	public List<ProcAtrOverTimeAndTransfer> getOverTimeAndTransferOrder(
			WorkInformation workInfo, RepositoriesRequiredByMonthlyAggr repositories){
		
		List<ProcAtrOverTimeAndTransfer> returnOrder = new ArrayList<>();
		
		// 就業時間帯コードを取得する
		WorkTimeCode workTimeCd = workInfo.getWorkTimeCode();
		if (workTimeCd.toString() == "") {
			returnOrder.add(ProcAtrOverTimeAndTransfer.OVER_TIME);
			return returnOrder;
		}
		
		// 代休振替設定を取得する
		val workTimezoneCommonSetOpt = GetWorkTimezoneCommonSet.get(this.companyId, workTimeCd.v(), repositories);
		if (!workTimezoneCommonSetOpt.isPresent()){
			returnOrder.add(ProcAtrOverTimeAndTransfer.OVER_TIME);
			return returnOrder;
		}
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.FromOverTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			// 代休振替設定区分を取得する
			val transferSetAtr = subHolTransferSet.getSubHolTransferSetAtr();
			if (transferSetAtr == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
				// 指定した時間を代休とする時
				returnOrder.add(ProcAtrOverTimeAndTransfer.OVER_TIME);
				returnOrder.add(ProcAtrOverTimeAndTransfer.TRANSFER);
				return returnOrder;
			}
			else {
				// 一定時間を超えたら代休とする時
				returnOrder.add(ProcAtrOverTimeAndTransfer.TRANSFER);
				returnOrder.add(ProcAtrOverTimeAndTransfer.OVER_TIME);
				return returnOrder;
			}
		}
		returnOrder.add(ProcAtrOverTimeAndTransfer.OVER_TIME);
		return returnOrder;
	}
	
	/**
	 * 休出・振替の処理順序を取得する（逆時系列用）
	 * @param workInfo 勤務情報
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 休出振替区分リスト（処理順）
	 */
	public List<ProcAtrHolidayWorkAndTransfer> getHolidayWorkAndTransferOrder(
			WorkInformation workInfo, RepositoriesRequiredByMonthlyAggr repositories){
		
		List<ProcAtrHolidayWorkAndTransfer> returnOrder = new ArrayList<>();
		
		// 就業時間帯コードを取得する
		WorkTimeCode workTimeCd = workInfo.getWorkTimeCode();
		if (workTimeCd.toString() == "") {
			returnOrder.add(ProcAtrHolidayWorkAndTransfer.HOLIDAY_WORK);
			return returnOrder;
		}
		
		// 代休振替設定を取得する
		val workTimezoneCommonSetOpt = GetWorkTimezoneCommonSet.get(this.companyId, workTimeCd.v(), repositories);
		if (!workTimezoneCommonSetOpt.isPresent()){
			returnOrder.add(ProcAtrHolidayWorkAndTransfer.HOLIDAY_WORK);
			return returnOrder;
		}
		val subHolTimeSets = workTimezoneCommonSetOpt.get().getSubHolTimeSet();
		for (val subHolTimeSet : subHolTimeSets){
			if (subHolTimeSet.getOriginAtr() != CompensatoryOccurrenceDivision.WorkDayOffTime) continue;
			val subHolTransferSet = subHolTimeSet.getSubHolTimeSet();
			
			// 代休振替設定．使用区分を取得する
			if (!subHolTransferSet.isUseDivision()) break;
			
			// 代休振替設定区分を取得する
			val transferSetAtr = subHolTransferSet.getSubHolTransferSetAtr();
			if (transferSetAtr == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
				// 指定した時間を代休とする時
				returnOrder.add(ProcAtrHolidayWorkAndTransfer.HOLIDAY_WORK);
				returnOrder.add(ProcAtrHolidayWorkAndTransfer.TRANSFER);
				return returnOrder;
			}
			else {
				// 一定時間を超えたら代休とする時
				returnOrder.add(ProcAtrHolidayWorkAndTransfer.TRANSFER);
				returnOrder.add(ProcAtrHolidayWorkAndTransfer.HOLIDAY_WORK);
				return returnOrder;
			}
		}
		returnOrder.add(ProcAtrHolidayWorkAndTransfer.HOLIDAY_WORK);
		return returnOrder;
	}
}
