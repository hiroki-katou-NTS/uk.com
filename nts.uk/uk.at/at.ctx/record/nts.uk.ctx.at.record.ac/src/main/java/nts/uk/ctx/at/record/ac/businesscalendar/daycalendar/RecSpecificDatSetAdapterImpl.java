package nts.uk.ctx.at.record.ac.businesscalendar.daycalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.CalendarInfoImport;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.RecCalendarCompanyAdapter;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationPub;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;

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

	@Override
	public WorkingDayCategory getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date) {
		Integer data = calendarInformationPub.getWorkingDayAtr(companyID, workplaceID, classCD, date);
		if(data != null) {
			return EnumAdaptor.valueOf(data, WorkingDayCategory.class);
		}
		return null;
	}

}
