package nts.uk.ctx.at.function.ac.workrecord.remainingnumbermanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror.GetNumberOfRemainingHolidaysRepository;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.NumberOfRemainingHolidaysPub;

@Stateless
public class NumberOfRemainingHolidaysAcFinder implements GetNumberOfRemainingHolidaysRepository{

	@Inject
	private NumberOfRemainingHolidaysPub NumberOfRemainingHolidays;
	
	@Override
	public int getNumberOfRemainingHolidays(String employeeId, GeneralDate referenceDate) {
		return NumberOfRemainingHolidays.NumberOfRemainingHolidays(employeeId, referenceDate);
	}

}
