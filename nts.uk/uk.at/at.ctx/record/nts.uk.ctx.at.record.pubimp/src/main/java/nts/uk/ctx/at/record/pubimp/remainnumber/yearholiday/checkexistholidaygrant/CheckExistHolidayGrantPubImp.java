package nts.uk.ctx.at.record.pubimp.remainnumber.yearholiday.checkexistholidaygrant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.yearholiday.checkexistholidaygrant.CheckExistHolidayGrant;
import nts.uk.ctx.at.record.pub.remainnumber.yearholiday.checkexistholidaygrant.CheckExistHolidayGrantPub;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class CheckExistHolidayGrantPubImp implements CheckExistHolidayGrantPub {

	
	@Inject
	private CheckExistHolidayGrant checkExistHolidayGrant;
	
	@Override
	public boolean checkExistHolidayGrant(String employeeId, GeneralDate date, Period period) {
		return checkExistHolidayGrant.checkExistHolidayGrant(employeeId, date, period);
	}

}
