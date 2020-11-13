/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

/**
 * The Interface EmployeeLoginSettingRepository.
 */
public interface EmployeeLoginSettingRepository {

	/**
	 * Gets the by contract code.
	 *
	 * @param contractCode the contract code
	 * @return the by contract code
	 */
	Optional<EmployeeLoginSetting> getByContractCode(String contractCode);
}
