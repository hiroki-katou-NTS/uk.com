package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.interval;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/** 月別実績のインターバル時間 */
@Getter
public class IntervalTimeOfMonthly implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;

	/** 時間*/
	private AttendanceTimeMonth time;
	
	/** 免除時間 */
	private AttendanceTimeMonth exemptionTime;
	
	private IntervalTimeOfMonthly(AttendanceTimeMonth time, AttendanceTimeMonth exemptionTime) {
		this.time = time;
		this.exemptionTime = exemptionTime;
	}
	
	public static IntervalTimeOfMonthly empty() {
		return new IntervalTimeOfMonthly(new AttendanceTimeMonth(0), new AttendanceTimeMonth(0));
	}
	
	public static IntervalTimeOfMonthly of(AttendanceTimeMonth time, AttendanceTimeMonth exemptionTime) {
		return new IntervalTimeOfMonthly(time, exemptionTime);
	}
	
	public void sum(IntervalTimeOfMonthly target) {
		this.time = this.time.addMinutes(target.time.valueAsMinutes());
		this.exemptionTime = this.exemptionTime.addMinutes(target.exemptionTime.valueAsMinutes());
	}

	/** ○インターバル時間 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTime) {
		if (attendanceTime == null) {
			return;
		}
		
		val intervalTime = attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getIntervalTime().getIntervalTime().valueAsMinutes();
		
		addIntervalTime(intervalTime);
		
		int exemptionTime = 0;
		for (val late : attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily()) {
			exemptionTime += late.getExemptionTime().getExemptionTime().valueAsMinutes();
		}
		
		addIntervalExemptionTime(exemptionTime);
	}
	
	public void addIntervalTime(int minutes) {
		this.time = this.time.addMinutes(minutes);
	}
	
	public void addIntervalExemptionTime(int minutes) {
		this.exemptionTime = this.exemptionTime.addMinutes(minutes);
	}
}