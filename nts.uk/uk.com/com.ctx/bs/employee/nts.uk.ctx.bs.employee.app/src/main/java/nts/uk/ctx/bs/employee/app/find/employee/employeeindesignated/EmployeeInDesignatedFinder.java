/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employee.employeeindesignated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmployeeInDesignatedFinder.
 */
@Stateless
public class EmployeeInDesignatedFinder {

	/** The aff workplace history repo. */
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepo;

	/** The emp repo. */
	@Inject
	private EmployeeRepository empRepo;

	/** The temporary absence repo. */
	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepo;

	/** The workplace info repo. */
	@Inject
	private WorkplaceInfoRepository workplaceInfoRepo;

	/** The person repo. */
	@Inject
	private PersonRepository personRepo;

	/**
	 * Search emp by workplace list.
	 *
	 * @param input
	 *            the input
	 * @return the list
	 */
	public List<EmployeeSearchOutput> searchEmpByWorkplaceList(SearchEmpInput input) {
		// Check empty for List Employee Status acquired from screen
		if (CollectionUtil.isEmpty(input.getEmpStatus())) {
			return Collections.emptyList();
		}
		// List Output
		List<EmployeeSearchOutput> outputData = new ArrayList<>();

		// Workplace Id List (Input), get Employees In Designated (Employee with Status)
		List<EmployeeInDesignatedDto> empListInDesignated = this.getEmpInDesignated(input.getWorkplaceIdList(),
				input.getReferenceDate(), input.getEmpStatus());
		
		// Check isEmpty of Employee In Designated List
		if (CollectionUtil.isEmpty(empListInDesignated)) {
			return Collections.emptyList();
		}
		
		// Employee Id List from Employees In Designated
		List<String> empIdList = empListInDesignated.stream().map(EmployeeInDesignatedDto::getEmployeeId)
				.collect(Collectors.toList());
		// Get All AffWorkplaceHistory
		List<AffWorkplaceHistory> affWorkplaceHistList = this.affWorkplaceHistoryRepo
				.searchWorkplaceOfCompanyId(empIdList, input.getReferenceDate());
		if (CollectionUtil.isEmpty(affWorkplaceHistList)) {
			return Collections.emptyList();
		}
		// Get Workplace Id list from All AffWorkplaceHistory acquired above
		List<String> workplaceList = affWorkplaceHistList.stream().map(a -> {
			return a.getWorkplaceId().v();
		}).collect(Collectors.toList());

		// List WorkplaceInfo
		List<WorkplaceInfo> workplaceInfoList = this.workplaceInfoRepo.getByWkpIds(workplaceList,
				input.getReferenceDate());

		// Get All Employee Domain from Employee Id List Above
		List<Employee> employeeList = this.empRepo.findByListEmployeeId(AppContexts.user().companyId(), empIdList);

		// Get PersonIds from Employee List acquired above
		List<String> personIds = employeeList.stream().map(Employee::getPId).collect(Collectors.toList());
		// Get All Person Domain from PersonIds above
		List<Person> personList = this.personRepo.getPersonByPersonIds(personIds);

		// Add Employee Data
		employeeList.stream().forEach(employee -> {
			// Get Person by personId
			Person person = new Person();
			if (!CollectionUtil.isEmpty(personList)) {
				person = personList.stream().filter(p -> {
					return employee.getPId().equals(p.getPersonId());
				}).findFirst().orElse(null);
			}

			// Get AffWorkplaceHistory
			AffWorkplaceHistory affWkpHist = affWorkplaceHistList.stream().filter(aff -> {
				return employee.getSId().equals(aff.getEmployeeId());
			}).findFirst().orElse(null);

			// Get WorkplaceInfo
			WorkplaceInfo wkpInfo = workplaceInfoList.stream().filter(wkp -> {
				return affWkpHist.getWorkplaceId().v() == wkp.getWorkplaceId();
			}).findFirst().orElse(null);

			// Employee Data
			EmployeeSearchOutput empData = EmployeeSearchOutput.builder().employeeId(employee.getSId())
					.employeeCode((employee.getSCd() == null) ? null : employee.getSCd().v())
					.employeeName((person == null || person.getPersonNameGroup() == null
							|| (person.getPersonNameGroup().getPersonName() == null)) ? null
									: person.getPersonNameGroup().getPersonName().v())
					.workplaceId(affWkpHist == null || (affWkpHist.getWorkplaceId() == null) ? null
							: affWkpHist.getWorkplaceId().v())
					.workplaceCode((wkpInfo == null) || (wkpInfo.getWorkplaceCode() == null) ? null
							: wkpInfo.getWorkplaceCode().v())
					.workplaceName((wkpInfo == null) || (wkpInfo.getWorkplaceName() == null) ? null
							: wkpInfo.getWorkplaceName().v())
					.build();

			// Add Employee to output Data
			outputData.add(empData);
		});

		return outputData;
	}

