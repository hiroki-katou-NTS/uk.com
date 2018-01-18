/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface AffClassHistItemRepository_ver1.
 */
public interface AffClassHistItemRepository_ver1 {
	
	/**
	 * Gets the by history id.
	 *
	 * @param historyId the history id
	 * @return the by history id
	 */
	Optional<AffClassHistItem_ver1> getByHistoryId(String historyId);
	
	/**
	 * Adds the.
	 *
	 * @param item the item
	 */
	void add(AffClassHistItem_ver1 item); 
	
	/**
	 * Update.
	 *
	 * @param item the item
	 */
	void update(AffClassHistItem_ver1 item); 
	
	/**
	 * Delete.
	 *
	 * @param historyId the history id
	 */
	void delete(String historyId); 
	
	/**
	 * Search classification.
	 *
	 * @param baseDate the base date
	 * @param classificationCodes the classification codes
	 * @return the list
	 */
	List<AffClassHistItem_ver1> searchClassification(GeneralDate baseDate,
			List<String> classificationCodes);
	
	/**
	 * Search classification.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param classificationCodes the classification codes
	 * @return the list
	 */
	List<AffClassHistItem_ver1> searchClassification(List<String> employeeIds,
			GeneralDate baseDate, List<String> classificationCodes);

}
