/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import java.util.Optional;

/**
 * The Interface SuperHD60HConMedRepository.
 */
public interface SuperHD60HConMedRepository {

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<SuperHD60HConMed> findById(String companyId);
	
	
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(SuperHD60HConMed domain);
}