	/**
	 * Gets the emp in designated.
	 *
	 * @param workplaceId the workplace id
	 * @param referenceDate the reference date
	 * @param empStatus the emp status
	 * @return the emp in designated
	 */
	public List<EmployeeInDesignatedDto> getEmpInDesignated(List<String> workplaceIds, GeneralDate referenceDate,
			List<Integer> empStatus) {
		List<AffWorkplaceHistory> affWorkplaceHistList = this.affWorkplaceHistoryRepo.getByWorkplaceIDs(workplaceIds,
				referenceDate);
		// check exist data
		if (CollectionUtil.isEmpty(affWorkplaceHistList)) {
			Collections.emptyList();
		}
		// Get List of Employee Id
		List<String> empIdList = affWorkplaceHistList.stream().map(AffWorkplaceHistory::getEmployeeId)
				.collect(Collectors.toList());
		// Output List
		List<EmploymentStatusDto> employmentStatus =  this.getStatusOfEmployments(empIdList,
				referenceDate);
		
		Map<String, EmploymentStatusDto> empStatusMap = employmentStatus.stream()
				.collect(Collectors.toMap(EmploymentStatusDto::getEmployeeId, Function.identity()));
		
		return empIdList.stream().map(empId -> {
			// Initialize EmployeeInDesignatedDto
			EmployeeInDesignatedDto empInDes = EmployeeInDesignatedDto.builder().build();
			EmploymentStatusDto empStatusOfMap = empStatusMap.get(empId);
			//check if null
			if (empStatus != null) {
				// Every EmpStatus Acquired from screen. Compare to empStatus Acquired above
				empStatus.stream().forEach(s -> {
					if (empStatusOfMap.getStatusOfEmployment() == s) {
						// Set EmployeeInDesignatedDto
						empInDes.setEmployeeId(empId);
						empInDes.setStatusOfEmp(empStatusOfMap.getStatusOfEmployment());
					}
				});
			}
			return empInDes;
		}).collect(Collectors.toList());
		
	}

	/**
	 * Gets the status of employment.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param referenceDate
	 *            the reference date
	 * @return the status of employment
	 */
	public List<EmploymentStatusDto> getStatusOfEmployments(List<String> employeeIds, GeneralDate referenceDate) {
		List<Employee> employees = empRepo.getByListEmployeeId(employeeIds);
		if (CollectionUtil.isEmpty(employees)) {
			return Collections.emptyList();
		}

		// Convert to dto
		return employees.stream().map(employee -> {
			EmploymentStatusDto statusOfEmploymentExport = new EmploymentStatusDto();
			statusOfEmploymentExport.setEmployeeId(employee.getSId());
			statusOfEmploymentExport.setRefereneDate(referenceDate);

			// List Entry Job History
			List<JobEntryHistory> listEntryJobHist = employee.getListEntryJobHist();

			// TODO: Solution for a Temporary Case (DB is unavailable: List of
			// JobEntryHistory is empty)
			if (CollectionUtil.isEmpty(listEntryJobHist)) {
				statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.BEFORE_JOINING.value);
				return statusOfEmploymentExport;
				// throw new RuntimeException("Data is invalid. Please check
				// again.")
			}
			// End of Solution

			// Filter ListEntryJobHist: JoinDate <= BaseDate <= RetirementDate
			List<JobEntryHistory> filteredEntryJobHist = listEntryJobHist.stream()
					.filter(x -> (x.getJoinDate().beforeOrEquals(referenceDate)
							&& x.getRetirementDate().afterOrEquals(referenceDate)))
					.collect(Collectors.toList());

			if (filteredEntryJobHist.isEmpty()) {
				// Case: Filtered ListEntryJobHist (Condition: JoinDate <= BaseDate <= RetirementDate) is empty
				
				// JobEntryHistory List (Condition: JoinDate < BaseDate)
				List<JobEntryHistory> jonEntryHistBefore = listEntryJobHist.stream().filter(item -> {
					return item.getJoinDate().before(referenceDate);
				}).collect(Collectors.toList());
				
				if (CollectionUtil.isEmpty(jonEntryHistBefore)) {
					// StatusOfEmployment = BEFORE_JOINING
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.BEFORE_JOINING.value);
				} else {
					// StatusOfEmployment = RETIREMENT
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.RETIREMENT.value);
				}
				
			} else {// Case: Filtered ListEntryJobHist (Condition: JoinDate <=
					// BaseDate <= RetirementDate) is not empty

				// Get TemporaryAbsence By employee ID and BaseDate
				Optional<TempAbsenceHisItem> temporaryAbsOpt = temporaryAbsenceRepo
						.getBySidAndReferDate(employee.getSId(), referenceDate);
				if (temporaryAbsOpt.isPresent()) {
					// Domain TemporaryAbsence is Present
					TempAbsenceHisItem temporaryAbsenceDomain = temporaryAbsOpt.get();
					// set LeaveHolidayType
//					statusOfEmploymentExport.setLeaveHolidayType(temporaryAbsenceDomain.getTempAbsenceType().value);
//
//					// Check if TempAbsenceType = TEMP_LEAVE
//					if (temporaryAbsenceDomain.getTempAbsenceType().value == TempAbsenceType.TEMP_LEAVE.value) {
//						// StatusOfEmployment = LEAVE_OF_ABSENCE
//						statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.LEAVE_OF_ABSENCE.value);
//					} else {
//						// StatusOfEmployment = HOLIDAY
//						statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.HOLIDAY.value);
//					}
				} else {
					// StatusOfEmployment = INCUMBENT 在籍
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.INCUMBENT.value);
				}
			}
			return statusOfEmploymentExport;
		}).collect(Collectors.toList());

	}

}
