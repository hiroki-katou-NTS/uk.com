/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.query.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.access.person.SyPersonAdapter;
import nts.uk.ctx.bs.employee.dom.access.person.dto.PersonImport;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItem_ver1;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistory_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory_ver1;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeQueryProcessor.
 */
@Stateless
public class EmployeeSearchQueryProcessor {
	/** The person repository. */
	@Inject
	private SyPersonAdapter personAdapter;

	/** The workplace repository. */
	@Inject
	private WorkplaceConfigInfoRepository workplaceConfigInfoRepo;

	@Inject
	private WorkplaceInfoRepository workplaceInfoRepo;

	/** The job title repository. */
	@Inject
	private JobTitleInfoRepository jobTitleInfoRepo;

	/** The employee repository. */
	@Inject
	private EmployeeDataMngInfoRepository employeeRepository;

	/** The employment history repository. */
	@Inject
	private EmploymentHistoryItemRepository employmentHistoryRepository;

	/** The classification history repository. */
	@Inject
	private AffClassHistItemRepository_ver1 classificationHistoryRepository;

	/** The job title history repository. */
	@Inject
	private AffJobTitleHistoryRepository_ver1 jobTitleHistoryRepository;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository_v1 workplaceHistoryRepository;
	
	/** The item repository. */
	@Inject
	private AffWorkplaceHistoryItemRepository_v1 itemRepository;
	
//	@Inject
//	private EmpInDesignatedPub empInDesignatedPub;

	/**
 * To employee.
 *
 * @param baseDate the base date
 * @param employeeIds the employee ids
 * @param companyId the company id
 * @return the list
 */

