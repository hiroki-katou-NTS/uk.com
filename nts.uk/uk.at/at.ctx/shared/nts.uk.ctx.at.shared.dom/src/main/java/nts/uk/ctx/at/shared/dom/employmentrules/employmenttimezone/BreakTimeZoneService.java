package nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

public interface BreakTimeZoneService {

	BreakTimeZoneSharedOutPut getBreakTimeZone(String companyId, String workTimeCode, int weekdayHolidayClassification,
			WorkStyle checkWorkDay);

}
