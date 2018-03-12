package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;


/**
 * The Interface EmployeeMonthDaySettingSetMemento.
 */
public interface EmployeeMonthDaySettingSetMemento {
	
 	/**
	  * Sets the company id.
	  *
	  * @param companyId the new company id
	  */
	 void setCompanyId(CompanyId companyId);
	
	 
 	/**
	  * Sets the employee id.
	  *
	  * @param employeeId the new employee id
	  */
	 void setEmployeeId(String employeeId);
	
	
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
