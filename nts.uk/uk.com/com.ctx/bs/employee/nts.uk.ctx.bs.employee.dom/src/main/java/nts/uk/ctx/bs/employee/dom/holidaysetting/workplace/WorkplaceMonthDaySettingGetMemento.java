package nts.uk.ctx.bs.employee.dom.holidaysetting.workplace;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.PublicHolidayMonthSetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;


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
