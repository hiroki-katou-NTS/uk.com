/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface WorkdayoffFrameRepository.
 */
public interface WorkdayoffFrameRepository {

	/**
	 * Find workdayoff frame.
	 *
	 * @param companyId the company id
	 * @param workdayoffFrNo the workdayoff fr no
	 * @return the optional
	 */
	Optional<WorkdayoffFrame> findWorkdayoffFrame(CompanyId companyId, int workdayoffFrNo);

	/**
	 * Update.
	 *
	 * @param workdayoffFrame the workdayoff frame
	 */
	void update(WorkdayoffFrame workdayoffFrame);

	/**
	 * Gets the all workdayoff frame.
	 *
	 * @param companyId the company id
	 * @return the all workdayoff frame
	 */
	List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId);

	/**
	 * Find by use atr.
	 *
	 * @param companyId the company id
	 * @param useAtr the use atr
	 * @return the list
	 */
	List<WorkdayoffFrame> findByUseAtr(String companyId, int useAtr);

	/**
	 * Gets the workdayoff frame by.
	 *
	 * @param companyId the company id
	 * @param workdayoffFrNos the workdayoff fr nos
	 * @return the workdayoff frame by
	 */
	List<WorkdayoffFrame> getWorkdayoffFrameBy(String companyId, List<Integer> workdayoffFrNos);

}
