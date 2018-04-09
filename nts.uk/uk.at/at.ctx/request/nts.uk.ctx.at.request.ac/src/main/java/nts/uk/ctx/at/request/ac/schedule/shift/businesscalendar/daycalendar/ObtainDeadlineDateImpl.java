package nts.uk.ctx.at.request.ac.schedule.shift.businesscalendar.daycalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar.ObtainDeadlineDateAdapter;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.daycalendar.ObtainDeadlineDatePub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ObtainDeadlineDateImpl implements ObtainDeadlineDateAdapter {

	@Inject
	private ObtainDeadlineDatePub obtainDeadlineDatePub;
	
	@Override
	public GeneralDate obtainDeadlineDate(GeneralDate targetDate, Integer specDayNo, String workplaceID,
			String companyID) {
		return obtainDeadlineDatePub.obtainDeadlineDate(targetDate, specDayNo, workplaceID, companyID);
	}

}
