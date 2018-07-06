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
	 * @param companyId the company id
	 * @return the list
	 */
	List<SystemResourceData> findListResourceCus(String companyId, String languageId);
	
	/**
	 * Find list resource.
	 *
	 * @return the list
	 */
	List<SystemResourceData> findListResource();

	/**
	 * Find by resource id.
	 *
	 * @param companyId the company id
	 * @param resourceId the resource id
	 * @return the optional
	 */
	Optional<CismtI18NResourceCus> findByResourceId(String companyId, String resourceId);
	
	
	/**
	 * Update.
	 *
	 * @param entity the entity
	 */
	void update(CismtI18NResourceCus entity);
}
