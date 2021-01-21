/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language;

import java.util.List;

/**
 * The Interface OvertimeNameLangRepository.
 */
public interface OvertimeNameLangRepository {
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param languageId the language id
	 * @return the list
	 */
	public List<OvertimeNameLang> findAll(String companyId, String languageId);
	
	
	/**
	 * Save all.
	 *
	 * @param domains the domains
	 */
	public void saveAll(List<OvertimeNameLang> domains);
}
