package nts.uk.ctx.at.record.pub.remainnumber.yearholiday.checkexistholidaygrant;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

public interface CheckExistHolidayGrantPub {
	public boolean checkExistHolidayGrant(String employeeId,GeneralDate date,Period period) ;
}
