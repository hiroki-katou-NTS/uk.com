/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

/**
 * The Class JobTitleHistoryRepository.
 */
public interface AffJobTitleHistoryRepository {

	/** The first item index. */
	final int FIRST_ITEM_INDEX = 0;

	/**
	 * Search job title history.
	 *
	 * @param baseDate the base date
	 * @param positionIds the position ids
	 * @return the list
	 */
	List<AffJobTitleHistory> searchJobTitleHistory(GeneralDate baseDate, List<String> positionIds);

	/**
	 * Search job title history.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param positionIds the position ids
	 * @return the list
	 */
	List<AffJobTitleHistory> searchJobTitleHistory(List<String> employeeIds,
			GeneralDate baseDate, List<String> positionIds);

	/**
	 * Find all job title history.
	 *
	 * @param baseDate
	 *            the base date
	 * @param employeeIds
	 *            the employee ids
	 * @return the list
	 */
	default List<AffJobTitleHistory> findAllJobTitleHistory(GeneralDate baseDate,
			List<String> employeeIds) {
		// Get result & Return
		return this.findWithRelativeOptions(employeeIds, Collections.emptyList(), baseDate);
	}

	/**
	 * Find by sid.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the list
	 */
	default List<AffJobTitleHistory> findBySid(String employeeId) {
		// Get result & Return
		return this.findWithRelativeOptions(Arrays.asList(employeeId), Collections.emptyList(),
				null);
	}

	/**
	 * Find by sid.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the aff job title history
	 */
	default Optional<AffJobTitleHistory> findBySid(String employeeId, GeneralDate baseDate) {
		// Get result
		List<AffJobTitleHistory> result = this.findWithRelativeOptions(Arrays.asList(employeeId),
				Collections.emptyList(), baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// Return
		return Optional.of(result.get(FIRST_ITEM_INDEX));
	}

	/**
	 * Find by job id.
	 *
	 * @param jobId
	 *            the job id
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	default List<AffJobTitleHistory> findByJobId(String jobId, GeneralDate baseDate) {
		// Get result & Return
		return this.findWithRelativeOptions(Collections.emptyList(), Arrays.asList(jobId),
				baseDate);
	}

	/**
	 * Find with options.
	 *
	 * @param employeeIds the employee ids
	 * @param positionIds the position ids
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<AffJobTitleHistory> findWithRelativeOptions(List<String> employeeIds,
			List<String> positionIds, GeneralDate baseDate);
}
