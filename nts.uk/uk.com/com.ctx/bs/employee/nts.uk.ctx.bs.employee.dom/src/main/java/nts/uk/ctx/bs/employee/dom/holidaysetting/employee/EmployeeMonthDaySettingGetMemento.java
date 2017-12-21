package nts.uk.ctx.bs.employee.dom.holidaysetting.employee;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.PublicHolidayMonthSetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;


/**
 * The Interface EmployeeMonthDaySettingGetMemento.
 */
public interface EmployeeMonthDaySettingGetMemento {
	
	 /**
 	 * Gets the company id.
 	 *
 	 * @return the company id
 	 */
	CompanyId getCompanyId();
	
	 /**
 	 * Gets the employee id.
 	 *
 	 * @return the employee id
 	 */
 	String getEmployeeId();
	
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
