package nts.uk.ctx.at.record.pubimp.remainingnumber.annualbreakmanage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManagePub;
import nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.basicinfo.function.calnextholidaygrant.CalNextHolidayGrantService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author tutk
 *
 */
@Stateless
public class CalNextHolidayGrantDefalult implements CalNextHolidayGrantService {

	@Inject
	private AnnualBreakManagePub annualBreakManagePub;
	
	@Override
	public List<NextAnnualLeaveGrant> getCalNextHolidayGrant(String companyId, String employeeId,
			Optional<Period> period) {
		if(!period.isPresent())
			return annualBreakManagePub.calculateNextHolidayGrant(employeeId, null);
		return annualBreakManagePub.calculateNextHolidayGrant(employeeId, new DatePeriod(period.get().getStartDate(), period.get().getEndDate()));
	}

}
