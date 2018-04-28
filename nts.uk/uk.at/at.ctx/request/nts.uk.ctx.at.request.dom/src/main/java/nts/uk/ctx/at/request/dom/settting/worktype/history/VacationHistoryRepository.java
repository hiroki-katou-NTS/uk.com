/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.settting.worktype.history;

import java.util.List;

/**
 * The Interface VacationHistoryRepository.
 */
public interface VacationHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param vacationHistory the vacation history
	 */
	void add(PlanVacationHistory vacationHistory);

	/**
	 * Update.
	 *
	 * @param vacationHistory the vacation history
	 */
	void update(PlanVacationHistory vacationHistory);

    /**
     * Removes the wkp config hist.
     *
     * @param companyId the company id
     * @param historyId the history id
     */
    void removeWkpConfigHist(String companyId, String historyId);

    /**
     * Find by work type code.
     *
     * @param companyId the company id
     * @param historyId the history id
     * @return the optional
     */
    List<PlanVacationHistory> findByWorkTypeCode(String companyId, String workTypeCode);

}
