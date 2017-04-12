/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;

/**
 * The Class JpaReportBaseRepository.
 */
public class JpaReportBaseRepository extends JpaRepository{

	/**
	 * Split in param list.
	 *
	 * @param <T> the generic type
	 * @param inputList the input list
	 * @return the list
	 */
	protected <T> List<List<T>> splitInParamList(List<T> inputList) {
		List<List<T>> resultList = new ArrayList<>();
		int fromIndex = 0;
		// NOTE: DURING TO LIMITATION OF NUMBER PARAMETER
		// WE MUST LIMIT EMPLOYEE SIZE WHEN QUERY.
		int maxParamSize = 1000;
		int nextIndex = fromIndex;

		// Split into sub user id h.
		do {
			// Cal next index of sublist.
			nextIndex = fromIndex + maxParamSize;
			if (nextIndex > inputList.size()) {
				nextIndex = inputList.size();
			}

			// Extract sub user id list.
			List<T> subUserIdH = inputList.subList(fromIndex, nextIndex);
			resultList.add(subUserIdH);
			fromIndex = nextIndex;
		} while (nextIndex < inputList.size());

		// Ret.
		return resultList;
	}
}
