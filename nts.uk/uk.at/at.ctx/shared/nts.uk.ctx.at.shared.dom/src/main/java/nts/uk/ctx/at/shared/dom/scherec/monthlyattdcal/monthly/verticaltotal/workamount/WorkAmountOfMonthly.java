package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workamount;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceAmountMonth;

/** 月別実績の勤務金額 */
@Getter
public class WorkAmountOfMonthly implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 就業時間金額 */
	private AttendanceAmountMonth workTimeAmount;
	
	public WorkAmountOfMonthly() {
		this.workTimeAmount = new AttendanceAmountMonth(0);
	}
	
	public static WorkAmountOfMonthly of(AttendanceAmountMonth workTimeAmount) {
		
		WorkAmountOfMonthly domain = new WorkAmountOfMonthly();
		domain.workTimeAmount = workTimeAmount;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTime){
		
		if (attendanceTime == null) {
			return;
		}
		
		/** 就業時間金額の集計 */
		this.workTimeAmount = this.workTimeAmount.addAmount(attendanceTime.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getWithinWorkTimeAmount().v());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(WorkAmountOfMonthly target){
		
		this.workTimeAmount = this.workTimeAmount.addAmount(target.getWorkTimeAmount().v());
	}
}
