/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import java.util.List;
import java.util.Optional;

/**
 * The Interface FlexSetRepository.
 */
public interface FlexSetRepository {

	/**
	 * Find by CID
	 * 
	 * @param companyId
	 * @return
	 */
	List<FlexSet> findByCompanyId(String companyId);

	/**
	 * Add Flex Set
	 * 
	 * @param flexSet
	 */
	void add(FlexSet flexSet);

	/**
	 * Update Flex Set
	 * 
	 * @param flexSet
	 */
	void update(FlexSet flexSet);

	/**
	 * Find by CID
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<FlexSet> findByCId(String companyId);

}
