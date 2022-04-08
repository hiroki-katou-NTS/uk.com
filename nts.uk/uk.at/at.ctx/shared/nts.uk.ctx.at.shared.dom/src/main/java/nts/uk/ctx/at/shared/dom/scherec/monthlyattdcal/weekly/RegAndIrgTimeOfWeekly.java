package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.WorkingSystemChangeCheckService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.WorkingSystemChangeCheckService.WorkingSystemChangeState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.TargetPremiumTimeWeek;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.GetAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.PremiumAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekStart;

/**
 * 週別の通常変形時間
 * @author shuichi_ishida
 */
@Getter
public class RegAndIrgTimeOfWeekly implements Cloneable {

	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	
	/** 週割増処理期間 */
	private DatePeriod weekPremiumProcPeriod;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 */
	public RegAndIrgTimeOfWeekly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		
		this.weekPremiumProcPeriod = new DatePeriod(GeneralDate.min(), GeneralDate.min());
		this.errorInfos = new ArrayList<>();
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
	 * @param weekPeriod 週期間
	 * @param period 期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param weekStart 週開始
	 * @param premiumTimeOfPrevMonLast 前月の最終週の週割増対象時間
	 * @param verticalTotalMethod 月別実績の縦計方法
	 */
	public void aggregatePremiumTime(Require require, String companyId, String employeeId, DatePeriod weekPeriod,
			DatePeriod period, WorkingSystem workingSystem, MonthlyAggregateAtr aggregateAtr,
			SettingRequiredByReg settingsByReg, SettingRequiredByDefo settingsByDefo,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, WeekStart weekStart,
			AttendanceTimeMonth premiumTimeOfPrevMonLast, AggregateMethodOfMonthly verticalTotalMethod,
			MonthlyCalculatingDailys monthlyCalcDailys) {

		// 週割増を集計する期間を求める
		this.weekPremiumProcPeriod = new DatePeriod(weekPeriod.start(), weekPeriod.end());

		// 労働制を確認する
		if (workingSystem == WorkingSystem.REGULAR_WORK){
			
			// 加算設定　取得　（割増用）
			val addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByReg.getHolidayAdditionMap());
			if (addSet.getErrorInfo().isPresent()){
				this.errorInfos.add(addSet.getErrorInfo().get());
			}
			
			// 「週割増・月割増を求める」を取得する
			boolean isAskPremium = false;
			if (aggregateAtr == MonthlyAggregateAtr.MONTHLY){
				isAskPremium = settingsByReg.getRegularAggrSet().getAggregateTimeSet().isSurchargeWeekMonth();
			}
			if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
				isAskPremium = settingsByReg.getRegularAggrSet().getExcessOutsideTimeSet().isSurchargeWeekMonth();
			}
			if (isAskPremium){
				
				// 通常勤務の週割増時間を集計する
				this.aggregateWeeklyPremiumTimeOfRegular(require, companyId, employeeId, this.weekPremiumProcPeriod,
						period, addSet, aggregateTotalWorkingTime, settingsByReg.getStatutoryWorkingTimeWeek(),
						weekStart, premiumTimeOfPrevMonLast, verticalTotalMethod, monthlyCalcDailys, aggregateAtr);
			}
		}
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){

			// 加算設定　取得　（割増用）
			val addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByDefo.getHolidayAdditionMap());
			if (addSet.getErrorInfo().isPresent()){
				this.errorInfos.add(addSet.getErrorInfo().get());
			}
			
			// 変形労働勤務の週割増時間を集計する
			this.aggregateWeeklyPremiumTimeOfIrregular(require, companyId, employeeId, this.weekPremiumProcPeriod,
					period, addSet, aggregateTotalWorkingTime, settingsByDefo.getStatutoryWorkingTimeWeek(),
					weekStart, premiumTimeOfPrevMonLast, verticalTotalMethod, monthlyCalcDailys, aggregateAtr);
		}
	}

	/**
	 * 通常勤務の週割増時間を集計する
	 * @param companyId 会社ID
	 * @param sid 社員ID
	 * @param weekPeriod 週割増処理期間
	 * @param period 期間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param statutoryWorkingTimeWeek 週間法定労働時間
	 * @param weekStart 週開始
	 * @param premiumTimeOfPrevMonLast 前月の最終週の週割増対象時間
	 */
	private void aggregateWeeklyPremiumTimeOfRegular(Require require, String companyId, String sid, DatePeriod weekPeriod, 
			DatePeriod period, AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeWeek, WeekStart weekStart, AttendanceTimeMonth premiumTimeOfPrevMonLast, 
			AggregateMethodOfMonthly verticalTotalMethod, MonthlyCalculatingDailys monthlyCalcDailys, 
			MonthlyAggregateAtr aggregateAtr) {
		
		// 通常勤務の週割増時間の対象となる時間を求める
		val targetPremiumTimeWeekOfReg = TargetPremiumTimeWeek.askPremiumTimeWeek(require,
				companyId, sid, weekPeriod, addSet, aggregateTotalWorkingTime, 
				premiumTimeOfPrevMonLast, true, WorkingSystem.REGULAR_WORK,
				monthlyCalcDailys, aggregateAtr);
		val targetPremiumTimeWeek = targetPremiumTimeWeekOfReg.getPremiumTimeWeek();

		// 法定労働時間を按分するか確認する　（期間が7日未満　の時、按分する）
		AttendanceTimeMonth targetStatutoryWorkingTime = distributeStatutoryWorkTime(require, sid, WorkingSystem.REGULAR_WORK,
				weekPeriod, period, statutoryWorkingTimeWeek, weekStart, verticalTotalMethod);
		
		// 週割増対象時間と法定労働時間を比較する
		if (targetPremiumTimeWeek.lessThanOrEqualTo(targetStatutoryWorkingTime)) return;
		
		// 週割増対象時間が法定労働時間を超えた分だけ週単位の週割増時間に入れる
		int weekUnit = targetPremiumTimeWeek.v() - targetStatutoryWorkingTime.v();
		
		// 「週単位の週割増時間」と「当月の週割増対象時間」を比較する
		if (weekUnit > targetPremiumTimeWeekOfReg.getPremiumTimeOfCurrentMonth().v()){
			
			// 「週単位の週割増時間」に「当月の週割増対象時間」を入れる　（当月の週割増対象時間を上限とする）
			weekUnit = targetPremiumTimeWeekOfReg.getPremiumTimeOfCurrentMonth().v();
		}
			
		// 週単位の週割増時間を週割増合計時間に加算する
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(weekUnit);
	}

	/**
	 * 変形労働勤務の週割増時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param weekPeriod 週期間
	 * @param period 期間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param statutoryWorkingTimeWeek 週間法定労働時間
	 * @param weekStart 週開始
	 * @param premiumTimeOfPrevMonLast 前月の最終週の週割増対象時間
	 */
	private void aggregateWeeklyPremiumTimeOfIrregular(Require require, String companyId, String employeeId,
			DatePeriod weekPeriod, DatePeriod period, AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			AttendanceTimeMonth statutoryWorkingTimeWeek, WeekStart weekStart, AttendanceTimeMonth premiumTimeOfPrevMonLast, 
			AggregateMethodOfMonthly verticalTotalMethod, MonthlyCalculatingDailys monthlyCalcDailys, 
			MonthlyAggregateAtr aggregateAtr) {
		
		// 変形労働勤務の週割増時間の対象となる時間を求める
		val targetPremiumTimeWeekOfIrg = TargetPremiumTimeWeek.askPremiumTimeWeek(require,
				companyId, employeeId, weekPeriod, addSet, aggregateTotalWorkingTime, 
				premiumTimeOfPrevMonLast, true, WorkingSystem.VARIABLE_WORKING_TIME_WORK, 
				monthlyCalcDailys, aggregateAtr);
		val targetPremiumTimeWeek = targetPremiumTimeWeekOfIrg.getPremiumTimeWeek();

		// （計画）所定労働時間を取得する
		val prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		val recordPresctibedWorkingTime = prescribedWorkingTime.getTotalSchedulePrescribedWorkingTime(weekPeriod);

		// 法定労働時間を按分するか確認する　（期間が7日未満　の時、按分する）
		AttendanceTimeMonth targetStatutoryWorkingTime = distributeStatutoryWorkTime(require, employeeId,
				WorkingSystem.VARIABLE_WORKING_TIME_WORK, weekPeriod, period, statutoryWorkingTimeWeek,
				weekStart, verticalTotalMethod);
		
		// 法定労働時間と所定労働時間を比較する
		int weekUnit = 0;
		if (targetStatutoryWorkingTime.greaterThanOrEqualTo(recordPresctibedWorkingTime.v())){
			
			// 週割増対象時間と法定労働時間を比較する
			if (targetPremiumTimeWeek.lessThanOrEqualTo(targetStatutoryWorkingTime.v())) return;
			
			// 週割増対象時間が法定労働時間を超えた分だけ週単位の週割増時間に入れる
			weekUnit = targetPremiumTimeWeek.v() - targetStatutoryWorkingTime.v();
		}
		else {
			
			// 週割増対象時間と所定労働時間を比較する
			if (targetPremiumTimeWeek.lessThanOrEqualTo(recordPresctibedWorkingTime.v())) return;
			
			// 週割増対象時間が所定労働時間を超えた分だけ週単位の週割増時間に入れる
			weekUnit = targetPremiumTimeWeek.v() - recordPresctibedWorkingTime.v();
		}
		
		// 「週単位の週割増時間」と「当月の週割増対象時間」を比較する
		if (weekUnit > targetPremiumTimeWeekOfIrg.getPremiumTimeOfCurrentMonth().v()){
			
			// 「週単位の週割増時間」から「前月の最終週の週割増時間」を引く
			weekUnit -= targetPremiumTimeWeekOfIrg.getPremiumTimeOfPrevMonth().v();
		}
			
		// 週単位の週割増時間を週割増合計時間に加算する
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(weekUnit);
	}

	/** ○法定労働時間を按分する */
	private AttendanceTimeMonth distributeStatutoryWorkTime(Require require, String sid, 
			WorkingSystem workingSystem, DatePeriod weekPeriod, DatePeriod period,
			AttendanceTimeMonth statutoryWorkingTimeWeek, WeekStart weekStart, 
			AggregateMethodOfMonthly verticalSetting) {
		
		/** ○按分するか確認する */
		if (confirmDistributeStatutoryWorkTime(require, sid, 
				workingSystem, weekPeriod, period, weekStart, verticalSetting)) {
			/** ○法定労働時間を按分する */
			int statutoryWorkingMinutesDay = statutoryWorkingTimeWeek.v() / 7;
			return new AttendanceTimeMonth(statutoryWorkingMinutesDay * daysIn(weekPeriod));
		}
		
		return statutoryWorkingTimeWeek;
	}
	
	/** 按分するか確認する */
	private boolean confirmDistributeStatutoryWorkTime(Require require, String sid, 
			WorkingSystem workingSystem, DatePeriod weekPeriod, DatePeriod period,
			WeekStart weekStart, AggregateMethodOfMonthly verticalSetting) {
		
		/** 週開始を取得する */
		if (weekStart == WeekStart.TighteningStartDate) {
			
			/** 按分すべきか */
			return shouldDistributeStatutoryWorkTime(weekPeriod);
		} else {
			
			/** 締め開始日じゃない場合の確認 */
			return confirmDistributeInNotClosureDayCase(require, sid, 
					workingSystem, weekPeriod, period, verticalSetting);
		}
	}
	
	/** 締め開始日じゃない場合の確認 */
	private boolean confirmDistributeInNotClosureDayCase(Require require, String sid, 
			WorkingSystem workingSystem, DatePeriod weekPeriod, DatePeriod period,
			AggregateMethodOfMonthly verticalSetting) {
		
		/** 最終週かを確認する */
		if (weekPeriod.end().equals(period.end())) {
			
			/** 週割増に前月の最終週を含めて計算するかを確認する */
			if (!verticalSetting.isCalcWithPreviousMonthLastWeek()) {
				/** 按分すべきか */
				return shouldDistributeStatutoryWorkTime(weekPeriod);
			}
			
			/** 次の期間の労働制が処理期間と一緒かを確認する */
			if (WorkingSystemChangeCheckService.isSameWorkingSystemWithNextPeriod(require, sid, weekPeriod, workingSystem) == WorkingSystemChangeState.CHANGED) {
				/** 按分すべきか */
				return shouldDistributeStatutoryWorkTime(weekPeriod);
			}
		} else {

			/** 週割増に前月の最終週を含めて計算するかを確認する */
			if (!verticalSetting.isCalcWithPreviousMonthLastWeek()) {
				/** 按分すべきか */
				return shouldDistributeStatutoryWorkTime(weekPeriod);
			}
		}
		
		return false;
	}
	
	/** 按分すべきか */
	private boolean shouldDistributeStatutoryWorkTime(DatePeriod weekPeriod) {
		if (daysIn(weekPeriod) < 7) 
			return true;
		
		return false;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(RegAndIrgTimeOfWeekly target){

		GeneralDate startDate = this.weekPremiumProcPeriod.start();
		GeneralDate endDate = this.weekPremiumProcPeriod.end();
		if (startDate.after(target.weekPremiumProcPeriod.start())) startDate = target.weekPremiumProcPeriod.start();
		if (endDate.before(target.weekPremiumProcPeriod.end())) endDate = target.weekPremiumProcPeriod.end();
		this.weekPremiumProcPeriod = new DatePeriod(startDate, endDate);
		
		this.weeklyTotalPremiumTime = this.weeklyTotalPremiumTime.addMinutes(target.weeklyTotalPremiumTime.v());
		this.errorInfos.addAll(target.errorInfos);
	}
	
	private int daysIn(DatePeriod weekPeriod) {
		
		return weekPeriod.start().daysTo(weekPeriod.end()) + 1;
	}
	
	public static interface Require extends WorkingSystemChangeCheckService.Require,
		TargetPremiumTimeWeek.Require {}
}
