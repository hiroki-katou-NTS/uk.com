/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.workplace.dto.WorkplaceFindDto;
import nts.uk.ctx.basic.app.find.company.organization.workplace.dto.WorkplaceInDto;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkplaceFinder.
 */
@Stateless
public class WorkplaceFinder {
	
	/** The repository. */
	@Inject 
	private WorkplaceRepository repository;
	
	
	/**
	 * Find all.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the list
	 */
	public List<WorkplaceFindDto> findAll(WorkplaceInDto inDto){

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// to domain
		return this.repository.findAll(companyId, inDto.getDate(), inDto.getFormat()).stream()
			.map(domain -> {
				WorkplaceFindDto dto = new WorkplaceFindDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
	}

}
