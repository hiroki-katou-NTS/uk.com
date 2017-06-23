/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employee.search;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.person.PersonDto;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistory;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.company.organization.employment.history.EmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.basic.dom.person.PersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeSearchFinder.
 */
@Stateless
public class EmployeeSearchFinder {
	
	/** The repository person. */
	@Inject
	private PersonRepository repositoryPerson;
	
	/** The repository employee. */
	@Inject
	private EmployeeRepository repositoryEmployee;
	
	/** The repository employment history. */
	@Inject
	private EmploymentHistoryRepository repositoryEmploymentHistory;
	
	/** The repository classification history. */
	@Inject
	private ClassificationHistoryRepository repositoryClassificationHistory;
	
	public List<PersonDto> searchModeEmployee(EmployeeSearchDto input){
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// find by employment
		
		List<EmploymentHistory> employmentHistory = this.repositoryEmploymentHistory
				.searchEmployee(input.getBaseDate(), input.getEmploymentCodes());
		
		// find by classification
		List<ClassificationHistory> classificationHistorys = this.repositoryClassificationHistory
				.searchClassification(
						employmentHistory.stream().map(employment -> employment.getEmployeeId().v())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getClassificationCodes());
		
		
		// to employees
		List<Employee> employees = this.repositoryEmployee.getListPersonByListEmployeeId(companyId,
				classificationHistorys.stream()
						.map(classification -> classification.getEmployeeId().v())
						.collect(Collectors.toList()));
		
		// to person info
		return this.repositoryPerson.getPersonByPersonId(
				employees.stream().map(employee -> employee.getPId()).collect(Collectors.toList()))
				.stream().map(person -> {
					PersonDto dto = new PersonDto();
					person.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());
	}
}
