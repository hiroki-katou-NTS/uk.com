/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.outsideot.breakdown;

import java.util.List;

/**
 * The Interface OutsideOTBRDItemRepository.
 */
public interface OutsideOTBRDItemRepository {
	
	/**
	 * Save all.
	 *
	 * @param overtimeBreakdownItems the overtime breakdown items
	 * @param companyId the company id
	 */
	public void saveAll(List<OutsideOTBRDItem> overtimeBreakdownItems, String companyId);
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutsideOTBRDItem> findAll(String companyId);
	
	/**
	 * Find all use.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutsideOTBRDItem> findAllUse(String companyId);

}
