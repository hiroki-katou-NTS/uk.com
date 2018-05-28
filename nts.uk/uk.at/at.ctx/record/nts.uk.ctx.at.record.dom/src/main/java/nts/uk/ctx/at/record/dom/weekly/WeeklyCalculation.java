package nts.uk.ctx.at.record.dom.weekly;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.byperiod.FlexTimeByPeriod;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 週別の計算
 * @author shuichi_ishida
 */
@Getter
public class WeeklyCalculation implements Cloneable {

	/** 通常変形時間 */
	private RegAndIrgTimeOfWeekly regAndIrgTime;
	/** フレックス時間 */
	private FlexTimeByPeriod flexTime;
	/** 総労働時間 */
	private AggregateTotalWorkingTime totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalSpentTime;
	/** 36協定時間 */
	private AgreementTimeOfWeekly agreementTime;

	/**
	 * コンストラクタ
	 */
	public WeeklyCalculation(){

		this.regAndIrgTime = new RegAndIrgTimeOfWeekly();
		this.flexTime = new FlexTimeByPeriod();
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalSpentTime = new AggregateTotalTimeSpentAtWork();
		this.agreementTime = new AgreementTimeOfWeekly();
	}

	/**
	 * ファクトリー
	 * @param regAndIrgTime 通常変形時間
	 * @param flexTime フレックス時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalSpentTime 総拘束時間
	 * @param agreementTime 36協定時間
	 * @return 月別実績の月の計算
	 */
	public static WeeklyCalculation of(
			RegAndIrgTimeOfWeekly regAndIrgTime,
			FlexTimeByPeriod flexTime,
			AggregateTotalWorkingTime totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalSpentTime,
			AgreementTimeOfWeekly agreementTime){
		
		WeeklyCalculation domain = new WeeklyCalculation();
		domain.regAndIrgTime = regAndIrgTime;
		domain.flexTime = flexTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalSpentTime = totalSpentTime;
		domain.agreementTime = agreementTime;
		return domain;
	}
	
	@Override
	public WeeklyCalculation clone() {
		WeeklyCalculation cloned = new WeeklyCalculation();
		try {
			cloned.regAndIrgTime = this.regAndIrgTime.clone();
			cloned.flexTime = this.flexTime.clone();
			cloned.totalWorkingTime = this.totalWorkingTime.clone();
			cloned.totalSpentTime = this.totalSpentTime.clone();
			cloned.agreementTime = this.agreementTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("WeeklyCalculation clone error.");
		}
		return cloned;
	}
	
	/**
	 * 週の計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param monthPeriod 月期間
	 * @param weekPeriod 週期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param procYmd 処理日
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param weekStart 週開始
	 */
	public void aggregate(
			String companyId,
			String employeeId,
			DatePeriod monthPeriod,
			DatePeriod weekPeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			GeneralDate procYmd,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			WeekStart weekStart){
		
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE) return;
		
		this.totalWorkingTime = aggregateTotalWorkingTime.clone();
		
		// 「労働制」を判断する
		switch (workingSystem){
		case REGULAR_WORK:
		case VARIABLE_WORKING_TIME_WORK:
			
			// 週割増時間を集計する
			this.regAndIrgTime.aggregatePremiumTime(companyId, employeeId, monthPeriod,
					workingSystem, aggregateAtr, procYmd, settingsByReg, settingsByDefo,
					aggregateTotalWorkingTime, weekStart);
			break;
			
		case FLEX_TIME_WORK:
			
			// フレックス
			break;
			
		case EXCLUDED_WORKING_CALCULATE:
			break;
		}
		
		// 実働時間の集計
		this.totalWorkingTime.aggregateActualWorkingTimeForWeek(
				weekPeriod, workingSystem, this.regAndIrgTime, this.flexTime);
		
		// 36協定時間
	}
}
