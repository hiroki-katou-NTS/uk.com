package nts.uk.ctx.at.function.ac.remainnumber.yearholiday.checkexistholidaygrant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.remainnumber.yearholiday.checkexistholidaygrant.CheckExistHolidayGrantAdapter;
import nts.uk.ctx.at.record.pub.remainnumber.yearholiday.checkexistholidaygrant.CheckExistHolidayGrantPub;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class CheckExistHolidayGrantAc implements CheckExistHolidayGrantAdapter {

	@Inject
	private CheckExistHolidayGrantPub checkExistHolidayGrantPub;
	
	@Override
	public boolean checkExistHolidayGrantAdapter(String employeeId, GeneralDate date, Period period) {
		return checkExistHolidayGrantPub.checkExistHolidayGrant(employeeId, date, period);
	}

}
