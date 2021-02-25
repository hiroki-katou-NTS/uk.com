package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

@Getter
/** 月別実績の休暇使用時間 */
public class HolidayUsageOfMonthly implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	/** 振休使用時間 */
	private AttendanceTimeMonth transferHoliday;
	
	/** 欠勤使用時間 */
	private AttendanceTimeMonth absence;
	
	private HolidayUsageOfMonthly(AttendanceTimeMonth transferHoliday, AttendanceTimeMonth absence) {
		this.transferHoliday = transferHoliday;
		this.absence = absence;
	}
	
	public static HolidayUsageOfMonthly empty() {
		
		return new HolidayUsageOfMonthly(new AttendanceTimeMonth(0), new AttendanceTimeMonth(0));
	}
	
	public static HolidayUsageOfMonthly of(AttendanceTimeMonth transferHoliday, AttendanceTimeMonth absence) {
		
		return new HolidayUsageOfMonthly(transferHoliday, absence);
	}
	
	/** ○休暇使用時間 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTime) {
		if (attendanceTime == null) {
			return;
		}
		
		this.absence = this.absence.addHours(attendanceTime.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getAbsence().getUseTime().valueAsMinutes());
		
		this.transferHoliday = this.transferHoliday.addHours(attendanceTime.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getTransferHoliday().getUseTime().valueAsMinutes());
	}
	
	public void sum(HolidayUsageOfMonthly target) {
		this.absence = this.absence.addMinutes(target.absence.valueAsMinutes());
		this.transferHoliday = this.transferHoliday.addMinutes(target.transferHoliday.valueAsMinutes());
	}
}
