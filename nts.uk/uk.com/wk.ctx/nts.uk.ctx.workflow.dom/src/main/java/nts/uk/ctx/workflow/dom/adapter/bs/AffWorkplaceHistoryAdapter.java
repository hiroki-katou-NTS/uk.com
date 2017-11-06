/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.dom.adapter.bs;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.AffWorkplaceHistoryImport;

/**
 * The Interface AffWorkplaceHistoryAdapter.
 */
public interface AffWorkplaceHistoryAdapter {

	/**
	 * Find by base date.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<AffWorkplaceHistoryImport> findByBaseDate(String employeeId, GeneralDate baseDate);
}
