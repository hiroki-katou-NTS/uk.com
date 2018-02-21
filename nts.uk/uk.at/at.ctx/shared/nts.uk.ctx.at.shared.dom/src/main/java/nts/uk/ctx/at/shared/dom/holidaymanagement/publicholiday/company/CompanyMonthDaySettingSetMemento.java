package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

/**
 * The Interface CompanyMonthDaySettingSetMemento.
 */
public interface CompanyMonthDaySettingSetMemento {
	
 	/**
	  * Sets the company id.
	  *
	  * @param companyId the new company id
	  */
	 void setCompanyId(CompanyId companyId);
	
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
