package nts.uk.ctx.at.schedule.pubimp.shift.businesscalendar.daycalendar;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.UseSet;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.daycalendar.ObtainDeadlineDatePub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ObtainDeadlineDatePubImpl implements ObtainDeadlineDatePub {
	
	@Inject
	private CalendarCompanyRepository calendarCompanyRepository;
	
	@Inject
	private CalendarWorkPlaceRepository calendarWorkPlaceRepository;
	
	@Override
	public GeneralDate obtainDeadlineDate(GeneralDate targetDate, Integer specDayNo, String workplaceID,
			String companyID) {
		GeneralDate loopDate = targetDate.addDays(1);
		do {
			Optional<CalendarWorkplace> opCalendarWorkplace = calendarWorkPlaceRepository
					.findCalendarWorkplaceByDate(workplaceID, loopDate);
			if(opCalendarWorkplace.isPresent()){
				if(!opCalendarWorkplace.get().getWorkingDayAtr().equals(UseSet.workingDay)){
					loopDate = loopDate.addDays(1);
					continue;
				}
			} else {
				Optional<CalendarCompany> opCalendarCompany = calendarCompanyRepository
						.findCalendarCompanyByDate(companyID, loopDate);
				if(!opCalendarCompany.isPresent()){
					loopDate = loopDate.addDays(1);
					continue;
				}
				if(!opCalendarCompany.get().getWorkingDayAtr().equals(UseSet.workingDay)){
					loopDate = loopDate.addDays(1);
					continue;
				}
			}
			loopDate = loopDate.addDays(1);
			if(loopDate.before(targetDate.addDays(specDayNo))){
				loopDate = loopDate.addDays(1);
				continue;
			}
		} while (loopDate.equals(targetDate.addDays(specDayNo)));
		return loopDate;
	}

}
