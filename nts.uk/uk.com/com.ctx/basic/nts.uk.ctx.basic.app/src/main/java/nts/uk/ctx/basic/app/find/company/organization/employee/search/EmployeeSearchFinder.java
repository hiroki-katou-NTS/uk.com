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
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffiliationClassificationHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffiliationClassificationHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffiliationEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffiliationEmploymentHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffiliationJobTitleHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffiliationJobTitleHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffiliationWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffiliationWorkplaceHistoryRepository;
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
	private AffiliationEmploymentHistoryRepository repositoryEmploymentHistory;
	
	/** The repository classification history. */
	@Inject
	private AffiliationClassificationHistoryRepository repositoryClassificationHistory;
	
	
	/** The repository job title history. */
	@Inject
	private AffiliationJobTitleHistoryRepository repositoryJobTitleHistory;
	
	/** The repository workplace history. */
	@Inject
	private AffiliationWorkplaceHistoryRepository repositoryWorkplaceHistory;
	
	/**
	 * Search mode employee.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<PersonDto> searchModeEmployee(EmployeeSearchDto input){
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// find by employment
		
		List<AffiliationEmploymentHistory> employmentHistory = this.repositoryEmploymentHistory
				.searchEmployee(input.getBaseDate(), input.getEmploymentCodes());
		
		// find by classification
		List<AffiliationClassificationHistory> classificationHistorys = this.repositoryClassificationHistory
				.searchClassification(
						employmentHistory.stream().map(employment -> employment.getEmployeeId().v())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getClassificationCodes());
		
		// find by job title
		List<AffiliationJobTitleHistory> jobTitleHistory = this.repositoryJobTitleHistory
				.searchJobTitleHistory(
						classificationHistorys.stream()
								.map(classification -> classification.getEmployeeId().v())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getJobTitleCodes());
		
		// find by work place
		List<AffiliationWorkplaceHistory> workplaceHistory = this.repositoryWorkplaceHistory
				.searchWorkplaceHistory(
						jobTitleHistory.stream().map(jobtitle -> jobtitle.getEmployeeId().v())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getWorkplaceCodes());
		// to employees
		List<Employee> employees = this.repositoryEmployee.getListPersonByListEmployeeId(companyId,
				workplaceHistory.stream().map(workplace -> workplace.getEmployeeId().v())
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
