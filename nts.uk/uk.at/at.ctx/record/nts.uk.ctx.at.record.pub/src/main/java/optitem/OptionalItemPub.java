/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package optitem;

import java.util.List;

/**
 * The Interface OptionalItemPub.
 */
public interface OptionalItemPub {

	/**
	 * Gets the optional items.
	 *
	 * @param companyId the company id
	 * @param optionalItemNos the optional item nos
	 * @return the optional items
	 */
	List<OptionalItemExport> getOptionalItems(String companyId, List<Integer> optionalItemNos);
}
