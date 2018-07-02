/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.ArrayList;
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
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.pub.employee.ConcurrentEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmpOfLoginCompanyExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDataMngInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.JobClassification;
import nts.uk.ctx.bs.employee.pub.employee.MailAddress;
import nts.uk.ctx.bs.employee.pub.employee.StatusOfEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	private SyWorkplacePub syWorkplacePub;

	@Inject
	private EmploymentHistoryItemRepository emptHistItem;

	@Inject
	private EmployeeDataMngInfoRepository sDataMngInfoRepo;
	
	@Inject
	private TempAbsHistRepository  tempAbsHistRepository;

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
		// Get Employee
		Optional<EmployeeDataMngInfo> empOpt = this.empDataMngRepo.findByEmpId(sId);
		if (!empOpt.isPresent()) {
			return null;
		}
		
		EmployeeDataMngInfo emp = empOpt.get();

		// Lay thông tin lịch sử vào ra công ty của nhân viên
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);
		String cid = AppContexts.user().companyId();
		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(cid, emp.getEmployeeId());

		AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(emp.getEmployeeId());

		Optional.ofNullable(affComHistByEmp).ifPresent(f -> {
			if (f.items() != null) {

				List<AffCompanyHistItem> filter = f.getLstAffCompanyHistoryItem();
				// lấy lịch sử ra vào cty gần nhất (order by RetiredDate DESC) 
				filter.sort((x, y) -> y.getDatePeriod().end().compareTo(x.getDatePeriod().end()));

				// set entryDate
				result.setEntryDate(filter.get(0).getDatePeriod().start());

				// set retireDate
				result.setRetiredDate(filter.get(0).getDatePeriod().end());
			}
		});

		// Lay thông tin Person
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

		List<EmployeeDataMngInfo> emps = this.empDataMngRepo.findByListEmployeeId(sIds);

		if (CollectionUtil.isEmpty(emps)) {
			return Collections.emptyList();
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
		List<EmployeeDataMngInfo> emps = this.empDataMngRepo.findByListEmployeeId(sIds);

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
	@Override
	public List<String> getListEmpByWkpAndEmpt(List<String> wkpsId, List<String> lstemptsCode, DatePeriod dateperiod) {

		// lấy List sid từ dateperiod and list workplaceId
		List<String> lstSidFromWorkPlace = affWkpItemRepo.getSidByListWkpIdAndDatePeriod(dateperiod, wkpsId);

		if (lstSidFromWorkPlace.isEmpty()) {
			return Collections.emptyList();
		}
		
		// (Thực hiện Lấy List sid từ list employeementId và period)

		List<String> lstSidFromEmpt = emptHistItem.getLstSidByListCodeAndDatePeriod(dateperiod, lstemptsCode);

		if (lstSidFromEmpt.isEmpty()) {
			return Collections.emptyList();
		}

		// lấy list sid chung từ 2 list lstEmpIdOfWkp vs lstEmpIdOfEmpt
		List<String> generalLstId = lstSidFromWorkPlace.stream().filter(lstSidFromEmpt::contains).collect(Collectors.toList());

		if (generalLstId.isEmpty()) {
			return Collections.emptyList();
		}

		// lây list Sid từ list sid và dateperiod
		List<String> lstSidFromAffComHist = affComHistRepo.getLstSidByLstSidAndPeriod(generalLstId, dateperiod);

		if (lstSidFromAffComHist.isEmpty()) {
			return Collections.emptyList();
		}

		// lây list sid từ list sid và dateperiod
		List<String> lstTempAbsenceHistory =  tempAbsHistRepository.getLstSidByListSidAndDatePeriod(lstSidFromAffComHist , dateperiod );
		
		// List sid tồn tại ở lstSidFromAffComHist nhưng không tồn tại ở list lstSidFromTempAbsHis
		List<String> result = lstSidFromAffComHist.stream().filter(i -> !lstTempAbsenceHistory.contains(i)).collect(Collectors.toList());
		if (result.isEmpty()) {
			return Collections.emptyList();
		}
		return result;
	}

	public List<AffCompanyHistByEmployee> getAffCompanyHistByEmployee(List<AffCompanyHist> lstAffComHist) {
		if (lstAffComHist.isEmpty()) {
			return Collections.emptyList();
		}
		List<AffCompanyHistByEmployee> result = new ArrayList<>();

		lstAffComHist.forEach(m -> {
			result.addAll(m.getLstAffCompanyHistByEmployee());
		});
		return result;
	}

	@Override
	public List<EmpOfLoginCompanyExport> getListEmpOfLoginCompany(String cid) {

		// lây toàn bộ nhân viên theo cid
		List<EmployeeDataMngInfo> lstEmp = empDataMngRepo.getAllByCid(cid);

		if (lstEmp.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> lstpid = lstEmp.stream().map(m -> m.getPersonId()).collect(Collectors.toList());

		Map<String, Person> personMap = personRepository.getPersonByPersonIds(lstpid).stream()
				.collect(Collectors.toMap(x -> x.getPersonId(), x -> x));
		List<EmpOfLoginCompanyExport> lstresult = new ArrayList<>();
		lstEmp.forEach(m -> {
			EmpOfLoginCompanyExport emp = new EmpOfLoginCompanyExport();
			emp.setScd(m.getEmployeeCode().v());
			emp.setSid(m.getEmployeeId());
			emp.setBussinesName(personMap.get(m.getPersonId()).getPersonNameGroup().getBusinessName().v());
			lstresult.add(emp);

		});
		return lstresult;
	}

	@Override
	public EmployeeBasicExport getEmpBasicBySId(String sId) {

		EmployeeBasicExport result = new EmployeeBasicExport();
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

		result.setEmployeeId(emp.getEmployeeId());
		result.setBusinessName(person.getPersonNameGroup().getBusinessName().v());
		result.setEmployeeCode(emp.getEmployeeCode().v());

		return result;
	}

	
	@Override
	public StatusOfEmployeeExport getStatusOfEmployee(String sid) {

		Optional<EmployeeDataMngInfo> empOpt = this.empDataMngRepo.findByEmpId(sid);
		if (empOpt.isPresent()) {
			return new StatusOfEmployeeExport(empOpt.get().getDeletedStatus().value == 0 ? false : true);
		} else {
			return null;
		}
	}
}
