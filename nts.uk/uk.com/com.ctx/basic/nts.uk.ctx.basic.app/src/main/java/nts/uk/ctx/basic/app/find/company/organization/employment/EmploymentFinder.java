/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.employment.dto.EmploymentFindDto;
import nts.uk.ctx.basic.dom.company.organization.employment.Employment;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentFinder.
 */
@Stateless
public class EmploymentFinder {

	/** The repository. */
	@Inject
	private EmploymentRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<EmploymentFindDto> findAll() {
		
		// Get Login User Info
		LoginUserContext loginUserContext = AppContexts.user();
		
		// Get Company Id
		String companyId = loginUserContext.companyId();
		
		// Get All Employment
		List<Employment> empList = this.repository.findAll(companyId);
		
		// Save to Memento
		return empList.stream().map(empoyment -> {
			EmploymentFindDto dto = new EmploymentFindDto();
			empoyment.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Gets the by id.
	 *
	 * @param employmentCode the employment code
	 * @return the by id
	 */
	public EmploymentFindDto getById(String employmentCode) {
		
		String companyId = AppContexts.user().companyId();
		EmploymentFindDto outputData = new EmploymentFindDto();
		Optional<Employment> employment = this.repository.findEmployment(companyId, employmentCode);
		
		if(employment.isPresent()) {
			employment.get().saveToMemento(outputData);
			return outputData;
		}
		return null;
	}
}
