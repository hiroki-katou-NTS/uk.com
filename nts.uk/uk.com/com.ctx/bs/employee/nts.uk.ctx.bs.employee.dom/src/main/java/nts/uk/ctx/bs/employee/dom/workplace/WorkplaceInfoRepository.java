/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.Optional;

/**
 * The Interface WorkplaceInfoRepository.
 */
public interface WorkplaceInfoRepository {

	/**
	 * Find by wkp id.
	 *
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WorkplaceInfo> find(String companyId,String wkpId,String historyId);
}
