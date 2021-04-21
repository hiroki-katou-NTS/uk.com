package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.CompensatoryLeaveUseTimeOfMonthly;

/** 月別実績集計のフレックスの代休扱い方法 */
@AllArgsConstructor
@Getter
public class FlexAggregateCompensatoryTimeSet {

	/** 代休を取得する月にマイナスペナルティをするか */
	private boolean compensatoryPenalty;
	
	/** 時間代休使用時の所定時間控除方法 */
	private TimeCompensatoryUsageDeductMethod timeCompensatoryDeductMethod;
	
	public FlexAggregateCompensatoryTimeSet() {
		this.compensatoryPenalty = false;
		this.timeCompensatoryDeductMethod = TimeCompensatoryUsageDeductMethod.DEDUCT_ON_LEAVEEARLY_LATE_OUTING_TIME_ONLY;
	}
	
	/** 代休時間を引いた後の所定時間を取得する */
	public MonthlyEstimateTime getSubtractedTime(CompensatoryLeaveUseTimeOfMonthly monthlyCompensatoryTime,
			Collection<AttendanceTimeOfDailyAttendance> dailyAttendanceTime, DatePeriod period,
			FlexMonthWorkTimeAggrSet flexWorkTimeAggrSet, MonthlyEstimateTime targetTime) {
		
		/** 代休を取得する月にマイナスペナルティをするかを確認する */
		if(this.compensatoryPenalty) {
			/** 所定労働時間を返す */
			return targetTime;
		}
		
		/** 代休使用時間を求める */
		int deductTime = getCompensatoryUsageTime(monthlyCompensatoryTime, dailyAttendanceTime, flexWorkTimeAggrSet, period);
		
		/** ○代休時間を引く時間を求める */
		return targetTime.minusMinutes(deductTime);
	}
	
	/** 代休使用時間を求める */
	private int getCompensatoryUsageTime(CompensatoryLeaveUseTimeOfMonthly monthlyCompensatoryTime,
			Collection<AttendanceTimeOfDailyAttendance> dailyAttendanceTime,
			FlexMonthWorkTimeAggrSet flexWorkTimeAggrSet, DatePeriod period) {
		
		/** 時間代休使用時の所定時間控除方法を確認する */
		if (this.timeCompensatoryDeductMethod == TimeCompensatoryUsageDeductMethod.DEDUCT_ON_LEAVEEARLY_LATE_OUTING_TIME_ONLY) {
			/** 遅刻、早退、外出の合計時間を取得する*/
			return calcSumLateLeaveEarlyOutingTime(dailyAttendanceTime);
		} else {
			/** ◯休出をフレックスに含めるか確認する */
			if (flexWorkTimeAggrSet.getFlexTimeHandle().isIncludeIllegalHdwk()) {
				/** ◯法定内代休時間の計算 */
				return monthlyCompensatoryTime.calcLegalTime().valueAsMinutes();
			} else {
				/** ◯代休使用時間の取得 */
				return monthlyCompensatoryTime.aggregateTemp(period).valueAsMinutes();
			}
		}
	}
	
	/** 遅刻、早退、外出の合計時間を取得する*/
	private int calcSumLateLeaveEarlyOutingTime(Collection<AttendanceTimeOfDailyAttendance> dailyAttendanceTime) {
		
		/** 合計遅刻時間を取得する */
		val compensatoryLateTime = dailyAttendanceTime.stream()
				.mapToInt(c -> c.getLateTimeOfDaily().stream()
									.mapToInt(l -> l.getOffsetCompensatoryTime().valueAsMinutes())
									.sum())
				.sum(); 
		/** 合計早退時間を取得する */
		val compensatoryLeaveEarlyTime = dailyAttendanceTime.stream()
				.mapToInt(c -> c.getLeaveEarlyTimeOfDaily().stream()
									.mapToInt(l -> l.getOffsetCompensatoryTime().valueAsMinutes())
									.sum())
				.sum(); 
		/** 合計外出時間を取得する */
		val compensatoryOutingTime = dailyAttendanceTime.stream()
				.mapToInt(c -> c.getOutingTimeOfDaily().stream()
									.mapToInt(l -> l.getOffsetCompensatoryTime().valueAsMinutes())
									.sum())
				.sum(); 
		
		/** 遅刻、早退、外出の合計時間を求める */
		return compensatoryLateTime + compensatoryLeaveEarlyTime + compensatoryOutingTime;
	}
	
	/** 時間代休使用時の所定時間控除方法*/
	@AllArgsConstructor
	public static enum TimeCompensatoryUsageDeductMethod {
		
		/** 遅刻、早退、外出の時間分だけ所定時間から控除する */
		DEDUCT_ON_LEAVEEARLY_LATE_OUTING_TIME_ONLY(0),
		/** 代休を使用した時間全てを所定時間から控除する */
		DEDUCT_ON_COMPENSATORY_TIME(1);
		
		public int value;
	}
}
