package nts.uk.ctx.bs.employee.app.find.person.category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.person.info.item.DataTypeStateDto;
import find.person.info.item.ItemTypeStateDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.SetItemDto;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefFactoryNew;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDept;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDeptRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCare;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCareRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTax;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTaxRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsurance;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsuranceRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMain;
import nts.uk.ctx.bs.employee.dom.jobtitle.main.JobTitleMainRepository;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.employee.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.bs.person.dom.person.info.setitem.SetItem;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.bs.person.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.bs.person.dom.person.info.timepointitem.TimePointItem;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoPermissionType;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
/**
 * get person information category and it's children
 * 
 * @author xuan vinh
 *
 */
@Stateless
public class EmpPerInfoCategoryFinder {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCtgDomainService perInfoCtgDomainService;
	@Inject
	private SubJobPosRepository subJobPosRepository;

	@Inject
	private IncomeTaxRepository incomeTaxRepository;

	@Inject
	private FamilySocialInsuranceRepository familySocialInsuranceRepository;

	@Inject
	private FamilyCareRepository familyCareRepository;

	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PersonRepository personRepository;

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;

	@Inject
	private JobTitleMainRepository jobTitleMainRepository;

	@Inject
	private CurrentAddressRepository currentAddressRepository;

	@Inject
	private AssignedWrkplcRepository assignedWrkplcRepository;

	@Inject
	private CurrentAffiDeptRepository currentAffiDeptRepository;

	@Inject
	private FamilyRepository familyRepository;

	@Inject
	private WidowHistoryRepository widowHistoryRepository;

	@Inject
	private PersonEmergencyCtRepository personEmergencyCtRepository;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;
	
	@Inject 
	private PersonInfoCategoryAuthRepository personInfoCategoryAuthRepository;
	
	@Inject
	I18NResourcesForUK ukResouce;
	
	//New update: start
	/**
	 * get person information category and it's children (Hiển thị category và
	 * danh sách tab category con của nó)
	 * 
	 * @param ctgId
	 * @return list PerCtgInfo: cha va danh sach con
	 */
	public List<PersonInfoCategory> getCtgTab(String ctgId) {
		String contractCode = AppContexts.user().contractCode();
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		List<PersonInfoCategory> lstPerInfoCtg = new ArrayList<>();
		lstPerInfoCtg = perInfoCtgRepositoty
				.getPerInfoCtgByParentCdWithOrder(perInfoCtg.getCategoryParentCode().v(), contractCode, true);		
		lstPerInfoCtg.add(perInfoCtg);
		return lstPerInfoCtg;
	}
	
	/**
	 * Hiển thị nội dung của 1 Tab 
	 * @param ctgId
	 * @return List<LayoutPersonInfoClsDto> : nội dung items và data của nó
	 */
	public List<Object> getTabDetail(String employeeId, String ctgId, String infoId){
		//app context
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		
		//get Employee
		Employee employee = employeeRepository.findBySid(companyId, employeeId).get();
		//Get PersonInfoCategory
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		//get PerInfoItemDefDto
		List<PersonInfoItemDefinition> lstDomain = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, infoId,
						roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)));
		List<PerInfoItemDefDto> lstPerInfoItemDef = new ArrayList<>();
		for(int i = 0; i < lstDomain.size(); i++)
			lstPerInfoItemDef.add(fromDomain(lstDomain.get(i), i));	
		
		//return list
				List<Object> lstLayoutPerInfoClsDto = new ArrayList<>();
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED)
			if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
				lstLayoutPerInfoClsDto = getEmployeeCtgItem(perInfoCtg, lstPerInfoItemDef, employee, infoId);
			else
				lstLayoutPerInfoClsDto = getPersonCtgItem(perInfoCtg, lstPerInfoItemDef, employeeId, employee.getPId(), infoId);
		else{
			//optional data
		}		
		return lstLayoutPerInfoClsDto;
	}
	
	/**
	 * Hiển thị nội dung 1 phần trong tab(nếu trong tab có list để lựa chọn)
	 * @param employeeId
	 * @param ctgId
	 * @param subDetailId
	 * @return
	 */
	public List<Object> getTabSubDetail(String employeeId, String ctgId, String subDetailId){
		return null;
	}
	
	/**
	 * convert perInfoItemDef domain to Dto
	 * @param itemDef
	 * @param dispOrder
	 * @return
	 */
	private PerInfoItemDefDto fromDomain(PersonInfoItemDefinition itemDef, int dispOrder) {
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class,
				ukResouce);
		return new PerInfoItemDefDto(itemDef.getPerInfoItemDefId(), itemDef.getPerInfoCategoryId(),
				itemDef.getItemCode().v(), itemDef.getItemName().v(), itemDef.getIsAbolition().value,
				itemDef.getIsFixed().value, itemDef.getIsRequired().value, itemDef.getSystemRequired().value,
				itemDef.getRequireChangable().value, dispOrder, 
				itemDef.getSelectionItemRefType(),
				createItemTypeStateDto(itemDef.getItemTypeState()),
				selectionItemRefTypes);
	}
	
	/**
	 * create ItemTypeStateDto
	 * @param itemTypeState
	 * @return
	 */
	
	private ItemTypeStateDto createItemTypeStateDto(ItemTypeState itemTypeState) {
		ItemType itemType = itemTypeState.getItemType();
		if (itemType == ItemType.SINGLE_ITEM) {
			SingleItem singleItemDom = (SingleItem) itemTypeState;
			return ItemTypeStateDto.createSingleItemDto(createDataTypeStateDto(singleItemDom.getDataTypeState()));
		} else {
			SetItem setItemDom = (SetItem) itemTypeState;
			return ItemTypeStateDto.createSetItemDto(setItemDom.getItems());
		}
	}
	
	/**
	 * create DataTypeStateDto
	 * @param dataTypeState
	 * @return
	 */
	private DataTypeStateDto createDataTypeStateDto(DataTypeState dataTypeState) {
		int dataTypeValue = dataTypeState.getDataTypeValue().value;
		switch (dataTypeValue) {
		case 1:
			StringItem strItem = (StringItem) dataTypeState;
			return DataTypeStateDto.createStringItemDto(strItem.getStringItemLength().v(),
					strItem.getStringItemType().value, strItem.getStringItemDataType().value);
		case 2:
			NumericItem numItem = (NumericItem) dataTypeState;
			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin().v() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax().v() : null;
			return DataTypeStateDto.createNumericItemDto(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), numItem.getDecimalPart().v(),
					numericItemMin, numericItemMax);
		case 3:
			DateItem dItem = (DateItem) dataTypeState;
			return DataTypeStateDto.createDateItemDto(dItem.getDateItemType().value);
		case 4:
			TimeItem tItem = (TimeItem) dataTypeState;
			return DataTypeStateDto.createTimeItemDto(tItem.getMax().v(), tItem.getMin().v());
		case 5:
			TimePointItem tPointItem = (TimePointItem) dataTypeState;
			return DataTypeStateDto.createTimePointItemDto(tPointItem.getTimePointItemMin().v(),
					tPointItem.getTimePointItemMax().v());
		case 6:
			SelectionItem sItem = (SelectionItem) dataTypeState;
			return DataTypeStateDto.createSelectionItemDto(sItem.getReferenceTypeState());
		default:
			return null;
		}
	}
	//New update: end
		

	/**
	 * get person ctg infor and list of item children
	 * 
	 * @param ctgId
	 * @return EmpPerCtgInfoDto
	 */
