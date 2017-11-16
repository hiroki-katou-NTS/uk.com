package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import find.person.info.item.SetItemDto;
import lombok.val;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefFactoryNew;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefinitionFactory;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmpCtgDomainServices;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.ParamForGetItemDef;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeCategoryFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PersonRepository personRepository;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private EmpCtgDomainServices empCtgDomainServices;

	@Inject
	private CurrentAddressRepository currentAddressRepo;

	@Inject
	private FamilyRepository familyRepo;

	@Inject
	private PersonEmergencyCtRepository personEmergencyCtRepo;

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepo;

	@Inject
	private JobTitleMainRepository jobTitleMainRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;

	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;

	@Inject
	private PerInfoItemDefFinder perInfoItemDefFinder;

	// inject category-data-repo
	@Inject
	private PerInfoCtgDataRepository perInCtgDataRepo;

	@Inject
	private PerInfoItemDataRepository perInItemDataRepo;

	@Inject
	private WidowHistoryRepository widowHistoryRepo;

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
	public List<List<LayoutPersonInfoClsDto>> getListInfoCtgByCtgIdAndSid(String empSelected, String ctgId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		// String loginEmpId = AppContexts.user().employeeId();
		// String roleIdOfLogin = AppContexts.user().roles().forPersonnel();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";
		String loginEmpId = "99900000-0000-0000-0000-000000000001";

		List<LayoutPersonInfoClsDto> listLayoutPersonInfoClsDto = new ArrayList<>();

		// Get PersonInfoCtg
		PersonInfoCategory perCtgInfo = perInfoCtgRepositoty
				.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();
		// Get list PerInfoItemDef
		List<PerInfoItemDefDto> lstPerInfoItemDef = getListPerItemDef(new ParamForGetItemDef(perCtgInfo,
				roleIdOfLogin == null ? "" : roleIdOfLogin, companyId, contractCode, loginEmpId.equals(empSelected)));

		// Khoi tao List<LayoutPersonInfoClsDto>
		List<LayoutPersonInfoClsDto> listClsDto = getListItemDf(lstPerInfoItemDef);

		// check person || employee
		if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.PERSON) {

		} else if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {

		}

		if (perCtgInfo.getIsFixed() == IsFixed.FIXED) {
			if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
				setEmployeeCtgItem(perCtgInfo, lstPerInfoItemDef, empSelected);
			else {
				String pid = employeeRepository.getBySid(empSelected).get().getPId();
				// setPersonCtgItem(perCtgInfo, lstPerInfoItemDef, empSelected, pid);
			}
		} else {
			// optional data

		}

		return null;
	}

	// check Type of Item is SET || SINGLE
	// GET List<LayoutPersonInfoClsDto>
	private List<LayoutPersonInfoClsDto> getListItemDf(List<PerInfoItemDefDto> lstPerInfoItemDef) {

		List<LayoutPersonInfoClsDto> lstResult = new ArrayList<>();

		for (int i = 0; i < lstPerInfoItemDef.size(); i++) {
			LayoutPersonInfoClsDto clsDto = new LayoutPersonInfoClsDto();
			if (lstPerInfoItemDef.get(i).getItemTypeState().getItemType() == ItemType.SET_ITEM.value) {
				// get itemId list of children
				SetItemDto setItem = (SetItemDto) lstPerInfoItemDef.get(i).getItemTypeState();
				// get children by itemId list
				List<PerInfoItemDefDto> lstItemDef = perInfoItemDefFinder
						.getPerInfoItemDefByListIdForLayout(setItem.getItems());
				clsDto.setListItemDf(lstItemDef);
				lstItemDef.add(lstPerInfoItemDef.get(i));

			} else {
				List<PerInfoItemDefDto> lstItemDef = new ArrayList<>();
				lstItemDef.add(lstPerInfoItemDef.get(i));
				clsDto.setListItemDf(lstItemDef);
			}
			lstResult.add(clsDto);
		}

		return lstResult;
	}

	// Set Data EmployeeCtgItem
	private void setEmployeeCtgItem(PersonInfoCategory perInfoCtg, List<PerInfoItemDefDto> lstPerInfoItemDef,
			String employeeId) {
		// TODO Auto-generated method stub

		LayoutPersonInfoClsDto lstLayoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00008":
			// Case Ctg == TemporaryAbsence
			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				lstLayoutPersonInfoClsDto.setListItemDf(itemSet);
				// get list TemporaryAbsence 休職休業
				List<TemporaryAbsence> listTemporaryAbsence = temporaryAbsenceRepo.getListBySid(employeeId);
				ActionRole actionRole = getActionRole(employeeId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping
				ItemDefinitionFactory.matchTemporaryAbsence(lstLayoutPersonInfoClsDto, actionRole,
						listTemporaryAbsence);
			}
			break;

		case "CS00009":
			// Case Ctg == JobTitleMain

			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				lstLayoutPersonInfoClsDto.setListItemDf(itemSet);
				// get List JobTitleMain
				List<JobTitleMain> listJobTitleMain = jobTitleMainRepo.getListBiSid(employeeId);
				ActionRole actionRole = getActionRole(employeeId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping
				ItemDefinitionFactory.matchJobTitleMain(lstLayoutPersonInfoClsDto, actionRole, listJobTitleMain);
			}
			break;
		case "CS00014":
			// Case Ctg == AffWorkplace
			// get list AffWorkplac
			break;
		case "CS00015":
			// Case Ctg ==

			break;

		}
	}

	// check Item Type is SET or SINGLE
	// Get List ItemDefDto
	private List<PerInfoItemDefDto> getPerItemSet(PerInfoItemDefDto itemDf) {

		List<PerInfoItemDefDto> lstResult = new ArrayList<>();

		if (itemDf.getItemTypeState().getItemType() == ItemType.SET_ITEM.value) {
			// get itemId list of children
			SetItemDto setItem = (SetItemDto) itemDf.getItemTypeState();
			// get children by itemId list
			lstResult = perInfoItemDefFinder.getPerInfoItemDefByListIdForLayout(setItem.getItems());
		}
		lstResult.add(itemDf);
		return lstResult;
	}

	// Set Data PersonCtgItem
	private List<List<LayoutPersonInfoClsDto>> setPersonCtgItem(PersonInfoCategory perInfoCtg,
			List<LayoutPersonInfoClsDto> listClsDto, String employeeId, String personId) {

		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {

			List<Object> lstLayoutPersonInfoClsDto = new ArrayList<>();
			switch (perInfoCtg.getCategoryCode().v()) {


			/*
			 * case "CS00003": // case Ctg == CurrentAddress
			 * 
			 * // get list CurrentAddress List<CurrentAddress> listCurrentAdd =
			 * currentAddressRepo.getListByPid(personId); ActionRole actionRole =
			 * getActionRole(employeeId, perInfoCtg.getPersonInfoCategoryId(),
			 * item.getId()); // mapping
			 * ItemDefinitionFactory.matchCurrentAddress(listClsDto, actionRole,
			 * listCurrentAdd);
			 * break;
			 * 
			 * case "CS00004": // Case Ctg = Family for (PerInfoItemDefDto item :
			 * lstPerInfoItemDef) { List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
			 * lstLayoutPersonInfoClsDto.setListItemDf(itemSet); // get list family
			 * List<Family> lstFamily = familyRepo.getListByPid(personId); ActionRole
			 * actionRole = getActionRole(employeeId, perInfoCtg.getPersonInfoCategoryId(),
			 * item.getId()); // mapping
			 * ItemDefinitionFactory.matchFamily(lstLayoutPersonInfoClsDto, actionRole,
			 * lstFamily); } break; case "CS00014": // Case Ctg == WidowHistory for
			 * (PerInfoItemDefDto item : lstPerInfoItemDef) { List<PerInfoItemDefDto>
			 * itemSet = getPerItemSet(item);
			 * lstLayoutPersonInfoClsDto.setListItemDf(itemSet); // get list CurrentAddress
			 * List<WidowHistory> listWidowHistory = widowHistoryRepo.getbyPid(personId);
			 * ActionRole actionRole = getActionRole(employeeId,
			 * perInfoCtg.getPersonInfoCategoryId(), item.getId()); // mapping
			 * ItemDefinitionFactory.matchWidowHistory(lstLayoutPersonInfoClsDto,
			 * actionRole, listWidowHistory); }
			 * 
			 * break; case "CS00015": // Case Ctg == EmergencyContact for (PerInfoItemDefDto
			 * item : lstPerInfoItemDef) { // get list CurrentAddress
			 * List<PersonEmergencyContact> lstEmergencyCt =
			 * personEmergencyCtRepo.getListbyPid(personId); ActionRole actionRole =
			 * getActionRole(employeeId, perInfoCtg.getPersonInfoCategoryId(),
			 * item.getId()); // mapping
			 * ItemDefinitionFactory.matchPersonEmergencyContact(lstLayoutPersonInfoClsDto,
			 * actionRole, lstEmergencyCt); } break;
			 */

			}
		} else {

		}
		return null;

	}

	/**
	 * get person information item definition
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	public List<PerInfoItemDefDto> getListPerItemDef(ParamForGetItemDef paramObject) {
		if (paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.MULTIINFO
				|| paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.SINGLEINFO) {
			return getPerInfoItemDefWithMultiple(paramObject);
		} else {
			return getPerInfoItemDefWithHistory(paramObject);
		}
	}

	/**
	 * Get PersonInfoItemDef Case CategoryType == Single
	 * 
	 * @param employeeId
	 * @param ctgId
	 * @return
	 */
	private CategoryPersonInfoClsDto getDataSingle(String employeeId, String ctgId) {
		Employee emp = employeeRepository.getBySid(employeeId).get();
		// todo
		return null;

	}

	/**
	 * get list PersonInfoItemDef Case CategoryType == MULTIINFO
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	public List<PerInfoItemDefDto> getPerInfoItemDefWithMultiple(ParamForGetItemDef paramObject) {
		// get per info item def with order
		List<PerInfoItemDefDto> lstPerInfoDef = perInfoItemDefFinder
				.getAllPerInfoItemDefByCatgoryId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId());

		// filter by auth return
		/*
		 * lstPerInfoDef.stream().filter(x -> { return paramObject.isSelfAuth() ?
		 * personInfoItemAuthRepository.getItemDetai(paramObject.getRoleId(),
		 * x.getPerInfoCtgId(), x.getId()) .get().getSelfAuth() !=
		 * PersonInfoAuthType.HIDE :
		 * personInfoItemAuthRepository.getItemDetai(paramObject.getRoleId(),
		 * x.getPerInfoCtgId(), x.getId()) .get().getOtherAuth() !=
		 * PersonInfoAuthType.HIDE; }).collect(Collectors.toList());
		 */

		return lstPerInfoDef;
	}

	/**
	 * get PersonInfoItemDef case CategoryType == History
	 * 
	 * @param dateRangeItemId
	 * @return PersonInfoItemDefinition
	 */
	private List<PerInfoItemDefDto> getPerInfoItemDefWithHistory(ParamForGetItemDef paramObject) {
		DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
				.getDateRangeItemByCategoryId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId());
		PerInfoItemDefDto perInfoItemDefDto = perInfoItemDefFinder
				.getPerInfoItemDefByItemDefId(dateRangeItem.getDateRangeItemId());
		List<PerInfoItemDefDto> listRult = new ArrayList<>();
		listRult.add(perInfoItemDefDto);
		return listRult;
	}

	// get Action Role
	private ActionRole getActionRole(String empId, String ctgId, String perInfoItemId) {
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		boolean isSelfAuth = empId.equals(loginEmpId);
		if (isSelfAuth)
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getSelfAuth() != PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
		else
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getOtherAuth() != PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
	}

}
