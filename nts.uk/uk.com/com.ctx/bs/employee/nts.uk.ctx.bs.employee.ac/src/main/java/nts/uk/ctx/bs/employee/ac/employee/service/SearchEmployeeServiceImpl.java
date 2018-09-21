/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ac.employee.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employee.service.RoleTypeImport;
import nts.uk.ctx.bs.employee.dom.employee.service.SearchEmployeeService;
import nts.uk.ctx.bs.employee.dom.employee.service.System;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.EmployeeInforDto;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.EmployeeSearchData;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.EmployeeSearchDto;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.PersonalBasicInfo;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.sys.auth.pub.service.EmployeeReferenceRangePub;
import nts.uk.ctx.sys.auth.pub.service.RolePubService;
import nts.uk.ctx.sys.auth.pub.service.WorkPlaceManagerPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SearchEmployeeServiceImpl.
 */
@Stateless
public class SearchEmployeeServiceImpl implements SearchEmployeeService {

	/** The emp data mng repo. */
	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	/** The aff com hist repo. */
	@Inject
	private AffCompanyHistRepository affComHistRepo;

	/** The person repo. */
	@Inject
	private PersonRepository personRepo;

	/** The role pub service. */
	@Inject
	private RolePubService rolePubService;

	/** The work place manager pub. */
	@Inject
	private WorkPlaceManagerPub workPlaceManagerPub;

	/** The aff workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;

	/** The aff workplace history item repository. */
	@Inject
	private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepository;

	/** The workplace info repo. */
	@Inject
	private WorkplaceInfoRepository workplaceInfoRepo;

	/** The workplace config info repo. */
	@Inject
	private WorkplaceConfigInfoRepository workplaceConfigInfoRepo;

	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.employee.service.SearchEmployeeService#
	 * searchByCode(nts.uk.ctx.bs.employee.dom.employee.service.dto.
	 * EmployeeSearchDto)
	 */
	@Override
	public EmployeeSearchData searchByCode(EmployeeSearchDto dto) {
		String cid = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		return this.getEmployeeFromEmployeeCode(cid, baseDate, dto);
	}

	/**
	 * Gets the employee from employee code.
	 *
	 * @param cid
	 *            the cid
	 * @param baseDate
	 *            the base date
	 * @param dto
	 *            the dto
	 * @return the employee from employee code
	 */
	// アルゴリズム「社員コードから社員を取得する」を実行する
	private EmployeeSearchData getEmployeeFromEmployeeCode(String cid, GeneralDate baseDate, EmployeeSearchDto dto) {
		Optional<EmployeeInforDto> optEmBaseInfo = this.getEmployeeBaseInfor(cid, dto.getEmployeeCode());
		if (optEmBaseInfo.isPresent()) {
			if (dto.getSystem().equals(System.Employment.code)) {
				Boolean canRef = this.canReference(baseDate, optEmBaseInfo.get().getEmployeeId(), dto.getSystem());
				if (canRef) {
					PersonalBasicInfo perInfo = this.getPersonalInfo(optEmBaseInfo.get().getEmployeeId());
					if (perInfo.getEntryDate().beforeOrEquals(GeneralDate.today())
							&& perInfo.getRetiredDate().afterOrEquals(GeneralDate.today())) {
						WorkplaceInfo wkpInfo = this.getWplBelongEmployee(perInfo.getEmployeeId(), baseDate);
						return EmployeeSearchData.builder().companyId(cid).employeeId(perInfo.getEmployeeId())
								.employeeCode(dto.getEmployeeCode()).personalId(perInfo.getPid())
								.businessName(perInfo.getBusinessName()).deptDisplayName("")
								.wkpDisplayName(wkpInfo.getWkpDisplayName().v()).build();
					} else {
						throw new BusinessException("Msg_7");
					}
				}
			} else {
				// 部門はまだ対象外（17/12/1 武藤）
				throw new BusinessException("Msg_7");
			}
		} else {
			throw new BusinessException("Msg_7");
		}
		throw new BusinessException("Msg_7");
	}

	/**
	 * Gets the personal info.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the personal info
	 */
	// アルゴリズム「社員IDから個人社員基本情報を取得」を実行する
	private PersonalBasicInfo getPersonalInfo(String employeeId) {
		Optional<EmployeeDataMngInfo> empOpt = empDataMngRepo.findByEmpId(employeeId);
		PersonalBasicInfo perResult = null;

		if (empOpt.isPresent()) {
			EmployeeDataMngInfo empData = empOpt.get();
			perResult = new PersonalBasicInfo();

			setEmployeeInfo(empData, perResult);

			setPersonInfo(empData.getPersonId(), perResult);

		}
		return perResult;
	}

