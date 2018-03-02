package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 合計時間
 * @author shuichu_ishida
 */
@Getter
public class TotalTime {

	/** 就業時間 */
	private AttendanceTimeMonth workTime;
	/** 残業時間 */
	private Map<OverTimeFrameNo, OverTimeFrameTotalTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, HolidayWorkFrameTotalTime> holidayWorkTime;
	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** 所定内割増時間 */
	private AttendanceTimeMonth withinPrescribedPremiumTime;
	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	
	/**
	 * コンストラクタ
	 */
	public TotalTime(){
		
		this.workTime = new AttendanceTimeMonth(0);
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
		this.flexExcessTime = new AttendanceTimeMonth(0);
		this.withinPrescribedPremiumTime = new AttendanceTimeMonth(0);
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * 丸め後の合計時間を設定する
	 * @param totalTimeBeforeRound 丸め前合計時間
	 * @param roundingSet 月別実績の丸め設定
	 */
	public void setTotalTimeAfterRound(
			TotalTimeBeforeRound totalTimeBeforeRound,
			RoundingSetOfMonthly roundingSet){
		
		int attendanceItemId = 0;

		// 就業時間を丸める
		attendanceItemId = AttendanceItemOfMonthly.WORK_TIME.value;
		this.workTime =
				roundingSet.excessOutsideRound(attendanceItemId, totalTimeBeforeRound.getWorkTime());
		
		// 残業時間を丸める
		for (val overTimeBeforeRound : totalTimeBeforeRound.getOverTime().values()){
			val overTimeFrameNo = overTimeBeforeRound.getOverTimeFrameNo();
			val overTimeItemId = AttendanceItemOfMonthly.OVER_TIME_01.value + overTimeFrameNo.v() - 1;
			val calcOverTimeItemId = AttendanceItemOfMonthly.CALC_OVER_TIME_01.value + overTimeFrameNo.v() - 1;
			val transTimeItemId = AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value + overTimeFrameNo.v() - 1;
			val calcTransTimeItemId = AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value + overTimeFrameNo.v() - 1;
			
			this.overTime.putIfAbsent(overTimeFrameNo, OverTimeFrameTotalTime.of(
					overTimeFrameNo,
					new TimeMonthWithCalculation(
							roundingSet.excessOutsideRound(
									overTimeItemId, overTimeBeforeRound.getOverTime().getTime()),
							roundingSet.excessOutsideRound(
									calcOverTimeItemId, overTimeBeforeRound.getOverTime().getCalcTime())),
					new TimeMonthWithCalculation(
							roundingSet.excessOutsideRound(
									transTimeItemId, overTimeBeforeRound.getTransferOverTime().getTime()),
							roundingSet.excessOutsideRound(
									calcTransTimeItemId, overTimeBeforeRound.getTransferOverTime().getCalcTime()))
					));
		}
		
		// 休出時間を丸める
		for (val holidayWorkTimeBeforeRound : totalTimeBeforeRound.getHolidayWorkTime().values()){
			val holidayWorkFrameNo = holidayWorkTimeBeforeRound.getHolidayWorkFrameNo();
			val holidayWorkTimeItemId = AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value + holidayWorkFrameNo.v() - 1;
			val calcHolidayWorkTimeItemId = AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value + holidayWorkFrameNo.v() - 1;
			val transTimeItemId = AttendanceItemOfMonthly.TRANSFER_TIME_01.value + holidayWorkFrameNo.v() - 1;
			val calcTransTimeItemId = AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value + holidayWorkFrameNo.v() - 1;
			
			this.holidayWorkTime.putIfAbsent(holidayWorkFrameNo, HolidayWorkFrameTotalTime.of(
					holidayWorkFrameNo,
					new TimeMonthWithCalculation(
							roundingSet.excessOutsideRound(
									holidayWorkTimeItemId, holidayWorkTimeBeforeRound.getHolidayWorkTime().getTime()),
							roundingSet.excessOutsideRound(
									calcHolidayWorkTimeItemId, holidayWorkTimeBeforeRound.getHolidayWorkTime().getCalcTime())),
					new TimeMonthWithCalculation(
							roundingSet.excessOutsideRound(
									transTimeItemId, holidayWorkTimeBeforeRound.getTransferTime().getTime()),
							roundingSet.excessOutsideRound(
									calcTransTimeItemId, holidayWorkTimeBeforeRound.getTransferTime().getCalcTime()))
					));
		}
		
		// フレックス超過時間を丸める
		attendanceItemId = AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value;
		this.flexExcessTime =
				roundingSet.excessOutsideRound(attendanceItemId, totalTimeBeforeRound.getFlexExcessTime());
		
		// 所定内割増時間を丸める
		attendanceItemId = AttendanceItemOfMonthly.WITHIN_PRESCRIBED_PREMIUM_TIME.value;
		this.withinPrescribedPremiumTime =
				roundingSet.excessOutsideRound(attendanceItemId, totalTimeBeforeRound.getWithinPrescribedPremiumTime());
		
		// 週割増合計時間を丸める
		attendanceItemId = AttendanceItemOfMonthly.WEEKLY_TOTAL_PREMIUM_TIME.value;
		this.weeklyTotalPremiumTime =
				roundingSet.excessOutsideRound(attendanceItemId, totalTimeBeforeRound.getWeeklyTotalPremiumTime());
		
		// 月割増合計時間を丸める
		attendanceItemId = AttendanceItemOfMonthly.MONTHLY_TOTAL_PREMIUM_TIME.value;
		this.monthlyTotalPremiumTime =
				roundingSet.excessOutsideRound(attendanceItemId, totalTimeBeforeRound.getMonthlyTotalPremiumTime());
	}
}
