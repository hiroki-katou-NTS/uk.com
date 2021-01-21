/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

/**
 * The Interface HourlyPaymentAdditionSetRepository.
 */
public interface HourlyPaymentAdditionSetRepository {
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<HourlyPaymentAdditionSet> findByCid(String companyId);
}