	/**
	 * Sets the person info.
	 *
	 * @param pId
	 *            the id
	 * @param perResult
	 *            the per result
	 */
	private void setPersonInfo(String pId, PersonalBasicInfo perResult) {
		Optional<Person> _person = personRepo.getByPersonId(pId);
		if (_person.isPresent()) {
			Person person = _person.get();
			perResult.setBirthDay(person.getBirthDate());
			perResult.setPid(person.getPersonId());
			perResult.setGender(person.getGender().value);
			perResult.setBusinessName(person.getPersonNameGroup().getBusinessName() == null ? ""
					: person.getPersonNameGroup().getBusinessName().v());
		}
	}

	/**
	 * Sets the employee info.
	 *
	 * @param employee
	 *            the employee
	 * @param perResult
	 *            the per result
	 */
	private void setEmployeeInfo(EmployeeDataMngInfo employee, PersonalBasicInfo perResult) {
		perResult.setEmployeeId(employee.getEmployeeId());
		perResult.setEmployeeCode(employee.getEmployeeCode() == null ? "" : employee.getEmployeeCode().v());

		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployeeDesc(employee.getCompanyId(),
				employee.getEmployeeId());

		AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(employee.getEmployeeId());

		if (affComHistByEmp != null) {

			AffCompanyHistItem affComHistItem = new AffCompanyHistItem();

			Optional<AffCompanyHistItem> history = affComHistByEmp.getHistory();

			if (history.isPresent()) {
				affComHistItem = history.get();
				perResult.setEntryDate(affComHistItem.start());
				perResult.setRetiredDate(affComHistItem.end());

			}
		}
	}

	/**
	 * Can reference.
	 *
	 * @param baseDate
	 *            the base date
	 * @param employeeId
	 *            the employee id
	 * @param system
	 *            the system
	 * @return the boolean
	 */
	// アルゴリズム「参照可能な社員かを判定する（職場）」を実行する
	private Boolean canReference(GeneralDate baseDate, String employeeId, String system) {
		String userId = AppContexts.user().userId();
		String loginEmployeeId = AppContexts.user().employeeId();
		EmployeeReferenceRangePub emReference = this.getEmployeeRefrenceRange(userId, system, baseDate);
		if (emReference.equals(EmployeeReferenceRangePub.ONLY_MYSELF)) {
			return employeeId.equals(loginEmployeeId) ? true : false;
		}
		List<String> lstWkp = this.getCanReferenceWorkplaceLst(employeeId, baseDate, emReference, null);
		String wkpId = this.getWplBelongEmployee(employeeId, baseDate).getWorkplaceId();
		return lstWkp.stream().anyMatch(item -> item.equals(wkpId));
	}

	/**
	 * Gets the can reference workplace lst.
	 *
	 * @param sid
	 *            the sid
	 * @param baseDate
	 *            the base date
	 * @param emReference
	 *            the em reference
	 * @param includeWkpManage
	 *            the include wkp manage
	 * @return the can reference workplace lst
	 */
	// 参照可能な職場リストを取得する
	private List<String> getCanReferenceWorkplaceLst(String sid, GeneralDate baseDate,
			EmployeeReferenceRangePub emReference, Boolean includeWkpManage) {
		List<String> listWorkPlaceId = new ArrayList<>();
		String employeeId = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		if (emReference.equals(EmployeeReferenceRangePub.ALL_EMPLOYEE)) {
			// imported（権限管理）「職場」を取得する
			listWorkPlaceId.addAll(workplaceInfoRepository.findAll(cid, baseDate).stream()
					.map(item -> item.getWorkplaceId()).collect(Collectors.toList()));
		} else {
			if (Objects.isNull(includeWkpManage) || includeWkpManage) {
				List<String> listWorkplaceMng = workPlaceManagerPub.getWkpManagerByEmpIdAndBaseDate(employeeId,
						baseDate);
				String wkpIdAffHist = this.getWplBelongEmployee(employeeId, baseDate).getWorkplaceId();
				if (emReference.equals(EmployeeReferenceRangePub.DEPARTMENT_AND_CHILD)) {
					List<String> childWkpId = this.getChildWkpIds(cid, wkpIdAffHist, baseDate);
					listWorkPlaceId.addAll(childWkpId);
				}
				listWorkPlaceId.addAll(listWorkplaceMng);
				listWorkPlaceId.add(wkpIdAffHist);
			}
		}
		return listWorkPlaceId;
	}

