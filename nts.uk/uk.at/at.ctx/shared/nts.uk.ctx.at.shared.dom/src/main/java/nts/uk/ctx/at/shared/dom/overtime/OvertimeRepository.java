/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime;

import java.util.List;

/**
 * The Interface OvertimeRepository.
 */
public interface OvertimeRepository {
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<Overtime> findAll(String companyId);
	
	
	/**
	 * Save all.
	 *
	 * @param overtimes the overtimes
	 * @param companyId the company id
	 */
	public void saveAll(List<Overtime> overtimes, String companyId);

}
