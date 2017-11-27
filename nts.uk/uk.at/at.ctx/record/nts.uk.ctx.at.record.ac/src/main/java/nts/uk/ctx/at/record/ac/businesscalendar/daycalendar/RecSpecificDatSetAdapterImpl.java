package nts.uk.ctx.at.record.ac.businesscalendar.daycalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.CalendarInfoImport;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.RecCalendarCompanyAdapter;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationPub;

@Stateless
public class RecSpecificDatSetAdapterImpl implements RecCalendarCompanyAdapter {

	@Inject
	private CalendarInformationPub calendarInformationPub;

	@Override
	public CalendarInfoImport findCalendarCompany(String companyID, String workplaceID, String classCD,
			GeneralDate date) {
		// return calendarInformationPub.getCalendarInformation(companyID,
		// workplaceID, classCD, date).;
		CalendarInfoImport calendarInfoDto = new CalendarInfoImport(
				calendarInformationPub.getCalendarInformation(companyID, workplaceID, classCD, date).getSiftCD(),
				calendarInformationPub.getCalendarInformation(companyID, workplaceID, classCD, date).getDate(),
				calendarInformationPub.getCalendarInformation(companyID, workplaceID, classCD, date).getWorkTypeCD());
		return calendarInfoDto;
	}

}
