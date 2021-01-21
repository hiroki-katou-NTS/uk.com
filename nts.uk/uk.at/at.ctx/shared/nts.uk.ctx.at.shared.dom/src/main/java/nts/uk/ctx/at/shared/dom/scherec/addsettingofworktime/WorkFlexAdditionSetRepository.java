/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

/**
 * The Interface WorkFlexAdditionSetRepository.
 */
public interface WorkFlexAdditionSetRepository {
	
	/**
	 * Find by cid.
	 *
	 * @param companyID the company ID
	 * @return the optional
	 */
	Optional<WorkFlexAdditionSet> findByCid(String companyID);
}

