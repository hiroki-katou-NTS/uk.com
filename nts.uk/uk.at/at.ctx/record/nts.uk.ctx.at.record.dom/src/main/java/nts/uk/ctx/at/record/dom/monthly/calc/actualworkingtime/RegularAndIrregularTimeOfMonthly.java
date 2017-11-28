package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の通常変形時間
 * @author shuichi_ishida
 */
@Getter
public class RegularAndIrregularTimeOfMonthly {

	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	/** 変形労働時間 */
	private IrregularWorkingTimeOfMonthly irregularWorkingTime;
	
	/**
	 * コンストラクタ
	 */
	public RegularAndIrregularTimeOfMonthly(){
		
		this.irregularWorkingTime = new IrregularWorkingTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param monthlyTotalPremiumTime 月割増合計時間
	 * @param irregularWorkingTime 変形労働時間
	 * @return 日別実績の通常変形時間
	 */
	public static RegularAndIrregularTimeOfMonthly of(
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AttendanceTimeMonth monthlyTotalPremiumTime,
			IrregularWorkingTimeOfMonthly irregularWorkingTime){
		
		RegularAndIrregularTimeOfMonthly domain = new RegularAndIrregularTimeOfMonthly();
		domain.weeklyTotalPremiumTime = weeklyTotalPremiumTime;
		domain.monthlyTotalPremiumTime = monthlyTotalPremiumTime;
		domain.irregularWorkingTime = irregularWorkingTime;
		return domain;
	}
	
	/**
	 * 月別実績を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void aggregateMonthly(
			String companyId,
			String employeeId,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		// 週開始を取得する
		//*****（２次）
		
		// 前月の最終週のループ
		//*****（保留中）

		// パラメータ「期間．開始日」を処理日にする
		
		// 処理をする期間の日数分ループ
		
			// 日別実績を集計する
			
			// 週の集計をする日か確認する
			//*****（２次）
			
			// 週別実績を集計する
			//*****（２次）
			
			// パラメータ「集計区分」を確認する
			//*****（２次）
			
			// 週割増時間を逆時系列で割り当てる
			//*****（２次）
			
			// 処理する日が残っているか確認する
		
		// ループ終了
		
	}
	
	/**
	 * 月単位の時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 */
	public void aggregateMonthlyHours(
			String companyId,
			String employeeId,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr){

		// 通常勤務の時
		if (workingSystem.isRegularWork()){
			
			// 通常勤務の月単位の時間を集計する
			
		}
		
		// 変形労働時間勤務の時
		if (workingSystem.isVariableWorkingTimeWork()){
			
			// 変形労働勤務の月単位の時間を集計する
			
		}
	}
}