	/**
	 * Gets the child wkp ids.
	 *
	 * @param cid
	 *            the cid
	 * @param wkpIdAffHist
	 *            the wkp id aff hist
	 * @param baseDate
	 *            the base date
	 * @return the child wkp ids
	 */
	// 配下の職場をすべて取得する
	private List<String> getChildWkpIds(String cid, String wkpIdAffHist, GeneralDate baseDate) {
		Optional<WorkplaceConfigInfo> wkpConfigInfo = workplaceConfigInfoRepo.findAllByParentWkpId(cid, baseDate,
				wkpIdAffHist);
		if (!wkpConfigInfo.isPresent()) {
			return Collections.emptyList();
		}
		List<WorkplaceHierarchy> listWkpHierachy = wkpConfigInfo.get().getLstWkpHierarchy();

		return listWkpHierachy.stream().map(wkpHierachy -> wkpHierachy.getWorkplaceId()).collect(Collectors.toList());
	}

	// imported（権限管理）「所属職場履歴」を取得する
	/**
	 * Gets the wpl belong employee.
	 *
	 * @param sid
	 *            the sid
	 * @param baseDate
	 *            the base date
	 * @return the wpl belong employee
	 */
	// No.30
	private WorkplaceInfo getWplBelongEmployee(String sid, GeneralDate baseDate) {
		// get AffWorkplaceHistory
		Optional<AffWorkplaceHistory> affWrkPlc = affWorkplaceHistoryRepository.getByEmpIdAndStandDate(sid, baseDate);
		if (!affWrkPlc.isPresent())
			return null;

		// get AffWorkplaceHistoryItem
		String historyId = affWrkPlc.get().getHistoryItems().get(0).identifier();
		Optional<AffWorkplaceHistoryItem> affWrkPlcItem = affWorkplaceHistoryItemRepository.getByHistId(historyId);
		if (!affWrkPlcItem.isPresent())
			return null;

		// Get workplace info.
		Optional<WorkplaceInfo> optWorkplaceInfo = workplaceInfoRepo.findByWkpId(affWrkPlcItem.get().getWorkplaceId(),
				baseDate);

		// Check exist
		if (!optWorkplaceInfo.isPresent()) {
			return null;
		}

		// Return workplace id
		WorkplaceInfo wkpInfo = optWorkplaceInfo.get();
		return wkpInfo;
	}

	/**
	 * Gets the employee refrence range.
	 *
	 * @param userId
	 *            the user id
	 * @param system
	 *            the system
	 * @param baseDate
	 *            the base date
	 * @return the employee refrence range
	 */
	// アルゴリズム「社員参照範囲を取得する」を実行する
	private EmployeeReferenceRangePub getEmployeeRefrenceRange(String userId, String system, GeneralDate baseDate) {
		EmployeeReferenceRangePub ref = rolePubService.getEmployeeReferenceRange(userId,
				this.convertSystemToRoleType(system), baseDate);
		return ref;
	}

	/**
	 * Convert system to role type.
	 *
	 * @param system
	 *            the system
	 * @return the integer
	 */
	private Integer convertSystemToRoleType(String system) {
		switch (system) {
		case "emp":
			return RoleTypeImport.EMPLOYMENT.value;
		case "sal":
			return RoleTypeImport.SALARY.value;
		case "hrm":
			return RoleTypeImport.HUMAN_RESOURCE.value;
		case "per":
			return RoleTypeImport.PERSONAL_INFO.value;
		case "off":
			return RoleTypeImport.OFFICE_HELPER.value;
		case "myn":
			return RoleTypeImport.MY_NUMBER.value;
		default:
			return null;
		}
	}

	// アルゴリズム「「会社ID」「社員コード」より社員基本情報を取得」を実行する
	/**
	 * Gets the employee base infor.
	 *
	 * @param cid
	 *            the cid
	 * @param employeeCode
	 *            the employee code
	 * @return the employee base infor
	 */
	// No.18
	private Optional<EmployeeInforDto> getEmployeeBaseInfor(String cid, String employeeCode) {
		Optional<EmployeeDataMngInfo> empInfo = empDataMngRepo.getEmployeeByCidScd(cid, employeeCode);

		if (!empInfo.isPresent() || !empInfo.get().getDeletedStatus().equals(EmployeeDeletionAttr.NOTDELETED)) {
			return Optional.empty();
		} else {
			EmployeeDataMngInfo emp = empInfo.get();

			Optional<Person> person = personRepo.getByPersonId(emp.getPersonId());

			EmployeeInforDto result = new EmployeeInforDto(emp.getCompanyId(),
					emp.getEmployeeCode() == null ? null : emp.getEmployeeCode().v(), emp.getEmployeeId(),
					emp.getPersonId(),
					(person.isPresent() && person.get().getPersonNameGroup().getBusinessName() != null)
							? person.get().getPersonNameGroup().getPersonName().getFullName().v() : null);
			return Optional.of(result);
		}
	}
}
