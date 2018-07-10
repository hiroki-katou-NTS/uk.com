package nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror;

import nts.arc.time.GeneralDate;

public interface GetNumberOfRemainingHolidaysRepository {

	int getNumberOfRemainingHolidays(String employeeId, GeneralDate referenceDate);
}
