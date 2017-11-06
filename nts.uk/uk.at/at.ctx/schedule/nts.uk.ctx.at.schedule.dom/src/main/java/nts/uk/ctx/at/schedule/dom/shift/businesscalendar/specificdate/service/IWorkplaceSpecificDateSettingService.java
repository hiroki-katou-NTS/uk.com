package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.service;

import nts.arc.time.GeneralDate;

public interface IWorkplaceSpecificDateSettingService {
	
	public SpecificDateItemOutput workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
}
