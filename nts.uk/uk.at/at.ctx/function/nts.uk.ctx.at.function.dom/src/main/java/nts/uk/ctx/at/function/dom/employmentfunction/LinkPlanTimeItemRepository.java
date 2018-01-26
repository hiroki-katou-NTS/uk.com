/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.employmentfunction;

import java.util.List;

/**
 * The Interface LinkPlanTimeItemRepository.
 */
public interface LinkPlanTimeItemRepository {
	
	/**
	 * Find all.
	 *
	 * @param CID the cid
	 * @return the list
	 */
	List<LinkPlanTimeItem> findAll(String CID);
}
