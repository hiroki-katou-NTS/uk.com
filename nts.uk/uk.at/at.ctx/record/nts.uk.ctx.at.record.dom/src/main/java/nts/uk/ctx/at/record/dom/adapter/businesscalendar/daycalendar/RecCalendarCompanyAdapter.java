package nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;

public interface RecCalendarCompanyAdapter {
	
	CalendarInfoImport findCalendarCompany(String companyID, String workplaceID, String classCD, GeneralDate date);
	
	WorkingDayCategory getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date);

	BasicWorkSettingImport getBasicWorkSetting(String companyID, String workplaceID, String classCD,
			Integer workingDayAtr);
}
