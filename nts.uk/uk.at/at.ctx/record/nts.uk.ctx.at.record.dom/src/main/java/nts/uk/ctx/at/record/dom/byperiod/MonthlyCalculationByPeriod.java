package nts.uk.ctx.at.record.dom.byperiod;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 期間別の月の計算
 * @author shuichu_ishida
 */
@Getter
public class MonthlyCalculationByPeriod implements Cloneable {

	/** 集計時間 */
	private TotalWorkingTimeByPeriod aggregateTime;
	/** フレックス時間 */
	private FlexTimeByPeriod flexTime;
	/** 総労働時間 */
	private AttendanceTimeMonth totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalSpentTime;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculationByPeriod(){

		this.aggregateTime = new TotalWorkingTimeByPeriod();
		this.flexTime = new FlexTimeByPeriod();
		this.totalWorkingTime = new AttendanceTimeMonth(0);
		this.totalSpentTime = new AggregateTotalTimeSpentAtWork();
	}
	
	/**
	 * ファクトリー
	 * @param aggregateTime 集計時間
	 * @param flexTime フレックス時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalSpentTime 総拘束時間
	 * @return 期間別の月の計算
	 */
	public static MonthlyCalculationByPeriod of(
			TotalWorkingTimeByPeriod aggregateTime,
			FlexTimeByPeriod flexTime,
			AttendanceTimeMonth totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalSpentTime){
		
		MonthlyCalculationByPeriod domain = new MonthlyCalculationByPeriod();
		domain.aggregateTime = aggregateTime;
		domain.flexTime = flexTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalSpentTime = totalSpentTime;
		return domain;
	}
	
	@Override
	public MonthlyCalculationByPeriod clone() {
		MonthlyCalculationByPeriod cloned = new MonthlyCalculationByPeriod();
		try {
			cloned.aggregateTime = this.aggregateTime.clone();
			cloned.flexTime = this.flexTime.clone();
			cloned.totalWorkingTime = new AttendanceTimeMonth(this.totalWorkingTime.v());
			cloned.totalSpentTime = this.totalSpentTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("MonthlyCalculationByPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * 勤怠項目IDに対応する時間を取得する　（丸め処理付き）
	 * @param attendanceItemId 勤怠項目ID
	 * @param roundingSet 月別実績の丸め設定
	 * @param isExcessOutside 時間外超過設定で丸めるかどうか
	 * @return 勤怠月間時間
	 */
	public AttendanceTimeMonth getTimeOfAttendanceItemId(
			int attendanceItemId,
			RoundingSetOfMonthly roundingSet,
			boolean isExcessOutside){

		AttendanceTimeMonth notExistTime = new AttendanceTimeMonth(0);

		val overTimeMap = this.aggregateTime.getOverTime().getAggregateOverTimeMap();
		val hdwkTimeMap = this.aggregateTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
		// 就業時間
		if (attendanceItemId == AttendanceItemOfMonthly.WORK_TIME.value){
			val workTime = this.aggregateTime.getWorkTime().getWorkTime();
			if (isExcessOutside) return roundingSet.excessOutsideRound(attendanceItemId, workTime);
			return roundingSet.itemRound(attendanceItemId, workTime);
		}
		
		// 残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getOverTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getTime());
		}
		
		// 計算残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getOverTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getCalcTime());
		}
		
		// 振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getTransferOverTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getTime());
		}
		
		// 計算振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getTransferOverTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getCalcTime());
		}
		
		// 休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getTime());
		}
		
		// 計算休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getCalcTime());
		}
		
		// 振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getTime());
		}
		
		// 計算振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getCalcTime());
		}
		
		// フレックス超過時間　（フレックス時間のプラス分）
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value){
			val flexExcessMinutes = this.flexTime.getFlexTime().v();
			if (flexExcessMinutes <= 0) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, new AttendanceTimeMonth(flexExcessMinutes));
			}
			return roundingSet.itemRound(attendanceItemId, new AttendanceTimeMonth(flexExcessMinutes));
		}
		
		// 所定内割増時間
		if (attendanceItemId == AttendanceItemOfMonthly.WITHIN_PRESCRIBED_PREMIUM_TIME.value){
			val withinPrescribedPremiumTime = this.aggregateTime.getWorkTime().getWithinPrescribedPremiumTime();
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, withinPrescribedPremiumTime);
			}
			return roundingSet.itemRound(attendanceItemId, withinPrescribedPremiumTime);
		}
		
		return notExistTime;
	}
}
