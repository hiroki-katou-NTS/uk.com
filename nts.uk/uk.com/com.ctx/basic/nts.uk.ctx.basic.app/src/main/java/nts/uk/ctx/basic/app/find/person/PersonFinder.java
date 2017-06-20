/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.person.PersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class PersonFinder.
 */
@Stateless
public class PersonFinder {
	
	/** The repository employee. */
	@Inject
	private EmployeeRepository repositoryEmployee;
	
	
	/** The repository. */
	@Inject 
	private PersonRepository repository;
	
	/**
	 * Gets the all person.
	 *
	 * @return the all person
	 */
	public List<PersonDto> getAllPerson(){
		
		// get login user 
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// data to dto
		return this.repository
				.getPersonByPersonId(this.repositoryEmployee.getAllEmployee(companyId).stream()
						.map(item -> item.getPId()).collect(Collectors.toList()))
				.stream().map(person -> {
					PersonDto dto = new PersonDto();
					person.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());
	}

}
