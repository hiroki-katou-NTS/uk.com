/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.workplace.affiliate;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface AffWorkplaceAdapter.
 */
public interface AffWorkplaceAdapter {
	
	/**
	 * Find by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the aff workplace dto
	 */
	Optional<AffWorkplaceDto> findBySid(String employeeId, GeneralDate baseDate);
	
	/**
	 * KIF 001
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<AffWorkPlaceSidImport> findBySidAndDate(String employeeId, GeneralDate baseDate);
}
