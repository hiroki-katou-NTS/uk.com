package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.SetItemDto;
import lombok.val;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefFactoryNew;
import nts.uk.ctx.bs.employee.app.find.layout.ItemDefinitionFactory;
import nts.uk.ctx.bs.employee.app.find.person.info.PersonDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemEmpInfoItemDataDto;
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
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
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
	
	//New update: start
	/**
	 * get person information category and it's children (Hiển thị category và
	 * danh sách tab category con của nó)
	 * 
	 * @param ctgId
	 * @return list PerCtgInfo: cha va danh sach con
	 */
	public List<PersonInfoCategory> getCtgAndChildren(String ctgId) {
		String contractCode = AppContexts.user().contractCode();
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		List<PersonInfoCategory> lstPerInfoCtg = new ArrayList<>();
		if(perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO)
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
	public List<LayoutPersonInfoClsDto> getCtgInfoData(String ctgId, String employeeId, String infoId){
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
		List<PerInfoItemDefDto> lstPerInfoItemDef = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, infoId,
						roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)))
				.stream().map(x -> fromDomain(x)).collect(Collectors.toList());
		
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED)
			if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
				setEmployeeCtgItem(perInfoCtg, lstPerInfoItemDef, employee, infoId);
			else
				setPersonCtgItem(perInfoCtg, lstPerInfoItemDef, employee.getPId(), infoId);
		else{
			//optional data
			//setCtgItemOptionDto(empPerCtgInfoDto, parentInfoId, true);
		}
		//return list
		List<LayoutPersonInfoClsDto> lstLayoutPerInfoClsDto = new ArrayList<>();
		return lstLayoutPerInfoClsDto;
	}
	
	public List<LayoutPersonInfoClsDto> getCtgInfoTabData(String ctgId){
		//return list
		List<LayoutPersonInfoClsDto> lstLayoutPerInfoClsDto = new ArrayList<>();
		return lstLayoutPerInfoClsDto;
	}
	
	
	private PerInfoItemDefDto fromDomain(PersonInfoItemDefinition domain){
		PerInfoItemDefDto perInfoItemDefDto = new PerInfoItemDefDto();
		perInfoItemDefDto.setId(domain.getPerInfoItemDefId());
		perInfoItemDefDto.setPerInfoCtgId(domain.getPerInfoCategoryId());
		perInfoItemDefDto.setItemCode(domain.getItemCode().v());
		perInfoItemDefDto.setItemName(domain.getItemName().v());
		perInfoItemDefDto.setIsFixed(domain.getIsFixed().value);
		perInfoItemDefDto.setIsRequired(domain.getIsRequired().value);
		//perInfoItemDefDto.setItemTypeState(domain.getItemTypeState());
		return perInfoItemDefDto;
	}
	//New update: end
		

	/**
	 * get person ctg infor and list of item children
	 * 
	 * @param ctgId
	 * @return EmpPerCtgInfoDto
	 */
	public EmpPerCtgInfoDto getCtgAndItemByCtgId(String ctgId) {
		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();
		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(),
				AppContexts.user().contractCode());
		return EmpPerCtgInfoDto.createObjectFromDomain(perCtgInfo, lstPerItemDef);
	}

	/**
	 * person ctg infor and list of item children by parent hiển thị category và
	 * danh sách item data
	 * 
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return EmpPerCtgInfoDto
	 */
	public List<LayoutPersonInfoClsDto> getCtgAndItemByParent(String employeeId, String ctgId, String parentInfoId) {
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		List<PerInfoItemDefDto> lstPerInfoItemDef;
		val perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		if (perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) {
			lstPerInfoItemDef = perInfoCtgDomainService.getPerItemDef(new ParamForGetPerItem(perInfoCtg, parentInfoId,
					roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)))
					.stream().map(x -> fromDomain(x)).collect(Collectors.toList());
			return lstLayoutPersonInfoClsDto;
		} else {
			lstPerInfoItemDef = perInfoCtgDomainService.getPerItemDef(new ParamForGetPerItem(perInfoCtg, parentInfoId,
					roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)))
					.stream().map(x -> fromDomain(x)).collect(Collectors.toList());
			EmpPerCtgInfoDto empPerCtgInfoDto = new EmpPerCtgInfoDto();
			//empPerCtgInfoDto = EmpPerCtgInfoDto.createObjectFromDomain(perInfoCtg, lstPerInfoItemDef);
			setEmployeeCtgItem(empPerCtgInfoDto, perInfoCtg, parentInfoId);
			return lstLayoutPersonInfoClsDto;
		}
	}
	
	private List<LayoutPersonInfoValueDto> mappingData(){
		List<LayoutPersonInfoValueDto> lst = new ArrayList<>();
		return lst;
	}
	
	

	/**
	 * Hiển thị item và danh sách data
	 * 
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return
	 */
	public EmpPerCtgInfoDto getItemInCtgAndChildrenList(String employeeId, String ctgId, String parentInfoId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		//get Employee
		Employee employee = employeeRepository.findBySid(companyId, employeeId).get();
		//Get PersonInfoCategory
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		//get PerInfoItemDefDto
		List<PerInfoItemDefDto> lstPerInfoItemDef = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, parentInfoId,
						roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)))
				.stream().map(x -> fromDomain(x)).collect(Collectors.toList());
		
		EmpPerCtgInfoDto empPerCtgInfoDto = EmpPerCtgInfoDto.createObjectFromDomain(perInfoCtg);

		if (perInfoCtg.getIsFixed() == IsFixed.FIXED)
			if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
				setEmployeeCtgItem(perInfoCtg, lstPerInfoItemDef, employee, parentInfoId);
			else
				setPersonCtgItem(perInfoCtg, lstPerInfoItemDef, employeeId, parentInfoId);
		else
			setCtgItemOptionDto(empPerCtgInfoDto, parentInfoId, true);
		return null;
	}

	/**
	 * set category item optional
	 * 
	 * @param sId
	 * @param ctgId
	 * @return CtgItemOptionalDto
	 */
	private void setCtgItemOptionDto(EmpPerCtgInfoDto empPerCtgInfoDto, String recordId, boolean isSingleList) {
		CtgItemOptionalDto ctgItemOptionalDto = new CtgItemOptionalDto();
		List<ItemEmpInfoItemDataDto> lstCtgItemOptionalDto = empInfoItemDataRepository
				.getAllInfoItemByRecordId(recordId).stream()
				.map(x -> ItemEmpInfoItemDataDto.fromDomain(x))
				.collect(Collectors.toList());
		if (isSingleList) {
			ctgItemOptionalDto.setLstEmpInfoItemData(lstCtgItemOptionalDto);
		} else {
			lstCtgItemOptionalDto.stream().forEach(x -> ctgItemOptionalDto.addToItemEmpInfoItemDataDto(x));
		}
		empPerCtgInfoDto.setCtgItemOptionalDto(ctgItemOptionalDto);
	}

	/**
	 * set category item
	 * 
	 * @param perInfoCtg
	 * @param parentInfoId
	 */
	private void setEmployeeCtgItem(EmpPerCtgInfoDto empPerCtgInfoDto, PersonInfoCategory perInfoCtg,
			String parentInfoId) {
		CtgItemFixDto ctgItemFixDto = new CtgItemFixDto();
		switch (perInfoCtg.getCategoryCode().v()) {
		
		}
		empPerCtgInfoDto.setCtgItemFixedDto(ctgItemFixDto);
	}

	/**
	 * set category item
	 * 
	 * @param empPerCtgInfoDto
	 * @param employee
	 * @param perInfoCtg
	 * @param parentInfoId
	 */
	private List<LayoutPersonInfoClsDto> setEmployeeCtgItem(PersonInfoCategory perInfoCtg, List<PerInfoItemDefDto> lstPerInfoItemDef, Employee employee, String infoId) {
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);

		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00002":
					
			//set optional data
			break;
		case "CS00008":
			// Waiting for DateHistoryItem
			Optional<TemporaryAbsence> temporaryAbsence = temporaryAbsenceRepository.getByTempAbsenceId(infoId);
			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, 
					temporaryAbsence.isPresent()?temporaryAbsence.get(): new TemporaryAbsence());	
			break;
		case "CS00009":
			Optional<JobTitleMain> jobTitleMain = jobTitleMainRepository.getJobTitleMainById(infoId);
			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, 
					jobTitleMain.isPresent()?jobTitleMain.get() : new JobTitleMain());
			break;
		case "CS00010":
			// Waiting for DateHistoryItem
			AssignedWorkplace assignedWorkplace = assignedWrkplcRepository.getAssignedWorkplaceById(infoId);
			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, assignedWorkplace);	
			break;
		case "CS00011":
			// Waiting for DateHistoryItem
			// Affiliation Department
			break;
		case "CS00012":
			// Waiting for DateHistoryItem
			CurrentAffiDept currentAffiDept = currentAffiDeptRepository.getCurrentAffiDeptById(infoId);
			//ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, currentAffiDept);
			break;
		case "CS00005":
			Optional<IncomeTax> incomeTax = incomeTaxRepository.getIncomeTaxById(infoId);
