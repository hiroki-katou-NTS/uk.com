package nts.uk.ctx.at.record.dom.monthly.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の月の計算
 * @author shuichi_ishida
 */
@Getter
public class MonthlyCalculation {

	/** 実働時間 */
	private RegularAndIrregularTimeOfMonthly actualWorkingTime;
	/** フレックス時間 */
	private FlexTimeOfMonthly flexTime;
	/** 法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTime;
	/** 総労働時間 */
	private AggregateTotalWorkingTime totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalTimeSpentAtWork;
	
	/** リポジトリ：日別実績の勤怠時間 */
	//AttendanceTimeOfDailyPerformanceRepository
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculation(){

		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();
	}

	/**
	 * ファクトリー
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 * @param statutoryWorkingTime 法定労働時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalTimeSpentAtWork 総拘束時間
	 * @return 月別実績の月の計算
	 */
	public static MonthlyCalculation of(
			RegularAndIrregularTimeOfMonthly actualWorkingTime,
			FlexTimeOfMonthly flexTime,
			AttendanceTimeMonth statutoryWorkingTime,
			AggregateTotalWorkingTime totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalTimeSpentAtWork){
		
		MonthlyCalculation domain = new MonthlyCalculation();
		domain.actualWorkingTime = actualWorkingTime;
		domain.flexTime = flexTime;
		domain.statutoryWorkingTime = statutoryWorkingTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalTimeSpentAtWork = totalTimeSpentAtWork;
		return domain;
	}
	
	/**
	 * 履歴ごとに月別実績を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 */
	public void aggregate(String companyId, String employeeId, DatePeriod datePeriod, WorkingSystem workingSystem){
		
		// 日別実績の勤怠時間　取得
		//*****（未）　日別実績の勤怠時間を、期間指定で複数取ってくる。
		List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys = new ArrayList<>();	// 受け仮
		//attendanceTimeOfDailys = （リポジトリ：find）;
		
		// 共有項目を集計する
		this.totalWorkingTime.aggregateSharedItem(attendanceTimeOfDailys);
		
		// 通常勤務　or　変形労働　の時
		if (workingSystem.isRegularWork() || workingSystem.isVariableWorkingTimeWork()){
			
			// 通常・変形労働勤務の月別実績を集計する
			this.actualWorkingTime.aggregateMonthly(companyId, employeeId, datePeriod,
					workingSystem, MonthlyAggregateAtr.Monthly, attendanceTimeOfDailys);
			
			// 通常・変形労働勤務の月単位の時間を集計する
			this.actualWorkingTime.aggregateMonthlyHours(companyId, employeeId, datePeriod,
					workingSystem, MonthlyAggregateAtr.Monthly);
		}
		// フレックス時間勤務　の時
		else if (workingSystem == WorkingSystem.FlexTimeWork){
			
			// フレックス勤務の月別実績を集計する
			//*****（仮）　便宜上集計　指定
			this.flexTime.aggregateMonthly(companyId, employeeId, datePeriod,
					workingSystem, MonthlyAggregateAtr.Monthly,
					MonthlyAggregateFlexAtr.ForConvenience, attendanceTimeOfDailys);
			
			// フレックス勤務の月単位の時間を集計する
			//*****（仮）　便宜上集計　指定
			this.flexTime.aggregateMonthlyHours(companyId, employeeId, datePeriod,
					MonthlyAggregateFlexAtr.ForConvenience);
		}

		// 実働時間の集計
		this.totalWorkingTime.aggregateActualWorkingTime();
	}
}
