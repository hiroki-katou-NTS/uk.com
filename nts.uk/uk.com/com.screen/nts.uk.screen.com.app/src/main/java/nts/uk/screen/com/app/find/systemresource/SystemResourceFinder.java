/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.find.systemresource;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.com.app.repository.systemresource.SystemResourceData;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository;
import nts.uk.screen.com.app.systemresource.dto.SystemResourceDto;

/**
 * The Class SystemResourceFinder.
 */
@Stateless
public class SystemResourceFinder {
	
	/** The repository. */
	@Inject
	private SystemResourceQueryRepository repository;
	
	
	/**
	 * Find list.
	 *
	 * @param listResourceId the list resource id
	 * @return the list
	 */
	public List<SystemResourceDto> findList(){
		List<SystemResourceData> result = this.repository.findListResource();
		
		List<SystemResourceDto> listDto = result.stream().map(e -> new SystemResourceDto(e.getResourceId(), e.getResourceContent())).collect(Collectors.toList());
		
		return listDto;
	}
}
