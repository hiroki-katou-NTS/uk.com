package nts.uk.ctx.bs.employee.dom.holidaysetting.employment;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.PublicHolidayMonthSetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;

/**
 * The Interface EmploymentMonthDaySettingGetMemento.
 */
public interface EmploymentMonthDaySettingGetMemento {
	
	 /**
 	 * Gets the company id.
 	 *
 	 * @return the company id
 	 */
 	CompanyId getCompanyId();
	
	 
 	/**
	  * Gets the employment code.
	  *
	  * @return the employment code
	  */
	 String getEmploymentCode();
	
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