//			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, 
//					incomeTax.isPresent()? incomeTax.get() : new IncomeTax());
			break;
		case "CS00006":
			Optional<FamilySocialInsurance> familySocialInsurance = familySocialInsuranceRepository
					.getFamilySocialInsById(infoId);
//			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, 
//					familySocialInsurance.isPresent()? familySocialInsurance.get() : new FamilySocialInsurance());
			break;
		case "CS00007":
			Optional<FamilyCare> familyCare = familyCareRepository.getFamilyCareById(infoId);
//			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, 
//					familyCare.isPresent()? familyCare.get() : new FamilyCare());
			break;
		case "CS00013":
			List<SubJobPosition> lstSubJobPos = subJobPosRepository.getSubJobPosByDeptId(infoId);
			//ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, lstSubJobPos);
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
			lstResult = perInfoItemDefRepositoty.getPerInfoItemDefByListId(setItem.getItems(), AppContexts.user().contractCode())
					.stream().map(x -> fromDomain(x)).collect(Collectors.toList());
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
	private void setPersonCtgItem(PersonInfoCategory perInfoCtg, List<PerInfoItemDefDto> lstPerInfoItemDef, String personId, String infoId) {
		List<LayoutPersonInfoClsDto> lstLayoutPersonInfoClsDto = new ArrayList<>();
		LayoutPersonInfoClsDto layoutPersonInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPersonInfoClsDto.setListItemDf(lstPerInfoItemDef);
		switch (perInfoCtg.getCategoryCode().v()) {
		case "CS00001":
			
			Optional<Person> person = personRepository.getByPersonId(personId);
			for(PerInfoItemDefDto item : lstPerInfoItemDef){
				List<PerInfoItemDefDto> itemSet = getPerItemSet(item);
				LayoutPersonInfoClsDto objMap = ItemDefFactoryNew.matchInformation(perInfoCtg.getCategoryCode().v(), itemSet, person.isPresent() ? person.get() : new Person());
				lstLayoutPersonInfoClsDto.add(objMap);
			}	
			break;
		case "CS00003":
			CurrentAddress currentAddress = currentAddressRepository.getCurAddById(infoId);
			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, currentAddress);
			break;
		case "CS00004":
			Family family = familyRepository.getFamilyById(infoId);
			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, family);
			break;
		case "CS00014":
			WidowHistory widowHistory = widowHistoryRepository.getWidowHistoryById(infoId);
			ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, widowHistory);
			break;
		case "CS00015":
			PersonEmergencyContact personEmergencyContact = personEmergencyCtRepository.getByid(infoId);
			//ItemDefinitionFactory.matchInformation(perInfoCtg.getCategoryCode().v(), layoutPersonInfoClsDto, personEmergencyContact);
			break;
		}
	}

}
