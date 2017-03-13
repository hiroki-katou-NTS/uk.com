/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.reference;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WageTableCodeRefRepository.
 */
public interface WageTableMasterRefRepository {

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<WageTableMasterRef> findAll(String companyCode);

	/**
	 * Find by code.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return the optional
	 */
	Optional<WageTableMasterRef> findByCode(String companyCode, String code);

}
