package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetAddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetVacationAddTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.PremiumAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 当月の変形期間繰越時間
 * @author shuichi_ishida
 */
@Getter
public class IrregularPeriodCarryforwardsTimeOfCurrent {

	/** 時間 */
	private AttendanceTimeMonthWithMinus time;
	/** 加算した休暇使用時間 */
	private AttendanceTimeMonth addedVacationUseTime;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 */
	public IrregularPeriodCarryforwardsTimeOfCurrent(){
		
		this.time = new AttendanceTimeMonthWithMinus(0);
		this.addedVacationUseTime = new AttendanceTimeMonth(0);
		this.errorInfos = new ArrayList<>();
	}
	
	/**
	 * 変形期間繰越時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param holidayAdditionMap 休暇加算時間設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param statutoryWorkingTimeMonth 月間法定労働時間
	 */
	public void aggregate(String companyId, String employeeId, DatePeriod datePeriod,
			AttendanceTimeMonth weeklyTotalPremiumTime,
			Map<String, AggregateRoot> holidayAdditionMap,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeMonth){

		val targetPremiumTimeMonth = new TargetPremiumTimeMonth();
		
		// 加算設定　取得　（割増用）
		val addSet = GetAddSet.get(WorkingSystem.VARIABLE_WORKING_TIME_WORK, PremiumAtr.PREMIUM, holidayAdditionMap);
		if (addSet.getErrorInfo().isPresent()){
			this.errorInfos.add(addSet.getErrorInfo().get());
		}
		
		// 「変形労働勤務の加算設定」を取得する
		switch (this.getAddMethod(holidayAdditionMap)){
		
		case ADD_FOR_SHORTAGE:
			// 月割増対象時間（休暇加算前）を求める
			targetPremiumTimeMonth.askTime(companyId, employeeId, datePeriod, weeklyTotalPremiumTime,
					addSet, aggregateTotalWorkingTime, statutoryWorkingTimeMonth, false);
			val beforeAddVacation = targetPremiumTimeMonth.getTime();
			
			// 月割増対象時間（休暇加算前）を〃（休暇加算後）に入れる
			AttendanceTimeMonthWithMinus afterAddVacation = new AttendanceTimeMonthWithMinus(
					beforeAddVacation.v());
			
			// 月割増対象時間（休暇加算後）＜0 の時、不足分を加算する
			if (afterAddVacation.lessThan(0)) {
				
				// 加算設定　取得　（不足時用）
				val addSetWhenShortage = GetAddSet.get(
						WorkingSystem.VARIABLE_WORKING_TIME_WORK, PremiumAtr.WHEN_SHORTAGE, holidayAdditionMap);
				if (addSetWhenShortage.getErrorInfo().isPresent()){
					this.errorInfos.add(addSetWhenShortage.getErrorInfo().get());
				}
				
				// 加算する休暇時間を取得する
				AttendanceTimeMonth vacationAddTime = GetVacationAddTime.getTime(
						datePeriod, aggregateTotalWorkingTime.getVacationUseTime(), addSetWhenShortage);
				
				// 月割増対象時間（休暇加算後）に、休暇加算時間を加算する
				afterAddVacation = afterAddVacation.addMinutes(vacationAddTime.v());
				
				// 休暇加算後が 0 を超えていたら、0にする　（加算した休暇使用時間を、0 になるまで加算した時間に補正する）
				if (afterAddVacation.greaterThan(0)){
					vacationAddTime = vacationAddTime.minusMinutes(afterAddVacation.v());
					afterAddVacation = new AttendanceTimeMonthWithMinus(0);
				}
				
				// 加算した時間を、加算した休暇使用時間に退避しておく
				this.addedVacationUseTime = vacationAddTime;
			}
	
			// 月割増対象時間（休暇加算後）を当月の変形期間繰越時間に入れる
			this.time = afterAddVacation;
			break;
			
		case NOT_ADD:
			// 月割増対象時間（休暇加算後）を求める　（休暇加算しない）
			targetPremiumTimeMonth.askTime(companyId, employeeId, datePeriod, weeklyTotalPremiumTime,
					addSet, aggregateTotalWorkingTime, statutoryWorkingTimeMonth, false);
			this.time = targetPremiumTimeMonth.getTime();
			this.addedVacationUseTime = new AttendanceTimeMonth(0);
			break;
			
		case ADD:
			// 月割増対象時間（休暇加算後）を求める　（休暇加算する）
			targetPremiumTimeMonth.askTime(companyId, employeeId, datePeriod, weeklyTotalPremiumTime,
					addSet, aggregateTotalWorkingTime, statutoryWorkingTimeMonth, true);
			this.time = targetPremiumTimeMonth.getTime();
			this.addedVacationUseTime = targetPremiumTimeMonth.getAddedVacationUseTime();
			break;
		}
	}

	/**
	 * 加算方法
	 * @author shuichu_ishida
	 */
	private enum ProcAtrAddMethod{
		ADD,
		NOT_ADD,
		ADD_FOR_SHORTAGE;
	}
	
	/**
	 * 変形労働勤務の加算設定（加算方法）を取得する
	 * @param holidayAdditionMap
	 */
	private ProcAtrAddMethod getAddMethod(Map<String, AggregateRoot> holidayAdditionMap){
	
		// 変形労働勤務の加算設定を取得する
		if (!holidayAdditionMap.containsKey("irregularWork")) return ProcAtrAddMethod.ADD;
		val setOfIrregular = (WorkDeformedLaborAdditionSet)holidayAdditionMap.get("irregularWork");
		val holidayCalcMethodSet = setOfIrregular.getVacationCalcMethodSet();
		
		boolean isPremiumAdd = false;		// 割増計算：休暇分を含める＝加算する なら true
		boolean isWorkTimeAdd = false;		// 就業計算：休暇分を含める＝加算する なら true
		
		val premiumCalcMethod = holidayCalcMethodSet.getPremiumCalcMethodOfHoliday();
		if (premiumCalcMethod.getAdvanceSet().isPresent()){
			if (premiumCalcMethod.getAdvanceSet().get().getIncludeVacationSet().getAddition() == NotUseAtr.USE){
				isPremiumAdd = true;
			}
		}
		val workTimeCalcMethod = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday();
		if (workTimeCalcMethod.getAdvancedSet().isPresent()){
			if (workTimeCalcMethod.getAdvancedSet().get().getIncludeVacationSet().getAddition() == NotUseAtr.USE){
				isWorkTimeAdd = true;
			}
		}
		
		if (!isPremiumAdd && isWorkTimeAdd){
			// 不足時加算
			return ProcAtrAddMethod.ADD_FOR_SHORTAGE;
		}
		if (!isPremiumAdd && !isWorkTimeAdd){
			// 加算しない
			return ProcAtrAddMethod.NOT_ADD;
		}
		// 加算する
		return ProcAtrAddMethod.ADD;
	}
}
