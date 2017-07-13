/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.query.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffClassHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffClassHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchy;
import nts.uk.ctx.basic.dom.company.organization.workplace.Workplace;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
import nts.uk.ctx.basic.dom.person.Person;
import nts.uk.ctx.basic.dom.person.PersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeQueryProcessor.
 */
@Stateless
public class EmployeeSearchQueryProcessor {
	/** The person repository. */
	@Inject
	private PersonRepository personRepository;
	
	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;
	
	/** The workplace repository. */
	@Inject
	private WorkplaceRepository workplaceRepository;
	
	/** The employment history repository. */
	@Inject
	private AffEmploymentHistoryRepository employmentHistoryRepository;
	
	/** The classification history repository. */
	@Inject
	private AffClassHistoryRepository classificationHistoryRepository;
	
	
	/** The job title history repository. */
	@Inject
	private AffJobTitleHistoryRepository jobTitleHistoryRepository;
	
	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;
	
	
	/**
	 * To employee.
	 *
	 * @param baseDate the base date
	 * @param employeeIds the employee ids
	 * @param companyId the company id
	 * @return the list
	 */

	public List<EmployeeSearchData> toEmployee(GeneralDate baseDate, List<String> employeeIds,
			String companyId) {

		// get employee list
		List<Employee> employees = this.employeeRepository.getListPersonByListEmployeeId(companyId,
				employeeIds);

		// get work place history by employee
		List<AffWorkplaceHistory> workplaceHistory = this.workplaceHistoryRepository
				.searchWorkplaceOfCompanyId(employeeIds, baseDate);

		// check exist data work place
		if (CollectionUtil.isEmpty(workplaceHistory)) {
			throw new BusinessException("Msg_177");
		}

		// get all work place of company
		List<Workplace> workplaces = this.workplaceRepository.getAllWorkplaceOfCompany(companyId,
				baseDate);

		// to map work place
		Map<String, Workplace> workplaceMap = workplaces.stream()
				.collect(Collectors.toMap((workplace) -> {
					return workplace.getWorkplaceId().v();
				}, Function.identity()));

		// to map work place history
		Map<String, AffWorkplaceHistory> workplaceHistoryMap = workplaceHistory.stream()
				.collect(Collectors.toMap((workplace) -> {
					return workplace.getEmployeeId();
				}, Function.identity()));

		// get person name
		List<Person> persons = this.personRepository.getPersonByPersonId(
				employees.stream().map(employee -> employee.getPId()).collect(Collectors.toList()));

		// to map person (person id)
		Map<String, Person> personMap = persons.stream().collect(Collectors.toMap((person) -> {
			return person.getPersonId().v();
		}, Function.identity()));

		List<EmployeeSearchData> employeeSearchData = new ArrayList<>();

		employees.forEach(employee -> {
			// check exist data
			if (workplaceHistoryMap.containsKey(employee.getSId())
					&& personMap.containsKey(employee.getPId()) && workplaceMap.containsKey(
							workplaceHistoryMap.get(employee.getSId()).getWorkplaceId().v())) {

				// add to dto
				EmployeeSearchData dto = new EmployeeSearchData();
				dto.setEmployeeId(employee.getSId());
				dto.setEmployeeCode(employee.getSCd().v());
				dto.setEmployeeName(personMap.get(employee.getPId()).getPersonName().v());
				dto.setWorkplaceId(
						workplaceHistoryMap.get(employee.getSId()).getWorkplaceId().v());

				dto.setWorkplaceCode(workplaceMap.get(dto.getWorkplaceId()).getWorkplaceCode().v());
				dto.setWorkplaceName(workplaceMap.get(dto.getWorkplaceId()).getWorkplaceName().v());
				employeeSearchData.add(dto);
			}
		});

		// check exist data employee search
		if (CollectionUtil.isEmpty(employeeSearchData)) {
			throw new BusinessException("Msg_176");
		}

		return employeeSearchData;
	}
	
