package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import find.person.info.item.SetItemDto;
import lombok.val;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefinitionFactory;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmpCtgDomainServices;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.ParamForGetItemDef;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
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
	PerInfoItemDefFinder perInfoItemDefFinder;
	
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
		// String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";

		// get list ctg
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

	public LayoutPersonInfoClsDto getListInfoCtgByCtgIdAndSid(String empSelected, String ctgId) {
		// String ctgIDTest = "2e164ec3-0002-4a4d-9266-498a46dd86d4";
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		// String loginEmpId = AppContexts.user().employeeId();
		String loginEmpId = "99900000-0000-0000-0000-000000000001";
		String roleId = AppContexts.user().roles().forPersonnel();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";

		LayoutPersonInfoClsDto personInfoClsDto = new LayoutPersonInfoClsDto();

		PersonInfoCategory perCtgInfo = perInfoCtgRepositoty
				.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();

		// check Type của Category
		if (perCtgInfo.getCategoryType() == CategoryType.SINGLEINFO) {

			// todo getDataSingle

			return null;
		} else {
			List<PerInfoItemDefDto> lstPerInfoItemDef = getListPerItemDef(
					new ParamForGetItemDef(perCtgInfo, roleIdOfLogin == null ? "" : roleIdOfLogin, companyId,
							contractCode, loginEmpId.equals(empSelected)));
			
			personInfoClsDto.setListItemDf(lstPerInfoItemDef);
			

			if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.PERSON) {// person
				// Get persinId
				String personId = employeeRepository.findBySid(companyId, empSelected).get().getPId();
				
				if (perCtgInfo.getIsFixed() == IsFixed.FIXED) {// fixed
					

				} else {// optional

				}
			} else {// employee
				if (perCtgInfo.getIsFixed() == IsFixed.FIXED) {// fixed

				} else {// optional

				}
			}
		}

		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(),
				AppContexts.user().contractCode());
		// return CategoryPersonInfoClsDto.createObjectFromDomain(perCtgInfo,
		// lstPerItemDef);
		return null;
	}

	/**
	 * get person information item definition
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	public List<PerInfoItemDefDto> getListPerItemDef(ParamForGetItemDef paramObject) {

		List<PerInfoItemDefDto> lstResult = new ArrayList<>();

		PerInfoItemDefDto infoItemDefDto = null;

		if (paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.MULTIINFO) {
			lstResult = getPerInfoItemDefWithMultiple(paramObject);
			infoItemDefDto = getPerInfoItemDefWithMultiple(paramObject).get(0);
		} else {
			infoItemDefDto = getPerInfoItemDefWithHistory(paramObject);
		}

		if (infoItemDefDto.getItemTypeState().getItemType() == ItemType.SET_ITEM.value) {

			// get itemId list of children
			SetItemDto setItemDto = (SetItemDto) infoItemDefDto.getItemTypeState();
			// get children by itemId list
			lstResult = perInfoItemDefFinder.getPerInfoItemDefByListId(setItemDto.getItems());
		}
		lstResult.add(infoItemDefDto);
		return lstResult;
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
		lstPerInfoDef.stream().filter(x -> {
			return paramObject.isSelfAuth()
					? personInfoItemAuthRepository.getItemDetai(paramObject.getRoleId(), x.getPerInfoCtgId(), x.getId())
							.get().getSelfAuth() != PersonInfoAuthType.HIDE
					: personInfoItemAuthRepository.getItemDetai(paramObject.getRoleId(), x.getPerInfoCtgId(), x.getId())
							.get().getOtherAuth() != PersonInfoAuthType.HIDE;
		}).collect(Collectors.toList());

		return lstPerInfoDef;
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
		return perInfoItemDefFinder.getPerInfoItemDefByItemDefId(dateRangeItem.getDateRangeItemId());
	}

	/**
	 * 
	 * @param PersonInfoCategory perInfoCtg
	 * @param id : personId || employeeId
	 * @return List<PerInfoItemDefDto> lstPerInfoItemDefDto
	 * 
	 */
	private void getListPersonCtgItemFix(String id, PersonInfoCategory perInfoCtg,
			LayoutPersonInfoClsDto personInfoClsDto) {

		List<CategoryItemFixDto> listCtgItemFixDto = new ArrayList<CategoryItemFixDto>();

		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			//PERSON
			
			switch (perInfoCtg.getCategoryCode().v()) {
			case "CS00003":
				// get list currentaddress
				// id == personid
				List<CurrentAddress> listCurrentAdd = currentAddressRepo.getListByPid(id);
				Map<String, List<LayoutPersonInfoValueDto>> ecMapFixedData = ItemDefinitionFactory
						.matchCurrentAddress(personInfoClsDto, listCurrentAdd);
				
				Map<String, List<LayoutPersonInfoValueDto>> ecMapOptionData = getPersDataOptionalForListClsItem(
						perInfoCtg.getCategoryCode().v(), personInfoClsDto, id);
				
				personInfoClsDto.setItems(mapFixDataWithOptionData(ecMapFixedData, ecMapOptionData));
				break;
				
			case "CS00004":
				// get list family
				List<Family> lstFamily = familyRepo.getListByPid(id);
				Map<String, List<LayoutPersonInfoValueDto>> fMapFixedData = ItemDefinitionFactory
						.matchFamilies(personInfoClsDto, lstFamily);
				
				Map<String, List<LayoutPersonInfoValueDto>> fMapOptionData = getPersDataOptionalForListClsItem(
						perInfoCtg.getCategoryCode().v(), personInfoClsDto, id);
				
				personInfoClsDto.setItems(mapFixDataWithOptionData(fMapFixedData, fMapOptionData));

				break;
			case "CS00014":
				// get list widowHistory 
				// Domain WidowHistory chuwa xong ( chua co pid)
				 List<WidowHistory> listWidowHistory =  widowHistoryRepo.getbyPid(id);
				 Map<String, List<LayoutPersonInfoValueDto>> whMapFixedData = ItemDefinitionFactory
							.matchWidowHistory(personInfoClsDto, listWidowHistory);
					Map<String, List<LayoutPersonInfoValueDto>> whMapOptionData = getPersDataOptionalForListClsItem(
							perInfoCtg.getCategoryCode().v(), personInfoClsDto, id);
					
					personInfoClsDto.setItems(mapFixDataWithOptionData(whMapFixedData, whMapOptionData));

				break;
			case "CS00015":
				// get list EmergencyContact
				List<PersonEmergencyContact> lstEmergencyCt = personEmergencyCtRepo.getListbyPid(id);
				Map<String, List<LayoutPersonInfoValueDto>> emMapFixedData = ItemDefinitionFactory
						.matchPersEmerConts(personInfoClsDto, lstEmergencyCt);
				Map<String, List<LayoutPersonInfoValueDto>> emMapOptionData = getPersDataOptionalForListClsItem(
						perInfoCtg.getCategoryCode().v(), personInfoClsDto, id);
				personInfoClsDto.setItems(mapFixDataWithOptionData(emMapFixedData, emMapOptionData));
				break;

			}

		} else {
			// EMPLOYEE
			
			switch (perInfoCtg.getCategoryCode().v()) {
			case "CS00008":
				// get list TemporaryAbsence 休職休業
				List<TemporaryAbsence> listTemporaryAbsence = temporaryAbsenceRepo.getListBySid(id);

				break;
			case "CS00009":
				// get List JobTitleMain
				List<JobTitleMain> listJobTitleMain = jobTitleMainRepo.getListBiSid(id);

				break;
			case "CS00010":
				// get list AffWorkplace

				break;
			case "CS00011":
				//

				break;
			case "CS00012":
				//

				break;

			}

		}

	}
	
	private List<Object> mapFixDataWithOptionData(Map<String, List<LayoutPersonInfoValueDto>> mapFixData,
			Map<String, List<LayoutPersonInfoValueDto>> mapOptionData) {
		List<Object> resultList = new ArrayList<Object>();
		mapFixData.forEach((domainId, fixDataList) -> {
			List<LayoutPersonInfoValueDto> optionDataList = mapOptionData.get(domainId);
			if (optionDataList != null) {
				List<LayoutPersonInfoValueDto> rowList = new ArrayList<>();
				rowList.addAll(fixDataList);
				rowList.addAll(optionDataList);
				resultList.add(rowList);
			}
		});
		return resultList;
	}
	
	
	private Map<String, List<LayoutPersonInfoValueDto>> getPersDataOptionalForListClsItem(String categoryCode,
			LayoutPersonInfoClsDto personInfoClsDto, String personId) {
		// ドメインモデル「個人情報カテゴリデータ」を取得する
		Map<String, List<LayoutPersonInfoValueDto>> resultMap = new HashMap<>();
		List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(personId,
				personInfoClsDto.getPersonInfoCategoryID());
		perInfoCtgDatas.forEach(perInfoCtgData -> {
			List<PersonInfoItemData> dataItems = perInItemDataRepo
					.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
			List<LayoutPersonInfoValueDto> ctgDataList = new ArrayList<>();
			for (PerInfoItemDefDto item : personInfoClsDto.getListItemDf()) {

				Optional<PersonInfoItemData> dataItemOpt = dataItems.stream()
						.filter(dataItem -> dataItem.getPerInfoItemDefId() == item.getId()).findFirst();
				if (dataItemOpt.isPresent()) {
					PersonInfoItemData data = dataItemOpt.get();
					Object value = null;
					switch (data.getDataState().getDataStateType()) {
					case String:
						value = data.getDataState().getStringValue();
						break;
					case Numeric:
						value = data.getDataState().getNumberValue().intValue();
						break;
					case Date:
						value = data.getDataState().getDateValue();
						break;
					}
					if (value != null) {
						ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, value));
					}
				} else {
					ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, null));
				}
			}
			resultMap.put(perInfoCtgData.getRecordId(), ctgDataList);
		});
		return resultMap;
	}

}