//	public EmpPerCtgInfoDto getCtgAndItemByCtgId(String ctgId) {
//		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();
//		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(),
//				AppContexts.user().contractCode());
//		return EmpPerCtgInfoDto.createObjectFromDomain(perCtgInfo, lstPerItemDef);
//	}


	/**
	 * set category item optional
	 * 
	 * @param sId
	 * @param ctgId
	 * @return CtgItemOptionalDto
	 */
//	private void setCtgItemOptionDto(EmpPerCtgInfoDto empPerCtgInfoDto, String recordId, boolean isSingleList) {
//		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();
//		List<ItemEmpInfoItemDataDto> lstCtgItemOptionalDto = empInfoItemDataRepository
//				.getAllInfoItemByRecordId(recordId).stream()
//				.map(x -> ItemEmpInfoItemDataDto.fromDomain(x))
//				.collect(Collectors.toList());
//		if (isSingleList) {
//			ctgItemOptionalDto.setLstEmpInfoItemData(lstCtgItemOptionalDto);
//		} else {
//			lstCtgItemOptionalDto.stream().forEach(x -> ctgItemOptionalDto.addToItemEmpInfoItemDataDto(x));
//		}
//		empPerCtgInfoDto.setCtgItemOptionalDto(ctgItemOptionalDto);
//	}


	/**
	 * set category item
	 * 
	 * @param empPerCtgInfoDto
	 * @param employee
	 * @param perInfoCtg
	 * @param parentInfoId
	 */
	private List<Object> getEmployeeCtgItem(PersonInfoCategory perInfoCtg, List<PerInfoItemDefDto> lstPerInfoItemDef, Employee employee, String infoId) {
		List<Object> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		if(!checkPerInfoCtgAuth(employee.getSId(), perInfoCtg.getPersonInfoCategoryId())) return new ArrayList<>();
		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00002":
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, employee);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			//set optional data
			break;
		case "CS00008":
			// Waiting for DateHistoryItem
			Optional<TemporaryAbsence> temporaryAbsence = temporaryAbsenceRepository.getByTempAbsenceId(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						temporaryAbsence.isPresent()? temporaryAbsence.get(): new TemporaryAbsence());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
			
		case "CS00009":
			Optional<JobTitleMain> jobTitleMain = jobTitleMainRepository.getJobTitleMainById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						jobTitleMain.isPresent()? jobTitleMain.get(): new JobTitleMain());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
			
		case "CS00010":
			// Waiting for DateHistoryItem
			AssignedWorkplace assignedWorkplace = assignedWrkplcRepository.getAssignedWorkplaceById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, assignedWorkplace);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
			
		case "CS00011":
			// Waiting for DateHistoryItem
			// Affiliation Department
			break;
			
		case "CS00012":
			// Waiting for DateHistoryItem
			CurrentAffiDept currentAffiDept = currentAffiDeptRepository.getCurrentAffiDeptById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, currentAffiDept);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00005":
			Optional<IncomeTax> incomeTax = incomeTaxRepository.getIncomeTaxById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						incomeTax.isPresent()?incomeTax.get(): new IncomeTax());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00006":
			Optional<FamilySocialInsurance> familySocialInsurance = familySocialInsuranceRepository
					.getFamilySocialInsById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						familySocialInsurance.isPresent()?familySocialInsurance.get(): new FamilySocialInsurance());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00007":
			Optional<FamilyCare> familyCare = familyCareRepository.getFamilyCareById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						familyCare.isPresent()?familyCare.get(): new FamilyCare());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00013":
			List<SubJobPosition> lstSubJobPos = subJobPosRepository.getSubJobPosByDeptId(infoId);
			List<LayoutPersonInfoClsDto> subList;
			for(SubJobPosition subJobPosition : lstSubJobPos){
				subList = new ArrayList<>();
				for(PerInfoItemDefDto item : lstPerInfoItemDef){
					List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
					//getActionRole ActionRole
					ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
					//mapping item with data
					LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, subJobPosition);			
					subList.add(objMap);
				}	
				lstLayoutPersonInfoClsDto.add(subList);
			}
			break;
		}
		return lstLayoutPersonInfoClsDto;
	}
