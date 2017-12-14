/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.access.person.SyPersonAdapter;
import nts.uk.ctx.bs.employee.dom.access.person.dto.PersonImport;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeMail;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
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
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonName;

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

	/** The person repository. */
	@Inject
	private PersonRepository personRepository;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Inject
	private AffCompanyHistRepository affComHistRepo;

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
	 * @see nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#getConcurrentEmployee(
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#findByEmpId(java.lang.
	 * String)
	 */
	@Override
	public EmployeeBasicInfoExport findBySId(String sId) {
		// Employee Opt
		Optional<Employee> empOpt = this.employeeRepository.getBySid(sId);
		if (!empOpt.isPresent()) {
			return null;
		}
		// Get Employee
		Employee emp = empOpt.get();
		// Person Opt
		Optional<Person> personOpt = this.personRepository.getByPersonId(emp.getPId());
		if (!personOpt.isPresent()) {
			return null;
		}
		// Get Person
		Person person = personOpt.get();
		String pname = person.getPersonNameGroup().getPersonName().getFullName().v();
		EmployeeMail comMailAddr = emp.getCompanyMail();

		EmployeeBasicInfoExport empBasicInfo = EmployeeBasicInfoExport.builder().pId(person.getPersonId())
				.employeeId(emp.getSId() == null ? null : emp.getSId()).pName((pname == null ? null : pname))
				.gender(person.getGender().value).birthDay(person.getBirthDate() == null ? null : person.getBirthDate())
				.pMailAddr(null).employeeCode(emp.getSCd() == null ? null : emp.getSCd().v())
				.entryDate(emp.getListEntryJobHist().get(0).getJoinDate())
				.retiredDate(emp.getListEntryJobHist().get(0).getRetirementDate())
				.companyMailAddr(comMailAddr == null ? null : new MailAddress(emp.getCompanyMail().v())).build();

		return empBasicInfo;
	}

	/*
	 * (non-Javadoc) req 126
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#findBySIds(java.util.
	 * List)
	 */
	@Override
	public List<EmployeeBasicInfoExport> findBySIds(List<String> sIds) {

		EmployeeBasicInfoExport result = null;

		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);

		List<EmployeeDataMngInfo> emps = this.empDataMngRepo.findByListEmployeeId(sIds);

		List<String> pIds = emps.stream().map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList());

		List<Person> persons = this.personRepository.getPersonByPersonIds(pIds);

		Map<String, Person> mapPersons = persons.stream()
				.collect(Collectors.toMap(Person::getPersonId, Function.identity()));

		return emps.stream().map(employee -> {

			// Get Person
			Person person = mapPersons.get(employee.getPersonId());
			
			if (person != null) {
				result.setGender(person.getGender().value);
				result.setPName(person.getPersonNameGroup().getBusinessName() == null ? null
						: person.getPersonNameGroup().getBusinessName().v());
				result.setBirthDay(person.getBirthDate());
			}

			AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(employee.getEmployeeId());

			AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(employee.getEmployeeId());

			AffCompanyHistItem affComHistItem = new AffCompanyHistItem();

			if (affComHistByEmp.items() != null) {

				List<AffCompanyHistItem> filter = affComHistByEmp.getLstAffCompanyHistoryItem().stream().filter(m -> {
					return m.end().beforeOrEquals(systemDate) && m.start().afterOrEquals(systemDate);
				}).collect(Collectors.toList());

				if (!filter.isEmpty()) {
					affComHistItem = filter.get(0);
					result.setEntryDate(affComHistItem.getDatePeriod().start());
					result.setRetiredDate(affComHistItem.getDatePeriod().end());
				}
			}

			result.setPId(employee.getPersonId());
			result.setCompanyMailAddr(null);
			result.setEmployeeCode(employee.getEmployeeCode() == null ? null : employee.getEmployeeCode().v());
			result.setEmployeeId(employee.getEmployeeId());
			result.setPMailAddr(null);

			return result;
		}).collect(Collectors.toList());

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
