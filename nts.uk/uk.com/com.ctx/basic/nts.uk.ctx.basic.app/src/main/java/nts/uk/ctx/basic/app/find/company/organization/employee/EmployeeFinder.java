/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.employee.dto.EmployeeFindDto;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeFinder.
 */

@Stateless
public class EmployeeFinder {
	
	/** The repository. */
	@Inject
	private EmployeeRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<EmployeeFindDto> findAll(){
		
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all management category
		List<Employee> employees = this.repository.findAll(companyId);

		// to domain
		return employees.stream().map(employee -> {
			EmployeeFindDto dto = new EmployeeFindDto();
			employee.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
