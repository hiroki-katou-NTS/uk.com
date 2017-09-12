/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown.language;

import java.util.List;

/**
 * The Interface OvertimeLangBRDItemRepository.
 */
public interface OvertimeLangBRDItemRepository {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param languageId the language id
	 * @return the list
	 */
	public List<OvertimeLangBRDItem> findAll(String companyId, String languageId);
	
	
	/**
	 * Save all.
	 *
	 * @param domains the domains
	 */
	public void saveAll(List<OvertimeLangBRDItem> domains);
}
