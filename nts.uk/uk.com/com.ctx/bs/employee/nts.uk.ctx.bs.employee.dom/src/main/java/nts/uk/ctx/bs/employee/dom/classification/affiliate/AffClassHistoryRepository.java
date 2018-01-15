/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.classification.affiliate;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface AffClassHistoryRepository.
 */
public interface AffClassHistoryRepository {

	/**
	 * Search classification.
	 *
	 * @param baseDate the base date
	 * @param classificationCodes the classification codes
	 * @return the list
	 */
	List<AffClassHistory> searchClassification(GeneralDate baseDate, 
			List<String> classificationCodes);

	/**
	 * Search classification.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param classificationCodes the classification codes
	 * @return the list
	 */
	List<AffClassHistory> searchClassification(List<String> employeeIds, 
			GeneralDate baseDate, List<String> classificationCodes);

	/**
	 * Find by.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<AffClassHistory> getAssignedClassificationBy(String employeeId, GeneralDate baseDate);
}
