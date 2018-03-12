package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;



/**
 * The Interface WorkplaceMonthDaySettingSetMemento.
 */
public interface WorkplaceMonthDaySettingSetMemento {
	
 	/**
	  * Sets the company id.
	  *
	  * @param companyId the new company id
	  */
	 void setCompanyId(CompanyId companyId);
	
	 
	 /**
 	 * Sets the workplace ID.
 	 *
 	 * @param workplaceID the new workplace ID
 	 */
 	void setWorkplaceID(String workplaceID);
	
	
 	/**
	  * Sets the management year.
	  *
	  * @param managementYear the new management year
	  */
	 void setManagementYear(Year managementYear);
	
	
 	/**
	  * Sets the public holiday month settings.
	  *
	  * @param publicHolidayMonthSettings the new public holiday month settings
	  */
	 void setPublicHolidayMonthSettings(List<PublicHolidayMonthSetting> publicHolidayMonthSettings);
}
