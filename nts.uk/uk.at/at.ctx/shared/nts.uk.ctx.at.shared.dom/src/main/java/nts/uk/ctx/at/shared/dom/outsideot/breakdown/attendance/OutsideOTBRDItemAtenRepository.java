/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.outsideot.breakdown.attendance;

import java.util.List;

/**
 * The Interface OvertimeBRDItemRepository.
 */
public interface OutsideOTBRDItemAtenRepository {
	
	/**
	 * Save all.
	 *
	 * @param attendanceItemIds the attendance item ids
	 * @param companyId the company id
	 * @param breakdownItemNo the breakdown item no
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
