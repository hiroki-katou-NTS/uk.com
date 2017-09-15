/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown;

import java.util.List;

/**
 * The Interface OvertimeBRDItemRepository.
 */
public interface OvertimeBRDItemRepository {
	
	/**
	 * Save all.
	 *
	 * @param overtimeBreakdownItems the overtime breakdown items
	 * @param companyId the company id
	 */
	public void saveAll(List<OvertimeBRDItem> overtimeBreakdownItems, String companyId);
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OvertimeBRDItem> findAll(String companyId);
	
	/**
	 * Find all use.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OvertimeBRDItem> findAllUse(String companyId);

}
