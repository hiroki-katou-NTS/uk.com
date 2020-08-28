package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import java.util.ArrayList;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;

public class WorkInfoOfDailyAttendanceHelper {
	public static WorkInfoOfDailyAttendance getWorkInfoOfDailyAttendanceDefault() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		return workInfoOfDailyAttendance;
	}
	
	public static WorkInfoOfDailyAttendance getData(WorkInformation recordInfo) {
		return new WorkInfoOfDailyAttendance(recordInfo, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
	}
}
