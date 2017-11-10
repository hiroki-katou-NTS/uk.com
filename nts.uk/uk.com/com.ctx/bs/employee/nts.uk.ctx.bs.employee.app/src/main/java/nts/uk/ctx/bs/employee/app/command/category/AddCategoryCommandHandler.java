package nts.uk.ctx.bs.employee.app.command.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.app.command.employee.LayoutPersonInfoCommand;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmpInfoCtgData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;

public class AddCategoryCommandHandler extends CommandHandler<AddCategoryCommand> {
	@Inject 
	private EmployeeRepository employeeRepository;
	
	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Inject
	private CurrentAddressRepository currentAddressRepository;
	
	@Inject 
	private FamilyRepository familyRepository;
	
	@Inject 
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Inject
	private JobTitleRepository jobTitleRepository;
	
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;
	
	@Inject
	private AffDepartmentRepository affDepartmentRepository;
	
	@Inject
	private SubJobPosRepository subJobPosRepository;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	@Inject 
	private PerInfoCtgDataRepository perInfoCtgDataRepository;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	@Override
	protected void handle(CommandHandlerContext<AddCategoryCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		// Get contractCode
		String contractCode = AppContexts.user().contractCode();
		// Get Command
		AddCategoryCommand command = context.getCommand();
		
		// ドメインモデル「社員」を取得する
		Optional<Employee> emp = employeeRepository.findBySid(companyId, command.getEmployeeId());
		
		if (!emp.isPresent()){
			return;
		}
		
		 Map<String, List<LayoutPersonInfoCommand>> groupByCategory =
				 command.getItems().stream().collect(Collectors.groupingBy(LayoutPersonInfoCommand ::getCategoryId));

		 groupByCategory.forEach((categoryID, listItem)->{
			 if (listItem.size() <= 0){
				 return;
			 }
			 Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategory(listItem.get(0).getCategoryId(),contractCode);
			 if (!perInfoCategory.isPresent()){
				 return;
			 }
			// In case of person
			if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
				// IS FIXED
				String newId = IdentifierUtil.randomUniqueId();
				String personID = emp.get().getPId();
				
				if (perInfoCategory.get().getIsFixed() == IsFixed.FIXED) {
					switch (CategoryTypeCode.valueOf(perInfoCategory.get().getCategoryCode().v())) {
					case CS00003:
						CurrentAddress currentAddress = new CurrentAddress();
						currentAddress.setCurrentAddressId(newId);
						currentAddress.setPid(personID);
						// Add data
						DomainValueFactory.matchInformation(listItem, currentAddress);
						// Add current address
						currentAddressRepository.addCurrentAddress(currentAddress);
						break;
					case CS00004:
						Family family = new Family();
						family.setFamilyId(newId);
						family.setPersonId(personID);
						// Map data
						DomainValueFactory.matchInformation(listItem, family);
						// Add family
						familyRepository.addFamily(family);
						break;
					case CS00014:
						break;
					case CS00015:
						break;
					default:
						break;
					}
					// ドメインモデル「個人情報カテゴリデータ」を更新する
					List<PersonInfoItemDefinition> listItemDef = perInfoItemDefRepositoty.getNotFixedPerInfoItemDefByCategoryId(categoryID, contractCode);
					PersonInfoItemData itemData = null;
					if (listItem.size() >0){
						// Add category data
						perInfoCtgDataRepository.addCategoryData(new PerInfoCtgData(newId,categoryID,personID));
						// Add item data
						for (LayoutPersonInfoCommand item : listItem) {
							DataState state = null;
							// Create data state
							switch( getItemType(listItemDef,item.getItemCode())){
							case STRING:
								state = DataState.createFromStringValue(DomainValueFactory.convertToString(item.getValue()));
							break;
							case NUMERIC:
								state = DataState.createFromNumberValue(DomainValueFactory.convertToDecimal(item.getValue()));
							break;
							case DATE:
								state = DataState.createFromDateValue(DomainValueFactory.convertToDate(item.getValue()));
							break;
							case TIME:
								state = DataState.createFromDateValue(DomainValueFactory.convertToDate(item.getValue()));
							break;
							case TIMEPOINT:
								state = DataState.createFromDateValue(DomainValueFactory.convertToDate(item.getValue()));
							break;
							case SELECTION:
								state = DataState.createFromNumberValue(DomainValueFactory.convertToDecimal(item.getValue()));
							break;
							}
							itemData = new PersonInfoItemData(DomainValueFactory.convertToString(item.getItemDefId()), newId,state);
							perInfoItemDataRepository.addItemData(itemData);
						}
					}
				} else {
					
				}
			} 
//				 In case of Employee
			else if(perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
				// IS FIXED
				String newId = IdentifierUtil.randomUniqueId();
				String empID = emp.get().getSId();
				
				if (perInfoCategory.get().getIsFixed() == IsFixed.FIXED) {
					switch (CategoryTypeCode.valueOf(perInfoCategory.get().getCategoryCode().v())) {
					case CS00008:
						TemporaryAbsence temporaryAbsence = new TemporaryAbsence();
						temporaryAbsence.setTempAbsenceId(newId);
						temporaryAbsence.setEmployeeId(empID);
						DomainValueFactory.matchInformation(listItem, temporaryAbsence);
						temporaryAbsenceRepository.addTemporaryAbsence(temporaryAbsence);
						break;
					case CS00009:
						JobTitle jobTitle = new JobTitle();
						jobTitle.setJobTitleId(newId);
						// Add data
						DomainValueFactory.matchInformation(listItem, jobTitle);
						// Add current address
						jobTitleRepository.add(jobTitle);
						break;
					case CS00010:
						AffWorkplaceHistory affWorkplaceHistory = new AffWorkplaceHistory();
//						affWorkplaceHistory.set
						// Map data
						DomainValueFactory.matchInformation(listItem, affWorkplaceHistory);
						// Add AffWorkplaceHistory
						affWorkplaceHistoryRepository.addAffWorkplaceHistory(affWorkplaceHistory);
						break;
					case CS00011:
						AffiliationDepartment affiliationDepartment = new AffiliationDepartment();
						affiliationDepartment.setDepartmentId(newId);
						affiliationDepartment.setEmployeeId(empID);
						// Map data
						DomainValueFactory.matchInformation(listItem, affiliationDepartment);
						// Add affiliationDepartment
						affDepartmentRepository.addAffDepartment(affiliationDepartment);
						break;
					case CS00012:
						break;
					case CS00013:
						SubJobPosition subJob = new SubJobPosition();
						subJob.setSubJobPosId(newId);
						// Map data
						DomainValueFactory.matchInformation(listItem, subJob);
						// Add SubJobPosition
						subJobPosRepository.addSubJobPosition(subJob);
						break;
					default:
						break;
				}
					
				// 社員情報の任意項目のデータを登録する
				List<PersonInfoItemDefinition> listItemDef = perInfoItemDefRepositoty.getNotFixedPerInfoItemDefByCategoryId(categoryID, contractCode);
				if (listItem.size() > 0){
					// Add emp category data
					emInfoCtgDataRepository.addCategoryData(new EmpInfoCtgData(newId,categoryID,empID));
					// Add item data
					EmpInfoItemData itemData = null;
					for (LayoutPersonInfoCommand item : listItem){
						nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState state = null;
						// Create data state
						switch( getItemType(listItemDef,item.getItemCode())){
						case STRING:
							state = nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState.createFromStringValue(DomainValueFactory.convertToString(item.getValue()));
						break;
						case NUMERIC:
							state = nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState.createFromNumberValue(DomainValueFactory.convertToDecimal(item.getValue()));
						break;
						case DATE:
							state = nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState.createFromDateValue(DomainValueFactory.convertToDate(item.getValue()));
						break;
						case TIME:
							state = nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState.createFromDateValue(DomainValueFactory.convertToDate(item.getValue()));
						break;
						case TIMEPOINT:
							state = nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState.createFromDateValue(DomainValueFactory.convertToDate(item.getValue()));
						break;
						case SELECTION:
							state = nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState.createFromNumberValue(DomainValueFactory.convertToDecimal(item.getValue()));
						break;
						}
						itemData = new EmpInfoItemData(item.getItemDefId(), newId, state);
						empInfoItemDataRepository.addItemData(itemData);
					}
				}
					
				} else {
					
				}
			}
		 });
		
	}
	
	private DataTypeValue getItemType(List<PersonInfoItemDefinition> listItemDef, String itemCode){
		Optional<PersonInfoItemDefinition> itemDef = listItemDef.stream().filter(item-> itemCode.equals(item.getItemCode().v())).findFirst();
		if (itemDef.isPresent()){
			SingleItem item = (SingleItem)itemDef.get().getItemTypeState();
			DataTypeState dataType = item.getDataTypeState();
			return dataType.getDataTypeValue();
		}
		return null;
	}
}
