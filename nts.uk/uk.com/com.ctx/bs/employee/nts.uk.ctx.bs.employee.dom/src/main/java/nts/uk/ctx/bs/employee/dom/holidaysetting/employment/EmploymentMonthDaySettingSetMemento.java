package nts.uk.ctx.bs.employee.dom.holidaysetting.employment;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.PublicHolidayMonthSetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;


/**
 * The Interface EmploymentMonthDaySettingSetMemento.
 */
public interface EmploymentMonthDaySettingSetMemento {
	
 	/**
	  * Sets the company id.
	  *
	  * @param companyId the new company id
	  */
	 void setCompanyId(CompanyId companyId);
 	
	 /**
 	 * Sets the employment code.
 	 *
 	 * @param employmentCode the new employment code
 	 */
 	void setEmploymentCode(String employmentCode);
	
	
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
