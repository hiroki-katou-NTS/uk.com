/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
//import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
//import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrame;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceRepositoryFrame;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.pub.employee.ConcurrentEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmpInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmpInfoRegistered;
import nts.uk.ctx.bs.employee.pub.employee.EmpOfLoginCompanyExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeIdCdPnameExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDataMngInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.JobClassification;
import nts.uk.ctx.bs.employee.pub.employee.MailAddress;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest596Export;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.bs.employee.pub.employee.StatusOfEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.TempAbsenceFrameExport;
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
	private TempAbsHistRepository tempAbsHistRepository;
	
	@Inject
	private TempAbsenceRepositoryFrame tempAbsenceRepoFrame;
	
	@Inject
	private PersonRepository personRepo;

//	@Inject
//	private AffJobTitleHistoryRepository affJobRep;

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

		List<EmployeeDataMngInfo> employeeList = empDataMngRepo.findByListEmployeeId(companyId,
				employeeIds.stream().distinct().collect(Collectors.toList()));
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);

		return employeeList.stream().map(employee -> {

			EmployeeExport result = new EmployeeExport();

			AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(employee.getEmployeeId());

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

		List<EmployeeDataMngInfo> employeeList = empDataMngRepo.findByListEmployeeId(companyId,
				employeeIds.stream().distinct().collect(Collectors.toList()));

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
		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(emp.getEmployeeId());

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

		result.setPId(person.getPersonId());
		result.setEmployeeId(emp.getEmployeeId());
		result.setPName(person.getPersonNameGroup().getBusinessName().toString());
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
	public List<EmployeeBasicInfoExport> findBySIds(List<String> sIdsInput) {

		List<EmployeeDataMngInfo> emps = this.empDataMngRepo
				.findByListEmployeeId(sIdsInput.stream().distinct().collect(Collectors.toList()));

		if (CollectionUtil.isEmpty(emps)) {
			return Collections.emptyList();
		}

		List<String> pIds = emps.stream().map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList());

		List<String> sIds = emps.stream().map(EmployeeDataMngInfo::getEmployeeId).collect(Collectors.toList());

		List<Person> persons = this.personRepository.getPersonByPersonIds(pIds);

		Map<String, Person> mapPersons = persons.stream()
				.collect(Collectors.toMap(Person::getPersonId, Function.identity()));

		Map<String, Optional<AffCompanyHistItem>> mapAffComHistItem = this.affComHistRepo.getAffEmployeeHistory(sIds)
				.stream().collect(Collectors.toMap(e -> e.getSId(), e -> {
					List<AffCompanyHistItem> lstAff = e.getLstAffCompanyHistoryItem().stream()
							.sorted((f1, f2) -> f2.getDatePeriod().start().compareTo(f1.getDatePeriod().start()))
							.collect(Collectors.toList());
					return !lstAff.isEmpty() ? Optional.of(lstAff.get(0)) : Optional.empty();
				}));

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

			Optional<AffCompanyHistItem> affCompanyHistItem = mapAffComHistItem.get(employee.getEmployeeId());

			if (affCompanyHistItem.isPresent()) {
				result.setEntryDate(affCompanyHistItem.get().getDatePeriod().start());
				result.setRetiredDate(affCompanyHistItem.get().getDatePeriod().end());
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
		List<EmployeeDataMngInfo> emps = this.empDataMngRepo
				.findByListEmployeeId(sIds.stream().distinct().collect(Collectors.toList()));

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
		List<String> generalLstId = lstSidFromWorkPlace.stream().filter(lstSidFromEmpt::contains)
				.collect(Collectors.toList());

		if (generalLstId.isEmpty()) {
			return Collections.emptyList();
		}
		// (Lấy danh sách sid từ  domain 「所属会社履歴（社員別）」)
		List<String> lstSidFromAffComHist = affComHistRepo.getLstSidByLstSidAndPeriod(generalLstId, dateperiod);

		if (lstSidFromAffComHist.isEmpty()) {
			return Collections.emptyList();
		}
		// (Lấy danh sách sid từ domain 「休職休業履歴」) 		
		List<String> lstSidAbsHist_NoCheckDate = tempAbsHistRepository.getByListSid(lstSidFromAffComHist);
		
		//(lưu employeeID  ko lấy được ở domain 「休職休業履歴」 vào list nhân viên đương nhiệm)
		List<String> result = lstSidFromAffComHist.stream().filter(i -> !lstSidAbsHist_NoCheckDate.contains(i))
				.collect(Collectors.toList());
		
		// lây list sid từ list sid và dateperiod
		List<String> lstTempAbsenceHistory = tempAbsHistRepository.getLstSidByListSidAndDatePeriod(lstSidAbsHist_NoCheckDate,
				dateperiod);
		
		if(!lstTempAbsenceHistory.isEmpty()) {
			result.addAll(lstTempAbsenceHistory);
		}
		
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

		List<String> lstpid = lstEmp.stream().map(m -> m.getPersonId()).distinct().collect(Collectors.toList());

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

	@Override
	public boolean isEmployeeDelete(String sid) {
		Optional<EmployeeDataMngInfo> optEmployeeData = this.sDataMngInfoRepo.findByEmpId(sid);
		if (!optEmployeeData.isPresent()
				|| (optEmployeeData.get().getDeletedStatus() == EmployeeDeletionAttr.TEMPDELETED
						|| optEmployeeData.get().getDeletedStatus() == EmployeeDeletionAttr.PURGEDELETED)) {
			return true;
		}
		return false;
	}

	@Override
	public List<EmpInfoExport> getEmpInfo(List<String> lstSid) {

		if (lstSid.isEmpty())
			return new ArrayList<>();

		List<EmployeeDataMngInfo> lstEmp = this.empDataMngRepo
				.findByListEmployeeId(lstSid.stream().distinct().collect(Collectors.toList()));

		if (lstEmp.isEmpty())
			return new ArrayList<>();

		List<String> sids = lstEmp.stream().map(i -> i.getEmployeeId()).collect(Collectors.toList());

		List<String> pids = lstEmp.stream().map(i -> i.getPersonId()).collect(Collectors.toList());

		Map<String, List<AffCompanyHistItem>> mapAffComHistItem = this.affComHistRepo.getAffEmployeeHistory(sids)
				.stream().collect(Collectors.toMap(e -> e.getSId(), e -> e.getLstAffCompanyHistoryItem()));

		Map<String, Person> personMap = personRepository.getPersonByPersonIds(pids).stream()
				.collect(Collectors.toMap(x -> x.getPersonId(), x -> x));

		return lstEmp.stream().map(employee -> {

			EmpInfoExport result = new EmpInfoExport();

			// Get Person
			Person person = personMap.get(employee.getPersonId());

			if (person != null) {
				result.setGender(person.getGender().value);
				result.setBusinessName(person.getPersonNameGroup().getBusinessName() == null ? null
						: person.getPersonNameGroup().getBusinessName().v());
				result.setBirthDay(person.getBirthDate());

				List<AffCompanyHistItem> lstAffComHistItem = mapAffComHistItem.get(employee.getEmployeeId());

				if (lstAffComHistItem != null) {
					List<AffCompanyHistItem> lstAff = lstAffComHistItem.stream()
							.sorted((f1, f2) -> f2.getDatePeriod().start().compareTo(f1.getDatePeriod().start()))
							.collect(Collectors.toList());

					result.setEntryDate(lstAff.get(0).getDatePeriod().start());
					result.setRetiredDate(lstAff.get(0).getDatePeriod().end());
				}

				result.setPId(employee.getPersonId());
				result.setEmployeeCode(employee.getEmployeeCode() == null ? null : employee.getEmployeeCode().v());
				result.setEmployeeId(employee.getEmployeeId());

				return result;
			}

			return null;

		}).filter(f -> f != null).collect(Collectors.toList());
	}

	@Override
	public List<String> getListEmployeeId(List<String> wkpIds, DatePeriod dateperiod) {
		// lấy List sid từ dateperiod and list workplaceId
		List<String> lstSidFromWorkPlace = affWkpItemRepo.getSidByListWkpIdAndDatePeriod(dateperiod, wkpIds);
		List<String> lstSidResult = affComHistRepo.getLstSidByLstSidAndPeriod(lstSidFromWorkPlace, dateperiod);
		return lstSidResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#findBySIdAndCompanyId(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public EmployeeBasicInfoExport findBySIdAndCompanyId(String sId, String companyId) {

		EmployeeBasicInfoExport result = new EmployeeBasicInfoExport();
		// Get Employee
		Optional<EmployeeDataMngInfo> empOpt = this.empDataMngRepo.findByEmpId(sId);
		if (!empOpt.isPresent()) {
			return null;
		}

		EmployeeDataMngInfo emp = empOpt.get();

		// Lay thông tin lịch sử vào ra công ty của nhân viên
		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployeeDesc(companyId, emp.getEmployeeId());

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

		result.setPId(person.getPersonId());
		result.setEmployeeId(emp.getEmployeeId());
		result.setPName(person.getPersonNameGroup().getBusinessName().toString());
		result.setGender(person.getGender().value);
		result.setPMailAddr(new MailAddress(""));
		result.setEmployeeCode(emp.getEmployeeCode().v());
		result.setCompanyMailAddr(new MailAddress(""));
		result.setBirthDay(person.getBirthDate());

		return result;
	}

	// request list 515
	@Override
	public List<String> getListEmployee(List<String> jobTitleIds, GeneralDate baseDate) {
		String cid = AppContexts.user().companyId();
		List<AffCompanyHist> listAffComHist = new ArrayList<>();
		List<String> employee = new ArrayList<>();
		
		// (Lấy domain [AffJobHistoryItem])
		List<AffJobTitleHistoryItem> listAffItem = jobTitleHistoryItemRepository.findHistJob(cid, baseDate, jobTitleIds);

		if (!listAffItem.isEmpty()) {
			// (Lấy domain [EmployeeDataMngInfo], filter chỉ những employee chưa bị delete)
			List<EmployeeDataMngInfo> mngInfo = empDataMngRepo
					.findBySidNotDel(listAffItem.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList()));
			if (!mngInfo.isEmpty()) {
				// (Lấy domain [AffCompanyHistByEmployee], chỉ filter employee đang làm tại thời
				// điểm baseDate)
				listAffComHist = affComHistRepo.getAffCompanyHistoryOfEmployeeListAndBaseDate(
					mngInfo.stream().map(x -> x.getEmployeeId()).distinct().collect(Collectors.toList()),
					baseDate);
			}
			if (!listAffComHist.isEmpty()) {
				// lấy list employee Id
				for (AffCompanyHist object : listAffComHist) {
					if(object != null){
						List<String> employeetemp = object.getLstAffCompanyHistByEmployee().stream().map(x -> x.getSId())
								.collect(Collectors.toList());
						employee.addAll(employeetemp);
					}
				}
			}
			return employee;
		}
		return new ArrayList<>();
	}

	@Override
	public Optional<EmpInfoRegistered> getEmpInfo(String cid, String pid) {

		boolean check = false;

		Optional<EmployeeDataMngInfo> empData = empDataMngRepo.findByCidPid(cid, pid);
		if (!empData.isPresent())
			return Optional.empty();

		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);

		List<AffCompanyHistByEmployee> affComHist = affComHistRepo
				.getAffEmployeeHistory(Arrays.asList(empData.get().getEmployeeId()));
		if (affComHist.isEmpty())
			return Optional.empty();
		
		List<AffCompanyHistItem> lstAffComHisItem = affComHist.get(0).items();
		
		for (int i = 0; i < lstAffComHisItem.size(); i++) {
			if (!lstAffComHisItem.get(i).afterOrEqualsStandardDate(systemDate) && !lstAffComHisItem.get(i).beforeOrEqualsStandardDate(systemDate)) {
				check = true;
				break;
			}
		}
		
		if(!check)
			return Optional.empty();
		
		Optional<Person> personOpt = this.personRepository.getByPersonId(empData.get().getPersonId());
		
		if(!personOpt.isPresent())
			return Optional.empty();
		
		EmpInfoRegistered result = new EmpInfoRegistered(
				cid,
				empData.get().getEmployeeCode().v(),
				empData.get().getEmployeeId(),
				personOpt.get().getPersonNameGroup().getBusinessName()!= null ? personOpt.get().getPersonNameGroup().getBusinessName().v() : null,
				pid);
		
		return Optional.of(result);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub#getSidCdPnameBySIds(java.util.List)
	 */
	@Override
	public List<EmployeIdCdPnameExport> getSidCdPnameBySIds(List<String> sIdsInput) {

		List<EmployeeDataMngInfo> emps = this.empDataMngRepo
				.findByListEmployeeId(sIdsInput.stream().distinct().collect(Collectors.toList()));

		if (CollectionUtil.isEmpty(emps)) {
			return Collections.emptyList();
		}

		List<String> pIds = emps.stream().map(EmployeeDataMngInfo::getPersonId)
				.collect(Collectors.toList());

		List<Person> persons = this.personRepository.getPersonByPersonIds(pIds);

		Map<String, Person> mapPersons = persons.stream()
				.collect(Collectors.toMap(Person::getPersonId, Function.identity()));

		return emps.stream().map(employee -> {
			// Get Person
			Person person = mapPersons.get(employee.getPersonId());

			EmployeIdCdPnameExport result = EmployeIdCdPnameExport.builder().build();

			if (person != null) {
				result.setPName(person.getPersonNameGroup().getBusinessName() == null ? null
						: person.getPersonNameGroup().getBusinessName().v());
			}

			result.setEmployeeCode(
					employee.getEmployeeCode() == null ? null : employee.getEmployeeCode().v());
			result.setEmployeeId(employee.getEmployeeId());

			return result;
		}).collect(Collectors.toList());
	}

	@Override
	public List<TempAbsenceFrameExport> getTempAbsenceFrameByCid(String cid) {
		List<TempAbsenceFrame> listTempAbsenceFrame = tempAbsenceRepoFrame.findByCidForReq546(cid);
		if (listTempAbsenceFrame.isEmpty())
			return new ArrayList<>();
		return listTempAbsenceFrame.stream().map(i -> {
			return new TempAbsenceFrameExport(i.getCompanyId(), i.getTempAbsenceFrNo().v().intValue(),
					i.getUseClassification().value, i.getTempAbsenceFrName().toString());
		}).collect(Collectors.toList());
	}
	
	@Override
	public List<ResultRequest596Export> getEmpDeletedLstBySids(List<String> sids) {
		List<ResultRequest596Export> result = new ArrayList<>();
		List<EmployeeDataMngInfo> emps = this.empDataMngRepo
				.findBySidDel(sids.stream().distinct().collect(Collectors.toList()));
		List<String> personLst = emps.parallelStream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<Person> personDomainLst = personRepo.getPersonByPersonIds(personLst);
		emps.parallelStream().forEach(c ->{
			Optional<Person> personOpt = personDomainLst.parallelStream().filter(p -> p.getPersonId().equals(c.getEmployeeId())).findFirst();
			if(personOpt.isPresent()) {
				result.add(new ResultRequest596Export(c.getEmployeeId(), c.getEmployeeCode().v(),
						personOpt.get().getPersonNameGroup().getBusinessName().v()));
			}
		});
		return result;
	}

	@Override
	public List<ResultRequest600Export> getEmpInfoLstBySids(List<String> sids, DatePeriod period, boolean isDelete, boolean isGetAffCompany) {
		List<String> personIds = new ArrayList<>();
		List<EmployeeDataMngInfo> emps = new ArrayList<>();
		List<ResultRequest600Export> result = new ArrayList<>();
		List<String> sidsDistinct = sids.stream().distinct().collect(Collectors.toList());
		
		//Input「削除社員を取り除く」をチェックする
		if(isDelete == true) {
			//ドメインモデル「社員データ管理情報」を取得する
			emps.addAll(this.empDataMngRepo.findBySidNotDel(sidsDistinct));
		}else {
			//ドメインモデル「社員データ管理情報」を全て取得する
			emps.addAll(this.empDataMngRepo.findByListEmployeeId(sidsDistinct));
		}
		
		if(!CollectionUtil.isEmpty(emps)) {
			//Input「会社に所属していない社員を取り除く」をチェックする
			if(isGetAffCompany == true) {
				//社員一覧を特定の会社に在籍している社員に絞り込む
				List<AffCompanyHist> affComHist = affComHistRepo.getAffComHisEmpByLstSidAndPeriod(sidsDistinct, period);
				if(!CollectionUtil.isEmpty(affComHist)) {
					personIds.addAll(affComHist.stream().map(c -> c.getPId()).collect(Collectors.toList()));
				}
				
			}else {
				personIds.addAll(emps.stream().map(c -> c.getPersonId()).collect(Collectors.toList()));
			}
			
			//ドメインモデル「個人基本情報」を全て取得する
			List<Person> personLst = personRepo.getPersonByPersonIds(personIds);
			emps.stream().forEach(c ->{
				Optional<Person> personOpt = personLst.stream().filter(p -> p.getPersonId().equals(c.getPersonId())).findFirst();
				if(personOpt.isPresent()) {
					result.add(new ResultRequest600Export(c.getEmployeeId(), c.getEmployeeCode().v(),
							personOpt.get().getPersonNameGroup().getBusinessName().v()));
				}
			});
		}

		return result;
	}

	@Override
	public List<String> filterSidLstByDatePeriodAndSids(List<String> sids, DatePeriod period) {
		List<String> sidsDistinct = sids.stream().distinct().collect(Collectors.toList());
		// ドメインモデル「所属会社履歴（社員別）」を取得する
		List<String> lstSidFromAffComHist = affComHistRepo.getLstSidByLstSidAndPeriod(sidsDistinct, period);

		if (lstSidFromAffComHist.isEmpty()) {
			return Collections.emptyList();
		}
		// ドメインモデル「休職休業履歴」を取得する		
		List<String> lstSidAbsHist_NoCheckDate = tempAbsHistRepository.getByListSid(lstSidFromAffComHist);
		
		//ドメインモデル「休職休業履歴」が取得できなかった社員は、在職者として社員IDをリストに保持する
		//(lưu employeeID  ko lấy được ở domain 「休職休業履歴」 vào list nhân viên đương nhiệm)
		List<String> result = lstSidFromAffComHist.stream().filter(i -> !lstSidAbsHist_NoCheckDate.contains(i))
				.collect(Collectors.toList());
		
		// 「休職休業履歴」．期間をチェックし、１日でも在職している社員を取得する
		//(Check 「休職休業履歴」．期間, lấy employee đang tồn tại dù chỉ 1 ngày)
		List<String> lstTempAbsenceHistory = tempAbsHistRepository.getLstSidByListSidAndDatePeriod(lstSidAbsHist_NoCheckDate,
				period);
		
		//１日でも在職している社員は在職者として社員IDをリストに保持する
		//(Lưu employee ở trên vào list employee đương nhiệm)
		if(!lstTempAbsenceHistory.isEmpty()) {
			result.addAll(lstTempAbsenceHistory);
		}
		
		if (result.isEmpty()) {
			return Collections.emptyList();
		}
		//在職者の社員IDをListで返す
		//(return list employeeID của nhân viên đương nhiệm)
		return result;
	}
	
	@Override
	public List<String> filterSidByCidAndPeriod(String cid, DatePeriod period) {
		//ドメインモデル「社員データ管理情報」を取得する
		List<String> listEmp = empDataMngRepo.getAllEmpNotDeleteByCid(cid).stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		//ドメインモデル「所属会社履歴（社員別）」をすべて取得する
		//取得できた「所属会社履歴（社員別）」の社員IDを返す
		List<String> lstSidResult = affComHistRepo.getLstSidByLstSidAndPeriod(listEmp, period);
		return lstSidResult;
	}
}
