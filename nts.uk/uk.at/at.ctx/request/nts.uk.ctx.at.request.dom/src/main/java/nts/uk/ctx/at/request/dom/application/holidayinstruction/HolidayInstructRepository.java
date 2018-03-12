package nts.uk.ctx.at.request.dom.application.holidayinstruction;

import nts.arc.time.GeneralDate;

public interface HolidayInstructRepository {
	public HolidayInstruct getHolidayWorkInstruct(GeneralDate instructDate,String targetPerson);
}
