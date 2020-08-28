package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;

public class WorkScheDisplaySettingHelper {
	public static WorkScheDisplaySetting getWorkSche() {
		return new WorkScheDisplaySetting("companyID", InitDispMonth.CURRENT_MONTH, new OneMonth(new DateInMonth(1, false)));
	}
}
