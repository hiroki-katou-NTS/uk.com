package nts.uk.ctx.at.schedule.dom.displaysetting;

import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;

public class DisplaySettingByWorkplaceHelper {
	public static DisplaySettingByWorkplace getWorkSche() {
		return new DisplaySettingByWorkplace("companyID", InitDispMonth.CURRENT_MONTH, new OneMonth(new DateInMonth(1, false)));
	}
}