	public List<EmployeeSearchData> toEmployee(GeneralDate baseDate, List<String> employeeIds, String companyId) {

		// get employee list
		List<EmployeeDataMngInfo> employeeDatas = this.employeeRepository.findByListEmployeeId(companyId, employeeIds);

		// check exist employee
		if (CollectionUtil.isEmpty(employeeDatas)) {
			throw new BusinessException("Msg_317");
		}
		
		// Filter by state
		employeeDatas = employeeDatas.stream()
				.filter(item -> { return EmployeeDeletionAttr.NOTDELETED.value == item.getDeletedStatus().value; })
				.collect(Collectors.toList());

		// get work place history by employee
		List<AffWorkplaceHistory_ver1> workplaceHistory = this.workplaceHistoryRepository
				.findByEmployees(employeeIds, baseDate);

		// check exist data work place
		if (CollectionUtil.isEmpty(workplaceHistory)) {
			throw new BusinessException("Msg_177");
		}

		// get all work place of company
		List<WorkplaceInfo> workplaces = this.workplaceInfoRepo.findAll(companyId, baseDate);

		// to map work place
		Map<String, WorkplaceInfo> workplaceMap = workplaces.stream().collect(Collectors.toMap((workplace) -> {
			return workplace.getWorkplaceId();
		}, Function.identity()));

		// to map work place history
		Map<String, AffWorkplaceHistory_ver1> workplaceHistoryMap = workplaceHistory.stream()
				.collect(Collectors.toMap((workplace) -> {
					return workplace.getEmployeeId();
				}, Function.identity()));

		// get person name
		List<PersonImport> persons = this.personAdapter
				.findByPersonIds(employeeDatas.stream().map(employee -> employee.getPersonId()).collect(Collectors.toList()));

		// to map person (person id)
		Map<String, PersonImport> personMap = persons.stream().collect(Collectors.toMap((person) -> {
			return person.getPersonId();
		}, Function.identity()));

		List<EmployeeSearchData> employeeSearchData = new ArrayList<>();

		employeeDatas.forEach(employee -> {
			// check exist data
			if (workplaceHistoryMap.containsKey(employee.getEmployeeId())
					&& personMap.containsKey(employee.getPersonId())) {
				
				// Find
				String wplId = this.itemRepository
						.getByHistId(
								workplaceHistoryMap.get(employee.getEmployeeId()).getHistoryItems().get(0).identifier())
						.get().getWorkplaceId();
				// If worplace is not found.
				if (workplaceMap.get(wplId) == null) {
					return;
				}
 				// add to dto
				EmployeeSearchData dto = new EmployeeSearchData();
				dto.setEmployeeId(employee.getEmployeeId());
				dto.setEmployeeCode(employee.getEmployeeCode().v());
				dto.setEmployeeName(personMap.get(employee.getPersonId()).getPersonName());
				dto.setWorkplaceId(wplId);

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
	public List<EmployeeSearchData> searchAllEmployee(GeneralDate baseDate) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all employee data of company id
		List<EmployeeDataMngInfo> employeeDatas = this.employeeRepository.findByCompanyId(companyId);

		return toEmployee(baseDate,
				employeeDatas.stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList()),
				companyId);
	}

	/**
	 * Search all employee.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	public List<EmployeeSearchData> searchEmployeeByLogin(GeneralDate baseDate) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
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
		List<EmploymentHistoryItem> employmentHistory = this.employmentHistoryRepository
				.searchEmployee(input.getBaseDate(), input.getEmploymentCodes());

		// find by classification
		List<AffClassHistItem_ver1> classificationHistorys = this.classificationHistoryRepository
				.searchClassification(
						employmentHistory.stream().map(employment -> employment.getEmployeeId())
								.collect(Collectors.toList()),
						input.getBaseDate(), input.getClassificationCodes());

		// find by job title
		List<AffJobTitleHistory_ver1> jobTitleHistory = this.jobTitleHistoryRepository
				.searchJobTitleHistory(input.getBaseDate(), classificationHistorys.stream()
						.map(classification -> classification.getEmployeeId()).collect(Collectors.toList()));
		// find by work place
		List<AffWorkplaceHistory_ver1> workplaceHistory = this.workplaceHistoryRepository
				.findByEmployees(
						jobTitleHistory.stream().map(jobtitle -> jobtitle.getEmployeeId())
								.collect(Collectors.toList()),
						input.getBaseDate());
		List<String> hisIds = workplaceHistory.stream().map(wh -> wh.items().get(0).identifier()).collect(Collectors.toList());
		List<String> empInWpl = this.itemRepository.findByHistIds(hisIds).stream().map(item -> item.getEmployeeId()).collect(Collectors.toList());
		// to employees
		List<EmployeeDataMngInfo> employeeDatas = this.employeeRepository.findByListEmployeeId(companyId, empInWpl);

		// to person info
		return this.toEmployee(input.getBaseDate(),
				employeeDatas.stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList()),
				companyId);
	}

	/**
	 * Search of workplace.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	public List<EmployeeSearchData> searchOfWorkplace(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee id
		String employeeId = loginUserContext.employeeId();

		Optional<AffWorkplaceHistory_ver1> workplaceHistory = this.workplaceHistoryRepository
				.getByEmpIdAndStandDate(employeeId, baseDate);

		// check exist data
		if (!workplaceHistory.isPresent()) {
			throw new BusinessException("Msg_177");
		}

		// return data
		return this.toEmployee(baseDate, Arrays.asList(employeeId), companyId);
	}

	/**
	 * Search workplace child.
	 *
	 * @param baseDate
	 *            the base date
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
		Optional<AffWorkplaceHistory_ver1> workplaceHistory = this.workplaceHistoryRepository
				.getByEmpIdAndStandDate(employeeId, baseDate);

		// check exist data
		if (!workplaceHistory.isPresent()) {
			throw new BusinessException("Msg_177");
		}
		
		String wplId = this.itemRepository.getByHistId(workplaceHistory.get().getHistoryItems().get(0).identifier()).get().getWorkplaceId();

		// get data child
		List<WorkplaceHierarchy> workPlaceHierarchies = this.workplaceConfigInfoRepo
				.findAllByParentWkpId(companyId, baseDate, wplId).get().getLstWkpHierarchy();
		List<String> wplIds = workPlaceHierarchies.stream().map(wp -> wp.getWorkplaceId()).collect(Collectors.toList());
		List<AffWorkplaceHistoryItem> itemList = this.itemRepository.findeByWplIDs(wplIds);
		
		List<String> empIds = itemList.stream().filter(item -> {
			return this.workplaceHistoryRepository.getByHistIdAndBaseDate(item.getHistoryId(), baseDate).isPresent();
		}).map(item -> item.getEmployeeId()).distinct().collect(Collectors.toList());
		// return data
		return this.toEmployee(baseDate, empIds, companyId);
	}

	/**
	 * Search workplace of employee.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	public List<String> searchWorkplaceOfEmployee(GeneralDate baseDate) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get employee id
		String employeeId = loginUserContext.employeeId();

		// get data work place history
		List<AffWorkplaceHistoryItem> items = this.itemRepository.getAffWrkplaHistItemByEmpIdAndDate(baseDate, employeeId);

		// return data
		return items.stream().map(AffWorkplaceHistoryItem::getWorkplaceId).collect(Collectors.toList());
	}

	/**
	 * Gets the of selected employee.
	 *
	 * @param input
	 *            the input
	 * @return the of selected employee
	 */
	public List<EmployeeSearchData> getOfSelectedEmployee(EmployeeSearchListQuery input) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		return this.toEmployee(input.getBaseDate(), input.getEmployeeIds(), companyId);
	}

	/**
	 * Search employees.
	 *
	 * @param query
	 *            the query
	 * @return the list
	 */
	public List<EmployeeSearchListData> searchEmployees(EmployeeSearchListQuery query) {

		// check exist data
		if (CollectionUtil.isEmpty(query.getEmployeeIds())) {
			return new ArrayList<>();
		}

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee data list
		List<EmployeeDataMngInfo> employeeDatas = this.employeeRepository.findByListEmployeeId(companyId,
				query.getEmployeeIds());

		// get map person
		Map<String, PersonImport> personMap = this.personAdapter
				.findByPersonIds(
						employeeDatas.stream().map(employee -> employee.getPersonId()).collect(Collectors.toList()))
				.stream().collect(Collectors.toMap((person) -> {
					return person.getPersonId();
				}, Function.identity()));

		// get map work place history
		Map<String, AffWorkplaceHistory_ver1> mapWorkplaceHistory = this.workplaceHistoryRepository
				.findByEmployees(query.getEmployeeIds(), query.getBaseDate()).stream()
				.collect(Collectors.toMap((workplace) -> {
					return workplace.getEmployeeId();
				}, Function.identity()));

		// get map work place
		Map<String, WorkplaceInfo> mapWorkplace = this.workplaceInfoRepo
				.findAll(companyId, query.getBaseDate()).stream()
				.collect(Collectors.toMap((workplace) -> {
					return workplace.getWorkplaceId();
				}, Function.identity()));

		// get map job title history
		Map<String, AffJobTitleHistory_ver1> mapJobTitleHistory = this.jobTitleHistoryRepository
				.findAllJobTitleHistory(query.getBaseDate(), query.getEmployeeIds()).stream()
				.collect(Collectors.toMap((jobtitle) -> {
					return jobtitle.getEmployeeId();
				}, Function.identity()));

		// get map job title
		Map<String, JobTitleInfo> mapJobTitle = this.jobTitleInfoRepo
				.findAll(companyId, query.getBaseDate()).stream()
				.collect(Collectors.toMap((jobtitle) -> {
					return jobtitle.getJobTitleId();
				}, Function.identity()));
		List<EmployeeSearchListData> dataRes = new ArrayList<>();

		for (EmployeeDataMngInfo employeeData : employeeDatas) {
			EmployeeSearchListData data = new EmployeeSearchListData();
			data.setEmployeeId(employeeData.getEmployeeId());
			data.setEmployeeCode(employeeData.getEmployeeCode().v());

			// check exist person data
			if (personMap.containsKey(employeeData.getPersonId())) {
				data.setEmployeeName(personMap.get(employeeData.getPersonId()).getPersonName());
			}

			// check exist work place history
			// Find 
			if (mapWorkplaceHistory.containsKey(employeeData.getEmployeeId())) {
				String wplId = this.itemRepository
						.getByHistId(
								mapWorkplaceHistory.get(employeeData.getEmployeeId()).getHistoryItems().get(0).identifier())
						.get().getWorkplaceId();
				WorkplaceInfo workplace = mapWorkplace.get(wplId);
				data.setWorkplaceId(workplace.getWorkplaceId());
				data.setWorkplaceCode(workplace.getWorkplaceCode().v());
				data.setWorkplaceName(workplace.getWorkplaceName().v());
			}

			// check exist job title history
			if (mapJobTitleHistory.containsKey(employeeData.getEmployeeId()) && mapJobTitle
					.containsKey(mapJobTitleHistory.get(employeeData.getEmployeeId()).getEmployeeId())) {
				AffJobTitleHistory_ver1 jobTitleHistory = mapJobTitleHistory.get(employeeData.getEmployeeId());
				JobTitleInfo jobTitleInfo = mapJobTitle.get(jobTitleHistory.getEmployeeId());
				data.setJobTitleId(jobTitleInfo.getJobTitleId());
				data.setJobTitleCode(jobTitleInfo.getJobTitleCode().v());
				data.setJobTitleName(jobTitleInfo.getJobTitleName().v());
			}
			dataRes.add(data);
		}
		return dataRes;
	}

}
