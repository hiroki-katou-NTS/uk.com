package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import java.util.ArrayList;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

public class WorkInfoOfDailyAttendanceHelper {
	public static WorkInfoOfDailyAttendance getWorkInfoOfDailyAttendanceDefault() {
		WorkInformation recordInfo1 = new WorkInformation("ty1", "ti1");
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance(recordInfo1, recordInfo1, CalculationState.Calculated, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
		return workInfoOfDailyAttendance;
	}
	
	public static WorkInfoOfDailyAttendance getData(WorkInformation recordInfo) {
		return new WorkInfoOfDailyAttendance(recordInfo, recordInfo, CalculationState.Calculated, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
				DayOfWeek.FRIDAY, new ArrayList<>());
	}
}
