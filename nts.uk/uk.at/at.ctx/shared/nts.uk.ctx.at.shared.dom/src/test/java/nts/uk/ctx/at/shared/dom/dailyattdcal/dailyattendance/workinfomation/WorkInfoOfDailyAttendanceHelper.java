package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

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
	
	public static WorkInfoAndTimeZone createWorkInfoAndTimeZone(List<TimeZone> listTimeZone) {
		return new WorkInfoAndTimeZone(new WorkType(), new WorkTimeSetting(), listTimeZone);
	}
}
