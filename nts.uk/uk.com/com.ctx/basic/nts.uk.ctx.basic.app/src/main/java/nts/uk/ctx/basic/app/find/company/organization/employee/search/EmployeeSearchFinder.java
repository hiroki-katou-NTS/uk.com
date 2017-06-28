/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employee.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
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
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchy;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
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
	
	/** The repository work place. */
	@Inject
	private WorkplaceRepository repositoryWorkplace;
	
	/** The repository employment history. */
	@Inject
	private AffiliationEmploymentHistoryRepository repositoryEmploymentHistory;
	
	/** The repository classification history. */
	@Inject
	private AffiliationClassificationHistoryRepository repositoryClassificationHistory;
	
	
	/** The repository job title history. */
	@Inject
	private AffiliationJobTitleHistoryRepository repositoryJobTitleHistory;
	
	/** The repository work place history. */
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
	
	
	/**
	 * Search of workplace.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<PersonDto> searchOfWorkplace(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get employee id
		String employeeId = loginUserContext.employeeId();
		
		
		// get company id
		String companyId = loginUserContext.companyId();

		List<AffiliationWorkplaceHistory> workplaceHistory = this.repositoryWorkplaceHistory
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);
		
		// check exist data
		if(CollectionUtil.isEmpty(workplaceHistory)){
			throw new BusinessException("Msg_177");
		}

		// get employee list
		List<Employee> employees = this.repositoryEmployee.getListPersonByListEmployeeId(companyId,
				workplaceHistory.stream().map(workplace -> workplace.getEmployeeId().v())
						.collect(Collectors.toList()));
		
		// check exist data
		if(CollectionUtil.isEmpty(employees)){
			throw new BusinessException("Msg_176");
		}
		
		// to person info
		List<PersonDto> persons=  this.repositoryPerson.getPersonByPersonId(
				employees.stream().map(employee -> employee.getPId()).collect(Collectors.toList()))
				.stream().map(person -> {
					PersonDto dto = new PersonDto();
					person.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());
		
		// check exist data
		if(CollectionUtil.isEmpty(persons)){
			throw new BusinessException("Msg_176");
		}
		
		return persons;
	}

	/**
	 * Search of work place child.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<PersonDto> searchWorkplaceChild(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get employee id
		String employeeId = loginUserContext.employeeId();

		// get company id
		String companyId = loginUserContext.companyId();

		// get data work place history
		List<AffiliationWorkplaceHistory> workplaceHistory = this.repositoryWorkplaceHistory
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// check exist data
		if (CollectionUtil.isEmpty(workplaceHistory)) {
			throw new BusinessException("Msg_177");
		}

		// get data child
		List<WorkPlaceHierarchy> workPlaceHierarchies = new ArrayList<>();
		workplaceHistory.forEach(work -> {
			workPlaceHierarchies.addAll(this.repositoryWorkplace.findAllHierarchyChild(companyId,
					work.getWorkplaceId().v()));
		});

		// get data work place history
		List<AffiliationWorkplaceHistory> workplaceHistoryChild = this.repositoryWorkplaceHistory
				.searchWorkplaceHistory(baseDate, workPlaceHierarchies.stream()
						.map(work -> work.getWorkplaceId().v()).collect(Collectors.toList()));

		// check exist data
		if (CollectionUtil.isEmpty(workplaceHistoryChild)) {
			throw new BusinessException("Msg_176");
		}

		// get employee list
		List<Employee> employees = this.repositoryEmployee.getListPersonByListEmployeeId(companyId,
				workplaceHistoryChild.stream().map(workplace -> workplace.getEmployeeId().v())
						.collect(Collectors.toList()));

		// check exist data
		if (CollectionUtil.isEmpty(employees)) {
			throw new BusinessException("Msg_176");
		}

		// to person info
		List<PersonDto> persons = this.repositoryPerson.getPersonByPersonId(
				employees.stream().map(employee -> employee.getPId()).collect(Collectors.toList()))
				.stream().map(person -> {
					PersonDto dto = new PersonDto();
					person.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());

		// check exist data
		if (CollectionUtil.isEmpty(persons)) {
			throw new BusinessException("Msg_176");
		}

		return persons;
	}
	
	/**
	 * Search workplace of employee.
	 *
	 * @return the list
	 */
	public List<String> searchWorkplaceOfEmployee(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get employee id
		String employeeId = loginUserContext.employeeId();

		// get data work place history
		List<AffiliationWorkplaceHistory> workplaceHistory = this.repositoryWorkplaceHistory
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// return data
		return workplaceHistory.stream().map(workplace -> workplace.getWorkplaceId().v())
				.collect(Collectors.toList());
	}
}
