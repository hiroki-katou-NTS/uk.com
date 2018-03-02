package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;


/**
 * The Interface WorkplaceMonthDaySettingGetMemento.
 */
public interface WorkplaceMonthDaySettingGetMemento {
	
	 /**
 	 * Gets the company id.
 	 *
 	 * @return the company id
 	 */
	CompanyId getCompanyId();
	
	
 	/**
	  * Gets the workplace ID.
	  *
	  * @return the workplace ID
	  */
	 String getWorkplaceID();
	
	 /**
 	 * Gets the management year.
 	 *
 	 * @return the management year
 	 */
 	Year getManagementYear();
	
	 /**
 	 * Gets the public holiday month settings.
 	 *
 	 * @return the public holiday month settings
 	 */
 	List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings();
}
