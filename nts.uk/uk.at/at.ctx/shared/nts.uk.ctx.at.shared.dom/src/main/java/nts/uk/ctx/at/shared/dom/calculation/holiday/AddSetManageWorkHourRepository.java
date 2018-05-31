/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.util.Optional;

/**
 * The Interface AddSetManageWorkHourRepository.
 */
public interface AddSetManageWorkHourRepository {
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<AddSetManageWorkHour> findByCid(String companyId);
}

