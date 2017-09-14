/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown.attendance;

import java.util.List;

/**
 * The Interface OvertimeBRDItemRepository.
 */
public interface OvertimeBRDItemAtenRepository {
	
	/**
	 * Save all.
	 *
	 * @param overtimeBreakdownItems the overtime breakdown items
	 * @param companyId the company id
	 */
	public void saveAll(List<Integer> attendanceItemIds, String companyId, int breakdownItemNo);
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<Integer> findAll(String companyId, int breakdownItemNo);
	

}
