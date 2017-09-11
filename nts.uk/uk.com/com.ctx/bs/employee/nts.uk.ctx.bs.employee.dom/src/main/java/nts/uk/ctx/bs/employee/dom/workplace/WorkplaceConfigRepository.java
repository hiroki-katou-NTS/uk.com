/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkplaceConfigRepository.
 */
public interface WorkplaceConfigRepository {

	/**
	 * Find all by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<WorkplaceConfig> findAllByCompanyId(String companyId);
	
	/**
	 * Find lastest by company id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findLastestByCompanyId(String companyId);
}
