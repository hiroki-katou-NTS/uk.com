/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.access.person.SyPersonAdapter;
import nts.uk.ctx.bs.employee.dom.access.person.dto.PersonImport;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.pub.employee.ConcurrentEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDataMngInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.JobClassification;
import nts.uk.ctx.bs.employee.pub.employee.MailAddress;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SyEmployeePubImp.
 */
@Stateless
public class SyEmployeePubImp implements SyEmployeePub {

	/** The person repository. */
	@Inject
	private SyPersonAdapter syPersonAdapter;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

	@Inject
	private AffJobTitleHistoryItemRepository jobTitleHistoryItemRepository;

	/** The person repository. */
	@Inject
	private PersonRepository personRepository;

	/** The emp data mng repo. */
	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	/** The aff com hist repo. */
	@Inject
	private AffCompanyHistRepository affComHistRepo;

	@Inject
	private AffWorkplaceHistoryItemRepository affWkpItemRepo;

	@Inject
	private WorkplaceConfigRepository wkpConfigRepo;

	@Inject
	private WorkplaceConfigInfoRepository wkpConfigInfoRepo;

	@Inject
	private SyWorkplacePub syWorkplacePub;

	@Inject
	private EmploymentHistoryItemRepository emptHistItem;

	@Inject
	private EmployeeDataMngInfoRepository sDataMngInfoRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#
	 * findByWpkIds(java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmployeeExport> findByWpkIds(String companyId, List<String> workplaceIds, GeneralDate baseDate) {
		// Query
		// update use AffWorkplaceHistory - get list Aff WorkplaceHistory by list wkpIds
		// and base data
		List<AffWorkplaceHistory> affWorkplaceHistories = this.workplaceHistoryRepository
				.getWorkplaceHistoryByWkpIdsAndDate(baseDate, workplaceIds);

		List<String> employeeIds = affWorkplaceHistories.stream().map(AffWorkplaceHistory::getEmployeeId)
				.collect(Collectors.toList());

		List<EmployeeDataMngInfo> employeeList = empDataMngRepo.findByListEmployeeId(companyId, employeeIds);
		String cid = AppContexts.user().companyId();
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);

		return employeeList.stream().map(employee -> {

			EmployeeExport result = new EmployeeExport();

			AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(cid, employee.getEmployeeId());

			AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(employee.getEmployeeId());

			AffCompanyHistItem affComHistItem = new AffCompanyHistItem();

			if (affComHistByEmp.items() != null) {

				List<AffCompanyHistItem> filter = affComHistByEmp.getLstAffCompanyHistoryItem().stream().filter(m -> {
					return m.end().afterOrEquals(systemDate) && m.start().beforeOrEquals(systemDate);
				}).collect(Collectors.toList());

				if (!filter.isEmpty()) {
					affComHistItem = filter.get(0);
					result.setJoinDate(affComHistItem.start());
					result.setRetirementDate(affComHistItem.end());
				}
			}

			result.setCompanyId(employee.getCompanyId());
			result.setSCd(employee.getEmployeeCode() == null ? null : employee.getEmployeeCode().v());
			result.setSId(employee.getEmployeeId());
			result.setPId(employee.getPersonId());
			result.setSMail("");// bo mail roi.

			return result;
		}).collect(Collectors.toList());
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
		List<AffJobTitleHistoryItem> affJobTitleHistories = this.jobTitleHistoryItemRepository
				.getByJobIdAndReferDate(jobId, baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(affJobTitleHistories)) {
			return Collections.emptyList();
		}

		List<String> employeeIds = affJobTitleHistories.stream().map(AffJobTitleHistoryItem::getEmployeeId)
				.collect(Collectors.toList());

		List<EmployeeDataMngInfo> employeeList = empDataMngRepo.findByListEmployeeId(companyId, employeeIds);

		List<String> personIds = employeeList.stream().map(EmployeeDataMngInfo::getPersonId)
				.collect(Collectors.toList());

		List<PersonImport> persons = this.syPersonAdapter.findByPersonIds(personIds);

		Map<String, String> personNameMap = persons.stream()
				.collect(Collectors.toMap(PersonImport::getPersonId, PersonImport::getPersonName));

		// Return
		// TODO: Du san Q&A for jobCls #
		return employeeList.stream()
				.map(item -> ConcurrentEmployeeExport.builder().employeeId(item.getEmployeeId())
						.employeeCd(item.getEmployeeCode().v()).personName(personNameMap.get(item.getPersonId()))
						.jobId(jobId).jobCls(JobClassification.Principal).build())
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

		EmployeeBasicInfoExport result = new EmployeeBasicInfoExport();
		// Employee Opt
		Optional<EmployeeDataMngInfo> empOpt = this.empDataMngRepo.findByEmpId(sId);
		if (!empOpt.isPresent()) {
			return null;
		}
		// Get Employee
		EmployeeDataMngInfo emp = empOpt.get();
		// Person Opt
		Optional<Person> personOpt = this.personRepository.getByPersonId(emp.getPersonId());
		if (!personOpt.isPresent()) {
			return null;
		}
		// Get Person
		Person person = personOpt.get();
		// String pname = person.getPersonNameGroup().getPersonName().getFullName().v();
		// EmployeeMail comMailAddr = emp.getCompanyMail();

		result.setPId(person.getPersonId());
		result.setEmployeeId(emp.getEmployeeId());
		result.setPName(person.getPersonNameGroup().getPersonName().getFullName().v());
		result.setGender(person.getGender().value);
		result.setPMailAddr(new MailAddress(""));
		result.setEmployeeCode(emp.getEmployeeCode().v());
		result.setCompanyMailAddr(new MailAddress(""));
		result.setBirthDay(person.getBirthDate());

		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);
		String cid = AppContexts.user().companyId();
		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(cid, emp.getEmployeeId());

		AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(emp.getEmployeeId());

		AffCompanyHistItem affComHistItem = new AffCompanyHistItem();

		if (affComHistByEmp.items() != null) {

			List<AffCompanyHistItem> filter = affComHistByEmp.getLstAffCompanyHistoryItem().stream().filter(m -> {
				return m.end().afterOrEquals(systemDate) && m.start().beforeOrEquals(systemDate);
			}).collect(Collectors.toList());

			if (!filter.isEmpty()) {
				affComHistItem = filter.get(0);
				result.setRetiredDate(affComHistItem.end());
				result.setEntryDate(affComHistItem.start());
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc) req 126
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#findBySIds(java.util.
	 * List)
	 */
	@Override
	public List<EmployeeBasicInfoExport> findBySIds(List<String> sIds) {

		String cid = AppContexts.user().companyId();
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);

		List<EmployeeDataMngInfo> emps = this.empDataMngRepo.getByListEmployeeId(sIds);

		if (CollectionUtil.isEmpty(emps)) {
			return null;
		}

		List<String> pIds = emps.stream().map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList());

		List<Person> persons = this.personRepository.getPersonByPersonIds(pIds);

		Map<String, Person> mapPersons = persons.stream()
				.collect(Collectors.toMap(Person::getPersonId, Function.identity()));

		return emps.stream().map(employee -> {

			EmployeeBasicInfoExport result = new EmployeeBasicInfoExport();

			// Get Person
			Person person = mapPersons.get(employee.getPersonId());

			if (person != null) {
				result.setGender(person.getGender().value);
				result.setPName(person.getPersonNameGroup().getBusinessName() == null ? null
						: person.getPersonNameGroup().getBusinessName().v());
				result.setBirthDay(person.getBirthDate());
			}

			AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(cid, employee.getEmployeeId());

			AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(employee.getEmployeeId());

			AffCompanyHistItem affComHistItem = new AffCompanyHistItem();

			if (affComHistByEmp.items() != null) {

				List<AffCompanyHistItem> filter = affComHistByEmp.getLstAffCompanyHistoryItem().stream().filter(m -> {
					return m.end().afterOrEquals(systemDate) && m.start().beforeOrEquals(systemDate);
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

	@Override
	public List<String> GetListSid(String sid, GeneralDate baseDate) {

		if (sid == null || baseDate == null) {
			return null;
		}

		// get AffWorkplaceHistoryItem
		AffWorkplaceHistoryItem affWkpItem = this.getAffWkpItem(sid, baseDate);
		if (affWkpItem == null) {
			return null;
		}

		// Get List WkpId ( Get From RequestList #154(ANH THANH NWS))
		List<String> lstWkpId = syWorkplacePub.findListWorkplaceIdByCidAndWkpIdAndBaseDate(
				AppContexts.user().companyId(), affWkpItem.getWorkplaceId(), baseDate);

		if (lstWkpId.isEmpty()) {
			return null;
		}

		List<AffWorkplaceHistoryItem> result = this.affWkpItemRepo.getAffWrkplaHistItemByListWkpIdAndDate(baseDate,
				lstWkpId);

		if (result.isEmpty()) {
			return null;
		}

		return result.stream().map(f -> f.getEmployeeId()).collect(Collectors.toList());
	}

	private AffWorkplaceHistoryItem getAffWkpItem(String sid, GeneralDate basedate) {

		List<AffWorkplaceHistoryItem> lstWkpHistItem = affWkpItemRepo.getAffWrkplaHistItemByEmpIdAndDate(basedate, sid);
		if (lstWkpHistItem.isEmpty()) {
			return null;
		}
		return lstWkpHistItem.get(0);

	}

	@Override
	public List<String> getEmployeeCode(String sid, GeneralDate basedate) {

		if (sid == null || basedate == null) {
			return null;
		}

		List<EmploymentHistoryItem> lstHistItem = emptHistItem.getEmploymentByEmpIdAndDate(basedate, sid);

		if (lstHistItem.isEmpty()) {
			return null;
		}
		return lstHistItem.stream().map(i -> i.getEmploymentCode() == null ? "" : i.getEmploymentCode().v())
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#getSdataMngInfo(java.lang.
	 * String)
	 */
	@Override
	public Optional<EmployeeDataMngInfoExport> getSdataMngInfo(String sid) {
		Optional<EmployeeDataMngInfo> optEmployeeDataMngInfo = this.sDataMngInfoRepo.findByEmpId(sid);

		// Check exist
		if (!optEmployeeDataMngInfo.isPresent()) {
			return Optional.empty();
		}

		EmployeeDataMngInfo mngInfo = optEmployeeDataMngInfo.get();

		return Optional.of(EmployeeDataMngInfoExport.builder().companyId(mngInfo.getCompanyId())
				.personId(mngInfo.getPersonId()).employeeId(mngInfo.getEmployeeId())
				.employeeCode(mngInfo.getEmployeeCode().v()).deletedStatus(mngInfo.getDeletedStatus().value)
				.deleteDateTemporary(mngInfo.getDeleteDateTemporary()).removeReason(mngInfo.getRemoveReason().v())
				.externalCode(mngInfo.getExternalCode().v()).build());
	}

	@Override
	public List<EmployeeInfoExport> getByListSid(List<String> sIds) {

		if (CollectionUtil.isEmpty(sIds)) {
			return Collections.emptyList();
		}
		// Lấy toàn bộ domain「社員データ管理情報」
		List<EmployeeDataMngInfo> emps = this.empDataMngRepo.getByListEmployeeId(sIds);

		if (CollectionUtil.isEmpty(emps)) {
			return Collections.emptyList();
		}

		List<String> pIds = emps.stream().map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList());
		
		// Lấy toàn bộ domain「個人基本情報」
		List<Person> persons = this.personRepository.getPersonByPersonIds(pIds);

		Map<String, Person> mapPersons = persons.stream()
				.collect(Collectors.toMap(Person::getPersonId, Function.identity()));
		
		return emps.stream().map(employee -> {

			EmployeeInfoExport result = new EmployeeInfoExport();

			// Get Person
			Person person = mapPersons.get(employee.getPersonId());

			if (person != null) {
				result.setBussinessName(person.getPersonNameGroup().getBusinessName() == null ? null
						: person.getPersonNameGroup().getBusinessName().v());
			}
			result.setSid(employee.getEmployeeId());
			result.setScd(employee.getEmployeeCode() == null ? null : employee.getEmployeeCode().v());

			return result;
		}).collect(Collectors.toList());
	}

}
