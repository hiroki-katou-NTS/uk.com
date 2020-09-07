package nts.uk.ctx.at.schedule.pubimp.shift.specificdayset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.UseSet;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.service.CalendarInformationOutput;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.service.ICalendarInformationService;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationExport;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CalendarInformationPubImpl implements CalendarInformationPub{
	
	@Inject
	private ICalendarInformationService calendarInformationService;
	
	@Override
	public CalendarInformationExport getCalendarInformation(String companyID, String workplaceID, String classCD,
			GeneralDate date) {
		CalendarInformationOutput calendarInformationOutput = calendarInformationService.getCalendarInformation(companyID, workplaceID, classCD, date);
		return new CalendarInformationExport(calendarInformationOutput.getWorkTypeCD(), calendarInformationOutput.getSiftCD(), calendarInformationOutput.getDate());
	}

	@Override
	public Integer getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date) {
		UseSet useSet =calendarInformationService.getWorkingDayAtr(companyID, workplaceID, classCD, date);
		if(useSet != null)
			return useSet.value;
		return null;
	}

}
