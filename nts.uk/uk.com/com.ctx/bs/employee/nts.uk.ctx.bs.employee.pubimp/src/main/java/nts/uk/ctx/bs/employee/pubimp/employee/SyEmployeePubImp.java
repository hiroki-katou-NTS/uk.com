/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.access.person.SyPersonAdapter;
import nts.uk.ctx.bs.employee.dom.access.person.dto.PersonImport;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.pub.employee.ConcurrentEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.JobClassification;
import nts.uk.ctx.bs.employee.pub.employee.MailAddress;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

/**
 * The Class SyEmployeePubImp.
 */
@Stateless
public class SyEmployeePubImp implements SyEmployeePub {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	/** The person repository. */
	@Inject
	private SyPersonAdapter syPersonAdapter;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

	/** The aff job title history repository. */
	@Inject
	private AffJobTitleHistoryRepository affJobTitleHistoryRepository;

	@Inject
	private PersonRepository personRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#
	 * findByWpkIds(java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmployeeExport> findByWpkIds(String companyId, List<String> workplaceIds, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository.searchWorkplaceHistory(baseDate,
				workplaceIds);

		List<String> employeeIds = affWorkplaceHistories.stream().map(AffWorkplaceHistory::getEmployeeId)
				.collect(Collectors.toList());

		List<Employee> employeeList = employeeRepository.findByListEmployeeId(companyId, employeeIds);

		// Return
		return employeeList.stream()
				.map(item -> EmployeeExport.builder().companyId(item.getCompanyId()).pId(item.getPId())
						.sId(item.getSId()).sCd(item.getSCd().v()).sMail(item.getCompanyMail().v())
						.retirementDate(item.getListEntryJobHist().get(0).getRetirementDate())
						.joinDate(item.getListEntryJobHist().get(0).getJoinDate()).build())
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#getConcurrentEmployee(
	 * java.lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<ConcurrentEmployeeExport> getConcurrentEmployee(String companyId, String jobId, GeneralDate baseDate) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.affJobTitleHistoryRepository.findByJobId(jobId, baseDate);

		List<String> employeeIds = affJobTitleHistories.stream().map(AffJobTitleHistory::getEmployeeId)
				.collect(Collectors.toList());

		List<Employee> employeeList = this.employeeRepository.findByListEmployeeId(companyId, employeeIds);

		List<String> personIds = employeeList.stream().map(Employee::getPId).collect(Collectors.toList());

		List<PersonImport> persons = this.syPersonAdapter.findByPersonIds(personIds);

		Map<String, String> personNameMap = persons.stream()
				.collect(Collectors.toMap(PersonImport::getPersonId, PersonImport::getPersonName));

		// Return
		// TODO: Du san Q&A for jobCls #
		return employeeList.stream()
				.map(item -> ConcurrentEmployeeExport.builder().employeeId(item.getSId()).employeeCd(item.getSCd().v())
						.personName(personNameMap.get(item.getPId())).jobId(jobId).jobCls(JobClassification.Principal)
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeBasicInfoExport findByEmpId(String empId) {
		// Employee Tbl
		Employee emp = this.employeeRepository.getBySid(empId).get();
		// Person Tbl
		Person person = this.personRepository.getByPersonId(emp.getPId()).get();
		
		EmployeeBasicInfoExport empBasicInfo = EmployeeBasicInfoExport.builder()
				.pId(emp.getPId())
				.pName(person.getPersonNameGroup().getPersonName().v())
				.companyMailAddr(new MailAddress(emp.getCompanyMail().v()))
				.birthDay(person.getBirthDate())
				.pMailAddr(new MailAddress(person.getMailAddress().v()))
				.empId(emp.getSId())
				.empCode(emp.getSCd().v())
				.entryDate(emp.getListEntryJobHist().get(0).getJoinDate())
				.retiredDate(emp.getListEntryJobHist().get(0).getRetirementDate())
				.build();
			
		return empBasicInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#findByListId(java.lang.
	 * String, java.util.List)
	 */
	// @Override
	// public List<EmployeeExport> findByListId(String companyId, List<String>
	// empIdList) {
	// List<Employee> employeeList =
	// employeeRepository.findByListEmployeeId(companyId, empIdList);
	// // Return
	// return employeeList.stream()
	// .map(item ->
	// EmployeeExport.builder().companyId(item.getCompanyId()).pId(item.getPId())
	// .sId(item.getSId()).sCd(item.getSCd().v()).sMail(item.getCompanyMail().v())
	// .retirementDate(item.getListEntryJobHist().get(0).getRetirementDate())
	// .joinDate(item.getListEntryJobHist().get(0).getJoinDate()).build())
	// .collect(Collectors.toList());
	// }

}
