/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employee.employeeindesignated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
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
	
//	@Inject
//	private StatusOfEmploymentPub employmentStatusPub;
	
	/**
	 * Search emp by workplace list.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<EmployeeSearchOutput> searchEmpByWorkplaceList(SearchEmpInput input) {
	
		// CompanyId
		String companyId = AppContexts.user().companyId();

		// List Output
		List<EmployeeSearchOutput> outputData = new ArrayList<>();
		
		// EmployeeInDesignatedDto
		List<EmployeeInDesignatedDto> empListInDesignatedTotal = new ArrayList<>();
		// ForEach Workplace Id (Input), get Employees In Designated
		input.getWorkplaceIdList().stream().forEach(wp-> {
			List<EmployeeInDesignatedDto> empListInDesignated = this.getEmpInDesignated(wp, input.getReferenceDate(), input.getEmpStatus());
			empListInDesignatedTotal.addAll(empListInDesignated);
		});
		empListInDesignatedTotal.stream().distinct();
		
		// Employee Id List from Employees In Designated
		List<String> empIdList = empListInDesignatedTotal.stream().map(EmployeeInDesignatedDto::getEmployeeId)
				.collect(Collectors.toList());
		// Get All AffWorkplaceHistory
		List<AffWorkplaceHistory> affWorkplaceHistList = this.affWorkplaceHistoryRepo
				.searchWorkplaceOfCompanyId(empIdList, input.getReferenceDate());
		
		// Get Workplace Id list from All AffWorkplaceHistory acquired above
		List<String> workplaceList = affWorkplaceHistList.stream().map(a-> {
			return a.getWorkplaceId().v();
		}).collect(Collectors.toList());
		
		// List WorkplaceInfo
		List<WorkplaceInfo> workplaceInfoList = new ArrayList<>();
		// ForEach Workplace Id List: Get WorkplaceInfo By Workplace Id
		workplaceList.stream().forEach(wkpId -> {
			// find workplace Info by workplace Id
			Optional<WorkplaceInfo> workplaceInfoOpt = this.workplaceInfoRepo.findByWkpId(wkpId, input.getReferenceDate());
			if (workplaceInfoOpt.isPresent()) {
				workplaceInfoList.add(workplaceInfoOpt.get());
			}
		});
		
		// Get All Employee Domain from Employee Id List Above 
		List<Employee> employeeList = this.empRepo.findByListEmployeeId(companyId, empIdList);
		
		// Get PersonIds from Employee List acquired above
		List<String> personIds = employeeList.stream().map(Employee::getPId)
				.collect(Collectors.toList());
		// Get All Person Domain from PersonIds above
		List<Person> personList = this.personRepo.getPersonByPersonIds(personIds);
		
		// Add Employee Data 
		employeeList.stream().forEach(employee -> {
			// Get Person by personId
			Person person = personList.stream().filter(p -> {
				return employee.getPId().equals(p.getPersonId());
			}).findFirst().orElse(null);
			
			// Get AffWorkplaceHistory
			AffWorkplaceHistory affWkpHist = affWorkplaceHistList.stream().filter(aff -> {
				return employee.getSId().equals(aff.getEmployeeId());
			}).findFirst().orElse(null);
			
			// Get WorkplaceInfo
			WorkplaceInfo wkpInfo = workplaceInfoList.stream().filter(wkp -> {
				return affWkpHist.getWorkplaceId().v() == wkp.getWorkplaceId();
			}).findFirst().orElse(null);
			
			// Employee Data
			EmployeeSearchOutput empData = EmployeeSearchOutput.builder()
					.employeeId(employee.getSId())
					.employeeCode((employee.getSCd() == null) ? null : employee.getSCd().v())
					.employeeName((person.getPersonNameGroup().getPersonName() == null)
									? null : person.getPersonNameGroup().getPersonName().v())
					.workplaceId(affWkpHist.getWorkplaceId().v())
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
	public List<EmployeeInDesignatedDto> getEmpInDesignated(String workplaceId, GeneralDate referenceDate,
			List<Integer> empStatus) {
		List<AffWorkplaceHistory> affWorkplaceHistList = this.affWorkplaceHistoryRepo.getByWorkplaceID(workplaceId,
				referenceDate);
		// check exist data
		if (CollectionUtil.isEmpty(affWorkplaceHistList)) {
			return null;
		}
		// Get List of Employee Id
		List<String> empIdList = affWorkplaceHistList.stream().map(AffWorkplaceHistory::getEmployeeId)
				.collect(Collectors.toList());
		// Output List
		List<EmployeeInDesignatedDto> empsInDesignated = new ArrayList<>();
		empIdList.stream().forEach(empId -> {
			// 在職状態を取�
			EmploymentStatusDto employmentStatus = this.getStatusOfEmployment(empId,
					referenceDate);
			//check if null
			if (employmentStatus != null) {
				// Every EmpStatus Acquired from screen. Compare to empStatus
				// Acquired above
				empStatus.stream().forEach(s -> {
					if (employmentStatus.getStatusOfEmployment() == s) {
						// Add to output list
						EmployeeInDesignatedDto empExport = EmployeeInDesignatedDto.builder().employeeId(empId)
								.statusOfEmp(employmentStatus.getStatusOfEmployment()).build();
						empsInDesignated.add(empExport);
					}
				});
			}
		});
		return empsInDesignated;
		
	}
	
	/**
	 * Gets the status of employment.
	 *
	 * @param employeeId the employee id
	 * @param referenceDate the reference date
	 * @return the status of employment
	 */
	public EmploymentStatusDto getStatusOfEmployment(String employeeId, GeneralDate referenceDate) {

		Optional<Employee> empOpt = empRepo.getBySid(employeeId);

		if (!empOpt.isPresent()) {
			return null;
		}

		EmploymentStatusDto statusOfEmploymentExport = new EmploymentStatusDto();
		statusOfEmploymentExport.setEmployeeId(employeeId);
		statusOfEmploymentExport.setRefereneDate(referenceDate);
		
		Employee employee = empOpt.get();

		// Filter ListEntryJobHist: JoinDate <= BaseDate <= RetirementDate
		List<JobEntryHistory> listEntryJobHist = employee.getListEntryJobHist().stream()
				.filter(x -> (x.getJoinDate().beforeOrEquals(referenceDate)
						&& x.getRetirementDate().afterOrEquals(referenceDate)))
				.collect(Collectors.toList());

		if (listEntryJobHist.size() == 0) {
			// Case: Filtered ListEntryJobHist (Condition: JoinDate <= BaseDate <= RetirementDate) is empty
			List<GeneralDate> listJointDate = new ArrayList<>();
			// List Entry Job History
			for (int i = 0; i < employee.getListEntryJobHist().size(); i++) {
				listJointDate.add(listEntryJobHist.get(i).getJoinDate());
			}
			// The First Joining Date
			GeneralDate firtJointDate = Collections.min(listJointDate);

			// Check if baseDate is before First Joining Date
			if (referenceDate.before(firtJointDate)) {
				// StatusOfEmployment = BEFORE_JOINING
				statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.BEFORE_JOINING.value);

			} else {
				// StatusOfEmployment = RETIREMENT
				statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.RETIREMENT.value);

			}
		} else {// Case: Filtered ListEntryJobHist (Condition: JoinDate <= BaseDate <= RetirementDate) is not empty

			// Get TemporaryAbsence By employee ID
			Optional<TemporaryAbsence> temporaryAbsOpt = temporaryAbsenceRepo.getBySidAndReferDate(employeeId, referenceDate);
			if (temporaryAbsOpt.isPresent()) {
				// Domain TemporaryAbsence is Present
				TemporaryAbsence temporaryAbsenceDomain = temporaryAbsOpt.get();
				// set LeaveHolidayType 
				statusOfEmploymentExport.setLeaveHolidayType(temporaryAbsenceDomain.getTempAbsenceType().value);

				// Check if TempAbsenceType = TEMP_LEAVE
				if (temporaryAbsenceDomain.getTempAbsenceType().value == 1) {
					// StatusOfEmployment = LEAVE_OF_ABSENCE
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.LEAVE_OF_ABSENCE.value);
				} else {
					// StatusOfEmployment = HOLIDAY
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.HOLIDAY.value);
				}
			} else {
				// StatusOfEmployment = INCUMBENT
				statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.INCUMBENT.value);
			}
		}
		return statusOfEmploymentExport;
	}
	
}
