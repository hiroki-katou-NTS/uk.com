/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.repository.systemresource;

import java.util.List;
import java.util.Optional;

import nts.uk.screen.com.app.systemresource.dto.SystemResourceDto;

/**
 * The Interface SystemResourceQueryRepository.
 */
public interface SystemResourceQueryRepository {
	
	/**
	 * Find by list resource id.
	 *
	 * @param listResourceId the list resource id
	 * @return the list
	 */
	List<SystemResourceDto> findByListResourceId(String listResourceId);
	
	
	/**
	 * Find by resource id.
	 *
	 * @param listResourceId the list resource id
	 * @return the optional
	 */
	Optional<SystemResourceDto> findByResourceId(String resourceId);
	
	/**
	 * Add.
	 *
	 * @param listData the list data
	 */
	void add(List<SystemResourceDto> listData);
	
	/**
	 * Update.
	 *
	 * @param listData the list data
	 */
	void update(List<SystemResourceDto> listData);
}
