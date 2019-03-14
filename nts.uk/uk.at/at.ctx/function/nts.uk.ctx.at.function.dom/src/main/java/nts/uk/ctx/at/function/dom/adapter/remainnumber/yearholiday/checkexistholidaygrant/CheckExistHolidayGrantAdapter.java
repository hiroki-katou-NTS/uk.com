package nts.uk.ctx.at.function.dom.adapter.remainnumber.yearholiday.checkexistholidaygrant;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
/**
 * 
 * @author tutk
 *
 */
public interface CheckExistHolidayGrantAdapter {
	public boolean checkExistHolidayGrantAdapter(String employeeId,GeneralDate date,Period period) ;
}
