package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
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
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param attendanceTimeOfMonthly 月別実績の勤怠時間
	 */
	public ExcessOutsideWorkMng(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			WorkingSystem workingSystem, AttendanceTimeOfMonthly attendanceTimeOfMonthly){
		
		this.monthlyDetail = new MonthlyDetail(attendanceTimeOfMonthly);
		this.excessOutsideWorkDetail = new ExcessOutsideWorkDetail();
		this.excessOutsideWork = attendanceTimeOfMonthly.getExcessOutsideWork();
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.datePeriod = datePeriod;
		this.workingSystem = workingSystem;
	}
	
	/**
	 * 集計
	 * @param monthlyCalculation 月別実績の月の計算
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(
			MonthlyCalculation monthlyCalculation,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 労働制を確認する
		if (this.workingSystem == WorkingSystem.REGULAR_WORK || this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の時間外超過を集計する
			this.aggregateExcessOutsideWork(monthlyCalculation, null, repositories);
		}
		if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス集計方法を確認する
			val aggrSetOfFlex = monthlyCalculation.getAggrSettingMonthly().getFlexWork();
			val flexAggregateMethod = aggrSetOfFlex.getAggregateMethod();
			if (flexAggregateMethod == FlexAggregateMethod.PRINCIPLE){
				
				// 原則集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(monthlyCalculation, flexAggregateMethod, repositories);
			}
			if (flexAggregateMethod == FlexAggregateMethod.FOR_CONVENIENCE){
				
				// 原則集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(monthlyCalculation, FlexAggregateMethod.PRINCIPLE, repositories);
				
				// 便宜上集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(monthlyCalculation, flexAggregateMethod, repositories);
				
				// 原則集計と便宜上集計の結果を比較する
			}
		}
		
		// 超休精算
	}
	
	/**
	 * 時間外超過を集計する
	 * @param monthlyCalculation 月別実績の月の計算
	 * @param flexAggregateMethod フレックス集計方法
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateExcessOutsideWork(
			MonthlyCalculation monthlyCalculation,
			FlexAggregateMethod flexAggregateMethod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 時間外超過設定を確認する
		val outsideOTSetOpt = repositories.getOutsideOTSet().findById(this.companyId);
		OutsideOTCalMed calcMethod = OutsideOTCalMed.TIME_SERIES;
		if (outsideOTSetOpt.isPresent()) calcMethod = outsideOTSetOpt.get().getCalculationMethod();
		if (calcMethod == OutsideOTCalMed.DECISION_AFTER){
			
			// 集計後に求める
			this.askAfterAggregate(monthlyCalculation, flexAggregateMethod, repositories);
		}
		if (calcMethod == OutsideOTCalMed.TIME_SERIES){
			
			// 時系列で求める
			this.askbyTimeSeries(monthlyCalculation, flexAggregateMethod, repositories);
		}
	}
	
	/**
	 * 集計後に求める
	 * @param monthlyCalculation 月別実績の月の計算
	 * @param flexAggregateMethod フレックス集計方法
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void askAfterAggregate(
			MonthlyCalculation monthlyCalculation,
			FlexAggregateMethod flexAggregateMethod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		val workplaceId = monthlyCalculation.getWorkplaceId();
		val employmentCd = monthlyCalculation.getEmploymentCd();
		val isRetireMonth = monthlyCalculation.isRetireMonth();
		val aggrSettingMonthly = monthlyCalculation.getAggrSettingMonthly();
		val legalTransferOrderSet = monthlyCalculation.getLegalTransferOrderSet();
		val holidayAdditionOpt = monthlyCalculation.getHolidayAdditionOpt();
		val attendanceTimeOfDailyMap = monthlyCalculation.getAttendanceTimeOfDailyMap();
		val statutoryWorkingTimeWeek = monthlyCalculation.getStatutoryWorkingTimeWeek();
		val statutoryWorkingTimeMonth = monthlyCalculation.getStatutoryWorkingTimeMonth();
		//val prescribedWorkingTimeWeek = monthlyCalculation.getPrescribedWorkingTimeWeek();
		val prescribedWorkingTimeMonth = monthlyCalculation.getPrescribedWorkingTimeMonth();
		
		val regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		val flexTime = new FlexTimeOfMonthly();
		AggregateTotalWorkingTime totalWorkingTime = new AggregateTotalWorkingTime(); 
		
		// 労働制を確認する
		if (workingSystem == WorkingSystem.REGULAR_WORK || workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の月別実績を集計する
			totalWorkingTime = regAndIrgTime.aggregateMonthly(
					this.companyId, this.employeeId, this.yearMonth, this.datePeriod, this.workingSystem,
					MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, aggrSettingMonthly, legalTransferOrderSet,
					holidayAdditionOpt, attendanceTimeOfDailyMap, statutoryWorkingTimeWeek, repositories);
			
			// 通常・変形労働時間勤務の月単位の時間を集計する
			regAndIrgTime.aggregateMonthlyHours(
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.datePeriod, this.workingSystem,
					MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, isRetireMonth, workplaceId, employmentCd,
					aggrSettingMonthly, holidayAdditionOpt, totalWorkingTime,
					statutoryWorkingTimeMonth, repositories);
		}
		if (workingSystem == WorkingSystem.FLEX_TIME_WORK){
			val aggrSetOfFlex = aggrSettingMonthly.getFlexWork();
			
			// フレックス勤務の月別実績を集計する
			totalWorkingTime = flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					flexAggregateMethod, aggrSetOfFlex, attendanceTimeOfDailyMap, repositories);
			
			// フレックス勤務の月単位の時間を集計する
			flexTime.aggregateMonthlyHours(this.companyId, this.employeeId, this.yearMonth, this.datePeriod,
					flexAggregateMethod, workplaceId, employmentCd, aggrSetOfFlex, holidayAdditionOpt,
					totalWorkingTime, prescribedWorkingTimeMonth, statutoryWorkingTimeMonth, repositories);
		}
		
		// 実働時間の集計
		totalWorkingTime.aggregateActualWorkingTime(this.datePeriod, this.workingSystem, regAndIrgTime, flexTime);
		
		// 時間外超過明細．丸め後合計時間に移送する
		
		// 月別実績の時間外超過に移送する
		
		// 時間外超過内訳に割り当てる
		
	}
	
	/**
	 * 時系列で求める
	 * @param monthlyCalculation 月別実績の月の計算
	 * @param flexAggregateMethod フレックス集計方法
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void askbyTimeSeries(
			MonthlyCalculation monthlyCalculation,
			FlexAggregateMethod flexAggregateMethod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		val workplaceId = monthlyCalculation.getWorkplaceId();
		val employmentCd = monthlyCalculation.getEmploymentCd();
		val isRetireMonth = monthlyCalculation.isRetireMonth();
		val aggrSettingMonthly = monthlyCalculation.getAggrSettingMonthly();
		val legalTransferOrderSet = monthlyCalculation.getLegalTransferOrderSet();
		val holidayAdditionOpt = monthlyCalculation.getHolidayAdditionOpt();
		val attendanceTimeOfDailyMap = monthlyCalculation.getAttendanceTimeOfDailyMap();
		val statutoryWorkingTimeWeek = monthlyCalculation.getStatutoryWorkingTimeWeek();
		val statutoryWorkingTimeMonth = monthlyCalculation.getStatutoryWorkingTimeMonth();
		//val prescribedWorkingTimeWeek = monthlyCalculation.getPrescribedWorkingTimeWeek();
		val prescribedWorkingTimeMonth = monthlyCalculation.getPrescribedWorkingTimeMonth();
		
		val regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		val flexTime = new FlexTimeOfMonthly();
		AggregateTotalWorkingTime totalWorkingTime = new AggregateTotalWorkingTime(); 
		
		// 労働制を確認する
		if (workingSystem == WorkingSystem.REGULAR_WORK || workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の月別実績を集計する
			totalWorkingTime = regAndIrgTime.aggregateMonthly(
					this.companyId, this.employeeId, this.yearMonth, this.datePeriod, this.workingSystem,
					MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, aggrSettingMonthly, legalTransferOrderSet,
					holidayAdditionOpt, attendanceTimeOfDailyMap, statutoryWorkingTimeWeek, repositories);
			
			// 通常・変形労働時間勤務の月単位の時間を集計する
			regAndIrgTime.aggregateMonthlyHours(
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.datePeriod, this.workingSystem,
					MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, isRetireMonth, workplaceId, employmentCd,
					aggrSettingMonthly, holidayAdditionOpt, totalWorkingTime,
					statutoryWorkingTimeMonth, repositories);
			
			// 通常・変形労働勤務の逆時系列割り当て
			
		}
		if (workingSystem == WorkingSystem.FLEX_TIME_WORK){
			val aggrSetOfFlex = aggrSettingMonthly.getFlexWork();
			
			// フレックス勤務の月別実績を集計する
			totalWorkingTime = flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					flexAggregateMethod, aggrSetOfFlex, attendanceTimeOfDailyMap, repositories);
			
			// フレックス勤務の月単位の時間を集計する
			flexTime.aggregateMonthlyHours(this.companyId, this.employeeId, this.yearMonth, this.datePeriod,
					flexAggregateMethod, workplaceId, employmentCd, aggrSetOfFlex, holidayAdditionOpt,
					totalWorkingTime, prescribedWorkingTimeMonth, statutoryWorkingTimeMonth, repositories);
			
			// フレックス時間勤務の逆時系列割り当て
			
		}
		
		// 丸め時間を割り当てる
		
		// 時間外超過内訳に割り当てる
		
	}
}
