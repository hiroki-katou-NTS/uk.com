/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

/**
 * The Interface SystemConfigRepository.
 */
public interface SystemConfigRepository {

	/**
	 * Gets the system config.
	 *
	 * @return the system config
	 */
	Optional<SystemConfig> getSystemConfig();
}