//
	private List<PerInfoItemDefDto> getPerItemSet(PerInfoItemDefDto item){
		//1 set - 2 Single
		List<PerInfoItemDefDto> lstResult = new ArrayList<>();
		if (item.getItemTypeState().getItemType() == 1) {
			//get itemId list of children
			SetItemDto setItem = (SetItemDto) item.getItemTypeState();
			// get children by itemId list
			List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty.getPerInfoItemDefByListId(setItem.getItems(), AppContexts.user().contractCode());
			for(int i = 0; i < lstDomain.size(); i++)
				lstResult.add(fromDomain(lstDomain.get(i), i));
		}
		lstResult.add(item);	
		return lstResult;
	}
	
	/**
	 * 
	 * @param perInfoCtg
	 * @param lstPerInfoItemDef
	 * @param personId
	 * @param infoId
	 */
	private List<Object> getPersonCtgItem(PersonInfoCategory perInfoCtg, List<PerInfoItemDefDto> lstPerInfoItemDef, String empId, String personId, String infoId) {
		if(!checkPerInfoCtgAuth(empId, perInfoCtg.getPersonInfoCategoryId())) return new ArrayList<>();
		List<Object> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		switch (perInfoCtg.getCategoryCode().v()) {		
		//person
		case "CS00001":
			
			Optional<Person> person = personRepository.getByPersonId(personId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, person.isPresent() ? person.get() : new Person());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		//current address
		case "CS00003":
			CurrentAddress currentAddress = currentAddressRepository.getCurAddById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, currentAddress);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00004":
			Family family = familyRepository.getFamilyById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, family);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00014":
			WidowHistory widowHistory = widowHistoryRepository.getWidowHistoryById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, widowHistory);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00015":
			PersonEmergencyContact personEmergencyContact = personEmergencyCtRepository.getByid(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, personEmergencyContact);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		}
		return lstLayoutPersonInfoClsDto;
	}
	
	private boolean checkPerInfoCtgAuth(String empId, String ctgId){
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		boolean isSelfAuth = empId.equals(loginEmpId);
		//get perInfoCtgAuth
		PersonInfoCategoryAuth personInfoCategoryAuth = personInfoCategoryAuthRepository.getDetailPersonCategoryAuthByPId(roleId, ctgId).get();
		if(isSelfAuth){
			return personInfoCategoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES;
		}else
			return personInfoCategoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES;
	}
	
	/**
	 * Set actionRole for each item
	 * @Steps:
	 * 		check ctgAuth
	 * 			if read only then all action role of items are read only
	 * 			else if edit
	 * 				each item
	 * 					if read only then action role is read only
	 * 					else if edit then action role is edit
	 * 
	 * @param empId
	 * @param ctgId
	 * @param perInfoItemId
	 * @return
	 */
	private ActionRole getActionRole(String empId, String ctgId, String perInfoItemId){
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		boolean isSelfAuth = empId.equals(loginEmpId);
		
		
		
		if(isSelfAuth)
			return personInfoItemAuthRepository
					.getItemDetai(roleId, ctgId, perInfoItemId)
					.get().getSelfAuth() != PersonInfoAuthType.UPDATE ? 
							ActionRole.EDIT: ActionRole.VIEW_ONLY;		
		else 
			return personInfoItemAuthRepository
					.getItemDetai(roleId, ctgId, perInfoItemId)
					.get().getOtherAuth() != PersonInfoAuthType.UPDATE?
							ActionRole.EDIT: ActionRole.VIEW_ONLY;	
	}

}
