package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

@Getter
/** 月別実績の労働時間 */
public class LaborTimeOfMonthly implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	/** 実働時間 */
	private AttendanceTimeMonth actualWorkTime;
	
	/** 総計算時間 */
	private AttendanceTimeMonth totalCalcTime;
	
	/** 計算差異時間 */
	private AttendanceTimeMonth calcDiffTime;
	
	private LaborTimeOfMonthly(AttendanceTimeMonth actualWorkTime, 
			AttendanceTimeMonth totalCalcTime, AttendanceTimeMonth calcDiffTime) {
		
		this.actualWorkTime = actualWorkTime;
		this.totalCalcTime = totalCalcTime;
		this.calcDiffTime = calcDiffTime;
	}
	
	public static LaborTimeOfMonthly empty() {
		
		return new LaborTimeOfMonthly(new AttendanceTimeMonth(0), new AttendanceTimeMonth(0), new AttendanceTimeMonth(0));
	}
	
	public static LaborTimeOfMonthly of(AttendanceTimeMonth actualWorkTime, 
			AttendanceTimeMonth totalCalcTime, AttendanceTimeMonth calcDiffTime) {
		
		return new LaborTimeOfMonthly(actualWorkTime, totalCalcTime, calcDiffTime);
	}
	
	/** ○労働時間 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTime) {
		if (attendanceTime == null) {
			return;
		}
		
		this.actualWorkTime = this.actualWorkTime.addHours(attendanceTime.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getActualTime().valueAsMinutes());
		
		this.totalCalcTime = this.totalCalcTime.addHours(attendanceTime.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getTotalCalcTime().valueAsMinutes());
		
		this.calcDiffTime = this.calcDiffTime.addHours(attendanceTime.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getCalcDiffTime().valueAsMinutes());
	}
	
	public void sum(LaborTimeOfMonthly target) {
		this.actualWorkTime = this.actualWorkTime.addMinutes(target.actualWorkTime.valueAsMinutes());
		this.calcDiffTime = this.calcDiffTime.addMinutes(target.calcDiffTime.valueAsMinutes());
		this.totalCalcTime = this.totalCalcTime.addMinutes(target.totalCalcTime.valueAsMinutes());
	}
}
