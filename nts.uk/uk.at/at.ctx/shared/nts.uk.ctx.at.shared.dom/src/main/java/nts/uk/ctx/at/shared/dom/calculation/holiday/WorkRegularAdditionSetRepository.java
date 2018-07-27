/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.util.Optional;

/**
 * The Interface WorkRegularAdditionSetRepository.
 */
public interface WorkRegularAdditionSetRepository {
	Optional<WorkRegularAdditionSet> findByCID(String companyID);
}

