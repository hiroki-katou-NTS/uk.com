/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.repository.systemresource;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.infra.i18n.resource.data.CismtI18NResourceCus;

/**
 * The Interface SystemResourceQueryRepository.
 */
public interface SystemResourceQueryRepository {
	
	/**
	 * Find by list resource id.
	 *
	 * @return the list
	 */
	List<SystemResourceData> findListResource(String companyId);
	
	
	/**
	 * Find by resource id.
	 *
	 * @param resourceId the resource id
	 * @return the optional
	 */
	Optional<CismtI18NResourceCus> findByResourceId(String companyId, String resourceId);
	
	
	/**
	 * Update.
	 *
	 * @param systemResource the system resource
	 */
	void update(CismtI18NResourceCus entity);
}
