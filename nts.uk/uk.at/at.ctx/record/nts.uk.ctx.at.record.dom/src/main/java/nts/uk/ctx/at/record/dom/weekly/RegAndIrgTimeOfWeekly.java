package nts.uk.ctx.at.record.dom.weekly;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.AddedVacationUseTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.TargetPremiumTimeMonthOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.TargetPremiumTimeMonthOfRegular;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetAddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 週別の通常変形時間
 * @author shuichu_ishida
 */
@Getter
public class RegAndIrgTimeOfWeekly implements Cloneable {

	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	
	/** 加算した休暇使用時間 */
	private AddedVacationUseTime addedVacationUseTime;
	/** 週割増処理期間 */
	private DatePeriod weekPremiumProcPeriod;
	
	/**
	 * コンストラクタ
	 */
	public RegAndIrgTimeOfWeekly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		
		this.addedVacationUseTime = new AddedVacationUseTime();
		this.weekPremiumProcPeriod = new DatePeriod(GeneralDate.min(), GeneralDate.min());
	}
	
	/**
	 * ファクトリー
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @return 週別の通常変形時間
	 */
	public static RegAndIrgTimeOfWeekly of(
			AttendanceTimeMonth weeklyTotalPremiumTime){

		RegAndIrgTimeOfWeekly domain = new RegAndIrgTimeOfWeekly();
		domain.weeklyTotalPremiumTime = weeklyTotalPremiumTime;
		return domain;
	}
	
	@Override
	public RegAndIrgTimeOfWeekly clone() {
		RegAndIrgTimeOfWeekly cloned = new RegAndIrgTimeOfWeekly();
		try {
			cloned.weeklyTotalPremiumTime = new AttendanceTimeMonth(this.weeklyTotalPremiumTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("RegAndIrgTimeOfWeekly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 週割増時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間(月別集計用)
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param procYmd 処理日
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param weekStart 週開始
	 */
	public void aggregatePremiumTime(
			String companyId,
			String employeeId,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			GeneralDate procYmd,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			WeekStart weekStart){

		// 週集計期間を求める
		this.weekPremiumProcPeriod = new DatePeriod(procYmd.addDays(-6), procYmd);
		if (this.weekPremiumProcPeriod.start().before(datePeriod.start())) {
			this.weekPremiumProcPeriod = new DatePeriod(datePeriod.start(), procYmd);
		}

		// 労働制を確認する
		if (workingSystem == WorkingSystem.REGULAR_WORK){
			
			// 加算設定　取得　（割増用）
			val addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByReg.getHolidayAdditionMap());
			
			// 「週割増・月割増を求める」を取得する
			boolean isAskPremium = false;
			if (aggregateAtr == MonthlyAggregateAtr.MONTHLY){
				isAskPremium = settingsByReg.getRegularAggrSet().getAggregateTimeSet().getSurchargeWeekMonth();
			}
			if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
				isAskPremium = settingsByReg.getRegularAggrSet().getExcessOutsideTimeSet().getSurchargeWeekMonth();
			}
			if (isAskPremium){
				
				// 通常勤務の週割増時間を集計する
				this.aggregateWeeklyPremiumTimeOfRegular(companyId, employeeId, this.weekPremiumProcPeriod,
						addSet, aggregateTotalWorkingTime, settingsByReg.getStatutoryWorkingTimeWeek(), weekStart);
			}
		}
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){

			// 加算設定　取得　（割増用）
			val addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByDefo.getHolidayAdditionMap());
			
			// 変形労働勤務の週割増時間を集計する
			this.aggregateWeeklyPremiumTimeOfIrregular(companyId, employeeId, this.weekPremiumProcPeriod,
					addSet, aggregateTotalWorkingTime, settingsByDefo.getStatutoryWorkingTimeWeek(), weekStart);
		}
	}

	/**
	 * 通常勤務の週割増時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param weekPeriod 週期間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param statutoryWorkingTimeWeek 週間法定労働時間
	 * @param weekStart 週開始
	 */
	private void aggregateWeeklyPremiumTimeOfRegular(
			String companyId,
			String employeeId,
			DatePeriod weekPeriod,
			AddSet addSet,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeWeek,
			WeekStart weekStart){
		
		// 通常勤務の週割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfRegular = new TargetPremiumTimeMonthOfRegular();
		this.addedVacationUseTime = targetPremiumTimeMonthOfRegular.askPremiumTimeMonth(
				companyId, employeeId, weekPeriod, addSet, aggregateTotalWorkingTime);
		val targetPremiumTimeWeek = targetPremiumTimeMonthOfRegular.getTargetPremiumTimeMonth();
		
		// 按分するか確認する　（週開始＝締め開始日　かつ　期間が7日未満　の時、按分する）
		boolean isDistribute = false;
		val periodDays = weekPeriod.start().daysTo(weekPeriod.end()) + 1;
		if (weekStart == WeekStart.TighteningStartDate){
			if (periodDays < 7) isDistribute = true;
		}
		AttendanceTimeMonth targetStatutoryWorkingTime = new AttendanceTimeMonth(statutoryWorkingTimeWeek.v());
		if (isDistribute){
			
			// 法定労働時間を按分する
			int statutoryWorkingMinutesDay = statutoryWorkingTimeWeek.v() / 7;
			targetStatutoryWorkingTime = new AttendanceTimeMonth(statutoryWorkingMinutesDay * periodDays);
		}
		
		// 週割増対象時間と法定労働時間を比較する
		if (targetPremiumTimeWeek.lessThanOrEqualTo(targetStatutoryWorkingTime)) return;
		
		// 週割増対象時間が法定労働時間を超えた分だけ週単位の週割増合計時間に入れる
		this.weeklyTotalPremiumTime = targetPremiumTimeWeek.minusMinutes(targetStatutoryWorkingTime.v());
	}

	/**
	 * 変形労働勤務の週割増時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param weekPeriod 週期間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param statutoryWorkingTimeWeek 週間法定労働時間
	 * @param weekStart 週開始
	 */
	private void aggregateWeeklyPremiumTimeOfIrregular(
			String companyId,
			String employeeId,
			DatePeriod weekPeriod,
			AddSet addSet,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeWeek,
			WeekStart weekStart){
		
		// 変形労働勤務の週割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfIrregular = new TargetPremiumTimeMonthOfIrregular();
		targetPremiumTimeMonthOfIrregular.askPremiumTimeMonth(
				companyId, employeeId, weekPeriod, addSet, aggregateTotalWorkingTime, true);
		val targetPremiumTimeWeek = targetPremiumTimeMonthOfIrregular.getTargetPremiumTimeMonth();

		// （実績）所定労働時間を取得する
		val prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		val recordPresctibedWorkingTime = prescribedWorkingTime.getTotalRecordPrescribedWorkingTime(weekPeriod);
		
		// 按分するか確認する　（週開始＝締め開始日　かつ　期間が7日未満　の時、按分する）
		boolean isDistribute = false;
		val periodDays = weekPeriod.start().daysTo(weekPeriod.end()) + 1;
		if (weekStart == WeekStart.TighteningStartDate){
			if (periodDays < 7) isDistribute = true;
		}
		AttendanceTimeMonth targetStatutoryWorkingTime = new AttendanceTimeMonth(statutoryWorkingTimeWeek.v());
		if (isDistribute){
			
			// 法定労働時間を按分する
			int statutoryWorkingMinutesDay = statutoryWorkingTimeWeek.v() / 7;
			targetStatutoryWorkingTime = new AttendanceTimeMonth(statutoryWorkingMinutesDay * periodDays);
		}
		
		// 法定労働時間と所定労働時間を比較する
		if (targetStatutoryWorkingTime.greaterThanOrEqualTo(recordPresctibedWorkingTime.v())){
			
			// 週割増対象時間と法定労働時間を比較する
			if (targetPremiumTimeWeek.lessThanOrEqualTo(targetStatutoryWorkingTime.v())) return;
			
			// 週割増対象時間が法定労働時間を超えた分だけ週単位の週割増合計時間に入れる
			this.weeklyTotalPremiumTime = targetPremiumTimeWeek.minusMinutes(targetStatutoryWorkingTime.v());
		}
		else {
			
			// 週割増対象時間と所定労働時間を比較する
			if (targetPremiumTimeWeek.lessThanOrEqualTo(recordPresctibedWorkingTime.v())) return;
			
			// 週割増対象時間が所定労働時間を超えた分だけ週単位の週割増合計時間に入れる
			this.weeklyTotalPremiumTime = targetPremiumTimeWeek.minusMinutes(recordPresctibedWorkingTime.v());
		}
	}
}