	/**
	 * Search all employee.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<EmployeeSearchData> searchAllEmployee(GeneralDate baseDate){

		//get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		//get company id
		String companyId = loginUserContext.companyId();
				
		// get all employee of company id
		List<Employee> employees = this.employeeRepository.getAllEmployee(companyId);

		return toEmployee(baseDate, employees.stream().map(employee -> employee.getSId())
				.collect(Collectors.toList()), companyId);
	}
	
	
	/**
	 * Search all employee.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<EmployeeSearchData> searchEmployeeByLogin(GeneralDate baseDate){

		//get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		//get company id
		String companyId = loginUserContext.companyId();
		
		// get employee id
		String employeeId = loginUserContext.employeeId();
				
		// add employeeId
		List<String> employeeIds = new ArrayList<>();
		
		employeeIds.add(employeeId);
		
		// get data
		return toEmployee(baseDate, employeeIds, companyId);
	}
	
	/**
	 * Search mode employee.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<EmployeeSearchData> searchModeEmployee(EmployeeSearchQuery input) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// find by employment
		List<AffEmploymentHistory> employmentHistory = this.employmentHistoryRepository
				.searchEmployee(input.getBaseDate(), input.getEmploymentCodes());

		// find by classification
		List<AffClassHistory> classificationHistorys = this.classificationHistoryRepository
				.searchClassification(
						employmentHistory.stream().map(employment -> employment.getEmployeeId())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getClassificationCodes());

		// find by job title
		List<AffJobTitleHistory> jobTitleHistory = this.jobTitleHistoryRepository
				.searchJobTitleHistory(
						classificationHistorys.stream()
								.map(classification -> classification.getEmployeeId())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getJobTitleCodes());
		// find by work place
		List<AffWorkplaceHistory> workplaceHistory = this.workplaceHistoryRepository
				.searchWorkplaceHistory(
						jobTitleHistory.stream().map(jobtitle -> jobtitle.getEmployeeId())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getWorkplaceCodes());
		// to employees
		List<Employee> employees = this.employeeRepository.getListPersonByListEmployeeId(companyId,
				workplaceHistory.stream().map(workplace -> workplace.getEmployeeId())
						.collect(Collectors.toList()));

		// to person info
		return this.toEmployee(input.getBaseDate(),
				employees.stream().map(employee -> employee.getSId()).collect(Collectors.toList()),
				companyId);
	}
	
	
	/**
	 * Search of workplace.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<EmployeeSearchData> searchOfWorkplace(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee id
		String employeeId = loginUserContext.employeeId();
		
		List<AffWorkplaceHistory> workplaceHistory = this.workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);
		
		// check exist data
		if(CollectionUtil.isEmpty(workplaceHistory)){
			throw new BusinessException("Msg_177");
		}

		// return data
		return this.toEmployee(baseDate, workplaceHistory.stream()
				.map(workplace -> workplace.getEmployeeId()).collect(Collectors.toList()),
				companyId);
	}

	/**
	 * Search workplace child.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<EmployeeSearchData> searchWorkplaceChild(GeneralDate baseDate) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee id
		String employeeId = loginUserContext.employeeId();
		
		
		// get data work place history
		List<AffWorkplaceHistory> workplaceHistory = this.workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// check exist data
		if (CollectionUtil.isEmpty(workplaceHistory)) {
			throw new BusinessException("Msg_177");
		}

		// get data child
		List<WorkPlaceHierarchy> workPlaceHierarchies = new ArrayList<>();
		workplaceHistory.forEach(work -> {
			workPlaceHierarchies.addAll(this.workplaceRepository.findAllHierarchyChild(companyId,
					work.getWorkplaceId().v()));
		});
		
		workplaceHistory = this.workplaceHistoryRepository.searchWorkplaceHistory(baseDate,
				workPlaceHierarchies.stream().map(workplace -> workplace.getWorkplaceId().v())
						.collect(Collectors.toList()));
		// return data
		return this.toEmployee(baseDate, workplaceHistory.stream()
				.map(workplace -> workplace.getEmployeeId()).collect(Collectors.toList()),
				companyId);
	}
	
	/**
	 * Search workplace of employee.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<String> searchWorkplaceOfEmployee(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get employee id
		String employeeId = loginUserContext.employeeId();
		
		
		// get data work place history
		List<AffWorkplaceHistory> workplaceHistory = this.workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// return data
		return workplaceHistory.stream().map(workplace -> workplace.getWorkplaceId().v())
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the of selected employee.
	 *
	 * @param input the input
	 * @return the of selected employee
	 */
	public List<EmployeeSearchData> getOfSelectedEmployee(EmployeeSearchListQuery input){		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		
		return this.toEmployee(input.getBaseDate(), input.getEmployeeIds(), companyId);
	}
}
