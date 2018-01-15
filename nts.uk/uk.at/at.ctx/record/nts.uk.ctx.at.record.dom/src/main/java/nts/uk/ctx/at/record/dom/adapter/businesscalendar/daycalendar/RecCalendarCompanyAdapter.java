package nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar;

import nts.arc.time.GeneralDate;

public interface RecCalendarCompanyAdapter {
	
	CalendarInfoImport findCalendarCompany(String companyID, String workplaceID, String classCD, GeneralDate date);

}
