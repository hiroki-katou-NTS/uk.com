package nts.uk.ctx.bs.employee.app.find.person.category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.DataTypeStateDto;
import find.person.info.item.ItemTypeStateDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.SetItemDto;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefFactoryNew;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
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
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMember;
import nts.uk.ctx.bs.person.dom.person.family.FamilyMemberRepository;
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
	private FamilyMemberRepository familyRepository;

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
	private AffDepartmentRepository affDepartmentRepository;

	@Inject
	I18NResourcesForUK ukResouce;

	// New update: start
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
		lstPerInfoCtg = perInfoCtgRepositoty.getPerInfoCtgByParentCdWithOrder(perInfoCtg.getCategoryParentCode().v(),
				contractCode, true);
		lstPerInfoCtg.add(perInfoCtg);
		return lstPerInfoCtg;
	}

	/**
	 * Hiển thị nội dung của 1 Tab
	 * 
	 * @param ctgId
	 * @return List<LayoutPersonInfoClsDto> : nội dung items và data của nó
	 */
	public List<EmpMaintLayoutDto> getTabDetail(String employeeId, String ctgId, String infoId) {
		// app context
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();

		// get Employee
		Employee employee = employeeRepository.findBySid(companyId, employeeId).get();
		// Get PersonInfoCategory
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		// get PerInfoItemDefDto
		List<PersonInfoItemDefinition> lstDomain = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, infoId, roleId == null ? "" : roleId, companyId,
						contractCode, loginEmpId.equals(employeeId)));
		List<PerInfoItemDefDto> lstPerInfoItemDef = new ArrayList<>();
		for (int i = 0; i < lstDomain.size(); i++)
			lstPerInfoItemDef.add(fromDomain(lstDomain.get(i), i));

		// return list
		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto;
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED)
			if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
				lstEmpMaintLayoutDto = getEmployeeCtgItem(perInfoCtg, lstPerInfoItemDef, employee, infoId);
			else
				lstEmpMaintLayoutDto = getPersonCtgItem(perInfoCtg, lstPerInfoItemDef, employeeId, employee.getPId(),
						infoId);
		else {
			// optional data
			lstEmpMaintLayoutDto = new ArrayList<>();
			EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(infoId, employeeId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
		}
		return lstEmpMaintLayoutDto;
	}

	/**
	 * Hiển thị nội dung 1 phần trong tab(nếu trong tab có list để lựa chọn)
	 * 
	 * @param employeeId
	 * @param ctgId
	 * @param subDetailId
	 * @return
	 */
	public List<EmpMaintLayoutDto> getTabSubDetail(String employeeId, String ctgId, String subDetailId, String infoId) {
		// app context
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		// Get PersonInfoCategory
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		// get PerInfoItemDefDto
		List<PersonInfoItemDefinition> lstDomain = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, infoId, roleId == null ? "" : roleId, companyId,
						contractCode, loginEmpId.equals(employeeId)));
		List<PerInfoItemDefDto> lstPerInfoItemDef = new ArrayList<>();
		for (int i = 0; i < lstDomain.size(); i++)
			lstPerInfoItemDef.add(fromDomain(lstDomain.get(i), i));		
		Optional<SubJobPosition> subJobPosition = subJobPosRepository.getById(subDetailId);
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		EmpMaintLayoutDto empMaintLayoutDto;
				
		for(PerInfoItemDefDto item : lstPerInfoItemDef){
			List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
			//getActionRole ActionRole
			ActionRole  actionRole = getActionRole(employeeId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
			//mapping item with data
			LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
					subJobPosition.isPresent()? subJobPosition.get(): new SubJobPosition());			
			lstLayoutPersonInfoClsDto.add(objMap);
		}
		//get affiDepartment
		empMaintLayoutDto = new EmpMaintLayoutDto();
		empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto = new ArrayList<>();
		lstEmpMaintLayoutDto.add(empMaintLayoutDto);
		return lstEmpMaintLayoutDto;
	}

	/**
	 * convert perInfoItemDef domain to Dto
	 * 
	 * @param itemDef
	 * @param dispOrder
	 * @return
	 */
	private PerInfoItemDefDto fromDomain(PersonInfoItemDefinition itemDef, int dispOrder) {
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		return new PerInfoItemDefDto(itemDef.getPerInfoItemDefId(), itemDef.getPerInfoCategoryId(),
				itemDef.getItemCode().v(), itemDef.getItemName().v(), itemDef.getIsAbolition().value,
				itemDef.getIsFixed().value, itemDef.getIsRequired().value, itemDef.getSystemRequired().value,
				itemDef.getRequireChangable().value, dispOrder, itemDef.getSelectionItemRefType(),
				createItemTypeStateDto(itemDef.getItemTypeState()), selectionItemRefTypes);
	}

	/**
	 * create ItemTypeStateDto
	 * 
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
	 * 
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
	// New update: end

	/**
	 * set category item optional
	 * 
	 * @param recordId
	 * @param isSingleList
	 */
	private List<LayoutPersonInfoClsDto> getCtgItemOptionDto(String recordId, String empId) {
		List<EmpInfoItemData> lstCtgItemOptionalDto = empInfoItemDataRepository.getAllInfoItemByRecordId(recordId);
		List<LayoutPersonInfoClsDto> lstObj = new ArrayList<>();
		List<Object> items = new ArrayList<>();
		for (int i = 0; i < lstCtgItemOptionalDto.size(); i++) {
			LayoutPersonInfoValueDto obj = getLayoutPerInfoValFromOptData(lstCtgItemOptionalDto.get(i), empId, i);
			LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
			items.add(obj);
			layoutPersonInfoClsDto.setItems(items);
		}
		return lstObj;
	}

	private LayoutPersonInfoValueDto getLayoutPerInfoValFromOptData(EmpInfoItemData empInfoItemData, String empId,
			int dispOrder) {
		Object data = null;
		PerInfoItemDefDto perInfoItemDefDto = fromDomain(perInfoItemDefRepositoty
				.getPerInfoItemDefById(empInfoItemData.getPerInfoDefId(), AppContexts.user().contractCode()).get(),
				dispOrder);
		return LayoutPersonInfoValueDto.initData(empInfoItemData.getPerInfoCtgCd(), perInfoItemDefDto, data,
				getActionRole(empId, empInfoItemData.getPerInfoCtgId(), empInfoItemData.getPerInfoDefId()));
	}

	/**
	 * set category item
	 * 
	 * @param empPerCtgInfoDto
	 * @param employee
	 * @param perInfoCtg
	 * @param parentInfoId
	 */
	private List<EmpMaintLayoutDto> getEmployeeCtgItem(PersonInfoCategory perInfoCtg, List<PerInfoItemDefDto> lstPerInfoItemDef, Employee employee, String infoId) {
		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto = new ArrayList<>();
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		String empId = employee.getSId();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		if(!checkPerInfoCtgAuth(employee.getSId(), perInfoCtg.getPersonInfoCategoryId())) return new ArrayList<>();
		EmpMaintLayoutDto empMaintLayoutDto;
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
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(empId, empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		case "CS00008":
			Optional<TempAbsenceHisItem> temporaryAbsence = temporaryAbsenceRepository.getByTempAbsenceId(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						temporaryAbsence.isPresent()? temporaryAbsence.get(): new TempAbsenceHisItem());			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(temporaryAbsence.get().getHistoryId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);										
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
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(jobTitleMain.get().getJobTitleId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
			
		case "CS00010":
			//--Having dateHistItem list
			AssignedWorkplace assignedWorkplace = assignedWrkplcRepository.getAssignedWorkplaceById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, assignedWorkplace);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(assignedWorkplace.getAssignedWorkplaceId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
			
		case "CS00011":
			// Affiliation Department
			Optional<AffiliationDepartment> affiDept = affDepartmentRepository.getById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						affiDept.isPresent()?affiDept.get(): null);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(affiDept.get().getDepartmentId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
			
		case "CS00012":
			//--Having dateHistItem list
			CurrentAffiDept currentAffiDept = currentAffiDeptRepository.getCurrentAffiDeptById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, currentAffiDept);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(currentAffiDept.getAffiDeptId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
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
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(incomeTax.get().getIncomeTaxID(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
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
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(familySocialInsurance.get().getSocailInsuaranceId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
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
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 		//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(familyCare.get().getFamilyCareId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		case "CS00013":		
			Optional<AffiliationDepartment> affiliationDepartment = affDepartmentRepository.getById(infoId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				//getActionRole ActionRole
				ActionRole  actionRole = getActionRole(employee.getSId(), perInfoCtg.getPersonInfoCategoryId(), item.getId());
				//mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, actionRole, 
						affiliationDepartment.isPresent()?affiliationDepartment.get(): null);			
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			//get affiDepartment
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
	 			//set optional data			
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(affiliationDepartment.get().getDepartmentId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto); 
			//get list period of position
			List<SubJobPosition> lstSubJobPos = subJobPosRepository.getSubJobPosByDeptId(infoId);
			lstLayoutPersonInfoClsDto = new ArrayList<>();
			for(SubJobPosition x : lstSubJobPos){
				LayoutPersonInfoClsDto objMap = new LayoutPersonInfoClsDto();
				objMap.getItems().add(new OptionObjecstDto(x.getStartDate().toString() + " ~ " + x.getEndDate().toString(), x.getSubJobPosId()));
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		}
		return lstEmpMaintLayoutDto;
	}

	//
	private List<PerInfoItemDefDto> getPerItemSet(PerInfoItemDefDto item) {
		// 1 set - 2 Single
		List<PerInfoItemDefDto> lstResult = new ArrayList<>();
		if (item.getItemTypeState().getItemType() == 1) {
			// get itemId list of children
			SetItemDto setItem = (SetItemDto) item.getItemTypeState();
			// get children by itemId list
			List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty
					.getPerInfoItemDefByListId(setItem.getItems(), AppContexts.user().contractCode());
			for (int i = 0; i < lstDomain.size(); i++)
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
	private List<EmpMaintLayoutDto> getPersonCtgItem(PersonInfoCategory perInfoCtg,
			List<PerInfoItemDefDto> lstPerInfoItemDef, String empId, String personId, String infoId) {
		if (!checkPerInfoCtgAuth(empId, perInfoCtg.getPersonInfoCategoryId()))
			return new ArrayList<>();
		List<EmpMaintLayoutDto> lstEmpMaintLayoutDto = new ArrayList<>();
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		EmpMaintLayoutDto empMaintLayoutDto;
		switch (perInfoCtg.getCategoryCode().v()) {
		// person
		case "CS00001":

			Optional<Person> person = personRepository.getByPersonId(personId);
			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				// getActionRole ActionRole
				ActionRole actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						itemSet, actionRole, person.isPresent() ? person.get() : new Person());
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			// set optional data
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(person.get().getPersonId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		// current address
		case "CS00003":
			CurrentAddress currentAddress = currentAddressRepository.getCurAddById(infoId);
			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				// getActionRole ActionRole
				ActionRole actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						itemSet, actionRole, currentAddress);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			// set optional data
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(currentAddress.getCurrentAddressId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		case "CS00004":
			FamilyMember family = familyRepository.getFamilyById(infoId);
			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				// getActionRole ActionRole
				ActionRole actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						itemSet, actionRole, family);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			// set optional data
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(family.getFamilyMemberId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		case "CS00014":
			WidowHistory widowHistory = widowHistoryRepository.getWidowHistoryById(infoId);
			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				// getActionRole ActionRole
				ActionRole actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						itemSet, actionRole, widowHistory);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			// set optional data
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(getCtgItemOptionDto(widowHistory.getWidowHistoryId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		case "CS00015":
			PersonEmergencyContact personEmergencyContact = personEmergencyCtRepository.getByid(infoId);
			for (PerInfoItemDefDto item : lstPerInfoItemDef) {
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				// getActionRole ActionRole
				ActionRole actionRole = getActionRole(empId, perInfoCtg.getPersonInfoCategoryId(), item.getId());
				// mapping item with data
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(),
						itemSet, actionRole, personEmergencyContact);
				lstLayoutPersonInfoClsDto.add(objMap);
			}
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto.setClassificationItems(lstLayoutPersonInfoClsDto);
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			// set optional data
			empMaintLayoutDto = new EmpMaintLayoutDto();
			empMaintLayoutDto
					.setClassificationItems(getCtgItemOptionDto(personEmergencyContact.getEmgencyContactId(), empId));
			lstEmpMaintLayoutDto.add(empMaintLayoutDto);
			break;
		}
		return lstEmpMaintLayoutDto;
	}

	/**
	 * check ctgAuth if it is self auth then check AllowPersonRef field else
	 * check AllowOtherRef
	 * 
	 * @param empId
	 * @param ctgId
	 * @return
	 */
	private boolean checkPerInfoCtgAuth(String empId, String ctgId) {
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		boolean isSelfAuth = empId.equals(loginEmpId);
		// get perInfoCtgAuth
		PersonInfoCategoryAuth personInfoCategoryAuth = personInfoCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(roleId, ctgId).get();
		if (isSelfAuth) {
			return personInfoCategoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES;
		} else
			return personInfoCategoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES;
	}

	/**
	 * Set actionRole for each item
	 * 
	 * @Effect:
	 * 
	 * 			each item if read only then action role is read only else if
	 *          edit then action role is edit
	 * 
	 * @param empId
	 * @param ctgId
	 * @param perInfoItemId
	 * @return
	 */
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
