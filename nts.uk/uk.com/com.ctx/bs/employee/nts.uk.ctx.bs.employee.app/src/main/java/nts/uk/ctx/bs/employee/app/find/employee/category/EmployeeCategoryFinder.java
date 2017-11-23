package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import find.person.info.item.SetItemDto;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefFactoryNew;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.ParamForGetItemDef;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMember;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMemberRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeCategoryFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;

	@Inject
	private PerInfoItemDefFinder perInfoItemDefFinder;

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;

	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;

	@Inject
	private JobTitleMainRepository jobTitleMainRepository;

	@Inject
	private AssignedWrkplcRepository assignedWrkplcRepository;

	@Inject
	private AffDepartmentRepository affDepartmentRepository;

	@Inject
	private CurrentAddressRepository currentAddressRepository;

	@Inject
	private FamilyMemberRepository familyRepository;

	@Inject
	private WidowHistoryRepository widowHistoryRepository;

	@Inject
	private PersonEmergencyCtRepository personEmergencyCtRepository;

	public List<CategoryPersonInfoClsDto> getAllPerInfoCtg(String companyId, String employeeIdSelected) {
		String empIdCurrentLogin = AppContexts.user().employeeId();
		// get roleIdOfLogin from app context;
		// String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";
		String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();

		// get list Category
		List<CategoryPersonInfoClsDto> listCategory = employeeRepository.getAllPerInfoCtg(companyId).stream().map(p -> {
			return new CategoryPersonInfoClsDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
					p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
					p.getCategoryType().value, p.getIsFixed().value);
		}).collect(Collectors.toList());

		if (employeeIdSelected == empIdCurrentLogin) {
			// get List Ctg theo quyền
			// todo
		} else {
			// get List Ctg theo quyền
			// todo
		}

		for (int i = 0; i < listCategory.size(); i++) {

		}
		return listCategory;
	};

	// Get List Infomation Of Category
	public List<EmpMaintLayoutDto> getListInfoCtgByCtgIdAndSid(String empSelected, String ctgId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		// String loginEmpId = AppContexts.user().employeeId();
		// String roleIdOfLogin = AppContexts.user().roles().forPersonnel();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";
		String loginEmpId = "99900000-0000-0000-0000-000000000001";
		// get Employee
		Employee employee = employeeRepository.findBySid(companyId, "90000000-0000-0000-0000-000000000001").get();

		// Get PersonInfoCtg
		PersonInfoCategory perCtgInfo = perInfoCtgRepositoty
				.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();

		// Get PerInfoItemDef
		PerInfoItemDefDto perInfoItemDef = null;

		if (perCtgInfo.getCategoryType() == CategoryType.SINGLEINFO) {
			// Case SINGLE

		} else if (perCtgInfo.getCategoryType() == CategoryType.MULTIINFO) {
			// Case MULTIFO
			// Get item đầu tiên trong list
			perInfoItemDef = getPerInfoItemDefWithMultiple(
					new ParamForGetItemDef(perCtgInfo, roleIdOfLogin == null ? "" : roleIdOfLogin, companyId,
							contractCode, loginEmpId.equals(empSelected)));

		} else {
			// Case HISTORY
			perInfoItemDef = getPerInfoItemDefWithHistory(
					new ParamForGetItemDef(perCtgInfo, roleIdOfLogin == null ? "" : roleIdOfLogin, companyId,
							contractCode, loginEmpId.equals(empSelected)));
		}

		List<PerInfoItemDefDto> lstPerInfoItemDefDto = new ArrayList<>();
		// return list
		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto = new ArrayList<>();

		if (perInfoItemDef != null) {
			lstPerInfoItemDefDto = getListItemDf(perInfoItemDef);
			if (perCtgInfo.getIsFixed() == IsFixed.FIXED)
				// Fixed Data
				if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
					lstEmpMaintLayoutDto = getEmployeeCtgItem(perCtgInfo, lstPerInfoItemDefDto, employee);
				else
					lstEmpMaintLayoutDto = getPersonCtgItem(perCtgInfo, lstPerInfoItemDefDto, employee);
			else {
				// optional data
				if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
					lstEmpMaintLayoutDto = new ArrayList<>();
					EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
					empMaintLayoutDto.setClassificationItems(getEmpInfoCtgDataOptionDto(perCtgInfo.getPersonInfoCategoryId(), employee.getSId()));
					lstEmpMaintLayoutDto.add(empMaintLayoutDto);

				} else {
					// Person
					lstEmpMaintLayoutDto = new ArrayList<>();
					EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
					empMaintLayoutDto.setClassificationItems(getPerInfoCtgDataOptionDto(perCtgInfo.getPersonInfoCategoryId(), employee.getPId()));
					lstEmpMaintLayoutDto.add(empMaintLayoutDto);
				}

			}
		}

		return lstEmpMaintLayoutDto;
	}

	// check Type of Item is SET || SINGLE
	// GET List<PerInfoItemDefDto>
	private List<PerInfoItemDefDto> getListItemDf(PerInfoItemDefDto perInfoItemDef) {
		List<PerInfoItemDefDto> lstResult = new ArrayList<>();
		if (perInfoItemDef.getItemTypeState().getItemType() == ItemType.SET_ITEM.value) {
			// get itemId list of children
			SetItemDto setItem = (SetItemDto) perInfoItemDef.getItemTypeState();
			// get children by itemId list
			lstResult = perInfoItemDefFinder.getPerInfoItemDefByListIdForLayout(setItem.getItems());
			lstResult.add(perInfoItemDef);

		} else {
			lstResult.add(perInfoItemDef);
		}
		return lstResult;
	}

	private List<EmpMaintLayoutDto> getPersonCtgItem(PersonInfoCategory perInfoCtg,
			List<PerInfoItemDefDto> lstPerInfoItemDef, Employee employee) {

		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto = new ArrayList<>();
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		EmpMaintLayoutDto empMaintLayoutDto;
		switch (perInfoCtg.getCategoryCode().v()) {

		// current address
		case "CS00003":
			List<CurrentAddress> currentAddress = currentAddressRepository.getListByPid(employee.getPId());
			// get Action Role
			ActionRole actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (CurrentAddress item : currentAddress) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRole, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			// set optional data
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getPerInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getPId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			break;
		case "CS00004":
			List<FamilyMember> familys = familyRepository.getListByPid(employee.getPId());
			// get Action Role
			ActionRole actionRolef = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (FamilyMember item : familys) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRolef, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getPerInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getPId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;

		case "CS00014":
			List<WidowHistory> widowHistory = widowHistoryRepository.getbyPid(employee.getPId());
			// get Action Role
			ActionRole actionRoleWh = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (WidowHistory item : widowHistory) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRoleWh, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getPerInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getPId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;

		case "CS00015":
			List<PersonEmergencyContact> personEmergencyContact = personEmergencyCtRepository
					.getListbyPid(employee.getPId());
			// get Action Role
			ActionRole actionRolePec = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (PersonEmergencyContact item : personEmergencyContact) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRolePec, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getPerInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getPId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		}
		return lstEmpMaintLayoutDto;
	}

	// Set Data EmployeeCtgItem
	private List<EmpMaintLayoutDto> getEmployeeCtgItem(PersonInfoCategory perInfoCtg,
			List<PerInfoItemDefDto> lstPerInfoItemDef, Employee employee) {

		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto = new ArrayList<>();
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		EmpMaintLayoutDto empMaintLayoutDto;
		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00008":
			// Case Ctg == TemporaryAbsence
			List<TempAbsenceHisItem> listTemporaryAbsence = temporaryAbsenceRepo.getListBySid(employee.getSId());
			// get Action Role
			ActionRole actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (TempAbsenceHisItem item : listTemporaryAbsence) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRole, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getEmpInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getSId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			break;
		case "CS00009":
			List<JobTitleMain> jobTitleMain = jobTitleMainRepository.getListBiSid(employee.getSId());
			ActionRole actionRoleJtm = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (JobTitleMain item : jobTitleMain) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRoleJtm, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getEmpInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getSId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			break;

		case "CS00010":
			// --Having dateHistItem list
			List<AssignedWorkplace> assignedWorkplace = assignedWrkplcRepository.getListBySId(employee.getSId());
			ActionRole actionRoleAsp = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (AssignedWorkplace item : assignedWorkplace) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRoleAsp, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getEmpInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getSId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;

		case "CS00011":
			// --Having dateHistItem list
			List<AffiliationDepartment> affiliationDepartment = affDepartmentRepository.getBySId(employee.getSId());
			ActionRole actionRoleAdf = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(),
					lstPerInfoItemDef.get(lstPerInfoItemDef.size() - 1).getId());
			for (AffiliationDepartment item : affiliationDepartment) {
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						lstPerInfoItemDef, actionRoleAdf, item);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);

			// set optional data 
			lstEmpMaintLayoutDto = new ArrayList<>();
			empMaintLayoutDto.setClassificationItems(getEmpInfoCtgDataOptionDto(perInfoCtg.getPersonInfoCategoryId(), employee.getSId()));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		}
		return lstEmpMaintLayoutDto;
	}

	/**
	 * get EmployeeInfoCategoryData
	 * 
	 * @param ctgId
	 * @param empId
	 */
	private List<LayoutPersonInfoClsDto> getEmpInfoCtgDataOptionDto(String ctgId, String empId) {
		List<EmpInfoItemData> lstCtgItemOptionalDto = empInfoItemDataRepository.getAllInfoItemBySidCtgId(ctgId, empId);
		List<LayoutPersonInfoClsDto> lstObj = new ArrayList<>();
		List<Object> items = new ArrayList<>();
		for (int i = 0; i < lstCtgItemOptionalDto.size(); i++) {
			LayoutPersonInfoValueDto obj = getLayoutEmpInfoValFromOptData(lstCtgItemOptionalDto.get(i), empId);
			LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
			items.add(obj);
			layoutPersonInfoClsDto.setItems(items);
		}
		return lstObj;
	}
	
	
	private LayoutPersonInfoValueDto getLayoutEmpInfoValFromOptData(EmpInfoItemData empInfoItemData, String empId) {
		Object data = null;
		PerInfoItemDefDto perInfoItemDefDto = perInfoItemDefFinder
				.getPerInfoItemDefByItemDefId(empInfoItemData.getPerInfoDefId());
		return LayoutPersonInfoValueDto.initData(empInfoItemData.getPerInfoCtgCd(), perInfoItemDefDto, data,
				getActionRole(empId, empInfoItemData.getPerInfoCtgId(), empInfoItemData.getPerInfoDefId()));
	}
	
	/**
	 * get PersonInfoCategoryData
	 * 
	 * @param ctgId
	 * @param empId
	 */
	
	private List<LayoutPersonInfoClsDto> getPerInfoCtgDataOptionDto(String ctgId, String pId) {
		List<PersonInfoItemData> lstCtgItemOptionalDto = perInfoItemDataRepository.getAllInfoItemByPidCtgId(ctgId, pId);
		List<LayoutPersonInfoClsDto> lstObj = new ArrayList<>();
		List<Object> items = new ArrayList<>();
		for (int i = 0; i < lstCtgItemOptionalDto.size(); i++) {
			LayoutPersonInfoValueDto obj = getLayoutPerInfoValFromOptData(lstCtgItemOptionalDto.get(i), pId);
			LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
			items.add(obj);
			layoutPersonInfoClsDto.setItems(items);
		}
		return lstObj;
	}
	
	private LayoutPersonInfoValueDto getLayoutPerInfoValFromOptData(PersonInfoItemData empInfoItemData, String empId) {
		Object data = null;
		PerInfoItemDefDto perInfoItemDefDto = perInfoItemDefFinder
				.getPerInfoItemDefByItemDefId(empInfoItemData.getPerInfoCtgId());
		return LayoutPersonInfoValueDto.initData(empInfoItemData.getPerInfoCtgCd(), perInfoItemDefDto, data,
				getActionRole(empId, empInfoItemData.getPerInfoCtgId(), empInfoItemData.getPerInfoItemDefId()));
	}

	/**
	 * get list PersonInfoItemDef Case CategoryType == MULTIINFO
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	public PerInfoItemDefDto getPerInfoItemDefWithMultiple(ParamForGetItemDef paramObject) {
		// get per info item def with order
		List<PerInfoItemDefDto> lstPerInfoDef = perInfoItemDefFinder
				.getAllPerInfoItemDefByCatgoryId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId());

		return lstPerInfoDef.get(0);
	}

	/**
	 * get PersonInfoItemDef case CategoryType == History
	 * 
	 * @param dateRangeItemId
	 * @return PersonInfoItemDefinition
	 */
	private PerInfoItemDefDto getPerInfoItemDefWithHistory(ParamForGetItemDef paramObject) {
		DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
				.getDateRangeItemByCategoryId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId());
		PerInfoItemDefDto perInfoItemDefDto = perInfoItemDefFinder
				.getPerInfoItemDefByItemDefId(dateRangeItem.getDateRangeItemId());
		return perInfoItemDefDto;
	}

	// get Action Role
	private ActionRole getActionRole(String empId, String ctgId, String perInfoItemId) {
		String loginEmpId = AppContexts.user().employeeId();
		// String roleId = AppContexts.user().roles().forPersonalInfo();
		// boolean isSelfAuth = empId.equals(loginEmpId);
		String roleId = "99900000-0000-0000-0000-000000000001";
		boolean isSelfAuth = true;
		if (isSelfAuth)
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getSelfAuth() != PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
		else
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getOtherAuth() != PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
	}

}
