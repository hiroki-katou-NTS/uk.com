/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.employee;

import java.util.List;

/**
 * The Interface UseContactSettingRepository.
 */
public interface UseContactSettingRepository {

	/**
	 * Find by employee id.
	 *
	 * @param employeeId the employee id
	 * @param companyId the company id
	 * @return the list
	 */
	List<UseContactSetting> findByEmployeeId(String employeeId, String companyId);

	/**
	 * Adds the.
	 *
	 * @param useContactSetting the use contact setting
	 * @param companyId the company id
	 */
	void add(UseContactSetting useContactSetting,String companyId);
}
