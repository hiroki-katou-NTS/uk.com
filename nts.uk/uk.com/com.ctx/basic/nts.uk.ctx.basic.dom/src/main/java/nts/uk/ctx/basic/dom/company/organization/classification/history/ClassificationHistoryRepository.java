/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification.history;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface ClassificationHistoryRepository.
 */
public interface ClassificationHistoryRepository {

	/**
	 * Search classification.
	 *
	 * @param baseDate the base date
	 * @param classificationCodes the classification codes
	 * @return the list
	 */
	List<ClassificationHistory> searchClassification(GeneralDate baseDate, 
			List<String> classificationCodes);

	/**
	 * Search classification.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param classificationCodes the classification codes
	 * @return the list
	 */
	List<ClassificationHistory> searchClassification(List<String> employeeIds, 
			GeneralDate baseDate, List<String> classificationCodes);

}
