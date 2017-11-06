/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.util.List;
import java.util.Optional;

public interface OptionalItemRepository {

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(OptionalItem dom);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the optional
	 */
	Optional<OptionalItem> find(String companyId, String id);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<OptionalItem> findAll(String companyId);

	/**
	 * Find by atr.
	 *
	 * @param companyId the company id
	 * @param atr the atr
	 * @return the list
	 */
	List<OptionalItem> findByAtr(String companyId, int atr);
}
