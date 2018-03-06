/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.Optional;

/**
 * The Interface StartMonthRepository.
 */
public interface StartMonthRepository {

	/**
      * Gets the start month by cid.
      *
      * @param companyId the company id
      * @return the start month by cid
      */
     Optional<StartMonth> getStartMonthByCid(String companyId);
}
