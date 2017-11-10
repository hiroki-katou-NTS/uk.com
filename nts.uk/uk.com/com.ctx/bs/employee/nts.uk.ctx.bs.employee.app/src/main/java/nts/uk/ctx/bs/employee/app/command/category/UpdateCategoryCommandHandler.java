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

public class UpdateCategoryCommandHandler extends CommandHandler<AddCategoryCommand> {
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
							// Add data
							DomainValueFactory.matchInformation(listItem, currentAddress);
							// Add current address
							currentAddressRepository.updateCurrentAddress(currentAddress);
							break;
						case CS00004:
							Family family = new Family();
							family.setPersonId(personID);
							// Map data
							DomainValueFactory.matchInformation(listItem, family);
							// Add family
							familyRepository.updateFamily(family);
							break;
						case CS00005:
							break;
						case CS00014:
							break;
						case CS00015:
							break;
					default:
						break;
					}
					// ドメインモデル「個人情報カテゴリデータ」を更新する
					if (listItem.size() >0){
						for (LayoutPersonInfoCommand dataInfoItem : listItem) {
//							perInfoCtgDataRepository.addCategoryData(currentAddressReposit);
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
						temporaryAbsence.setEmployeeId(empID);
						DomainValueFactory.matchInformation(listItem, temporaryAbsence);
						temporaryAbsenceRepository.updateTemporaryAbsence(temporaryAbsence);
						break;
					case CS00009:
						JobTitle jobTitle = new JobTitle();
						// Add data
						DomainValueFactory.matchInformation(listItem, jobTitle);
						// Add current address
						jobTitleRepository.update(jobTitle);
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
						affiliationDepartment.setEmployeeId(empID);
						// Map data
						DomainValueFactory.matchInformation(listItem, affiliationDepartment);
						// Add affiliationDepartment
						affDepartmentRepository.updateAffDepartment(affiliationDepartment);
						break;
					case CS00012:
						break;
					case CS00013:
						SubJobPosition subJob = new SubJobPosition();
						subJob.setSubJobPosId(newId);
						// Map data
						DomainValueFactory.matchInformation(listItem, subJob);
						// Add SubJobPosition
						subJobPosRepository.updateSubJobPosition(subJob);
						break;
				}
					
				// 社員情報の任意項目のデータを登録する
				List<PersonInfoItemDefinition> listItemDef = perInfoItemDefRepositoty.getNotFixedPerInfoItemDefByCategoryId(categoryID, contractCode);
				PersonInfoItemData itemData = null;
				if (listItem.size() > 0){
					perInfoCtgDataRepository.addCategoryData(new PerInfoCtgData(newId,categoryID,empID));
					for (LayoutPersonInfoCommand item : listItem){
						DataState state = new DataState();
						itemData = new PersonInfoItemData(DomainValueFactory.convertToString(item.getItemDefId()), newId,state);
						
//						perInfoItemDataRepository
					}
				}
					
				} else {
					
				}
			}
		 });
//		for (LayoutPersonInfoCommand item : groupByCategory){
//			// ドメインモデル「個人情報カテゴリ」を取得する
//			perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategory(item.getCategoryId(),contractCode);
//			// In case of person
//			if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
//				// IS FIXED
//				if (perInfoCategory.get().getIsFixed() == IsFixed.FIXED) {
//					switch (perInfoCategory.get().getCategoryCode().v()) {
//						case "CS00003":
//							break;
//						case "CS00004":
//							break;
//						case "CS00014":
//							break;
//						case "CS00015":
//							break;
//					}
//				} else {
//					
//				}
//			} 
			// In case of Employee
//			else if(perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
//				// IS FIXED
//				if (perInfoCategory.get().getIsFixed() == IsFixed.FIXED) {
//					
//				} else {
//					
//				}
//			}
//		}
		
	}
	private DataTypeValue getItemType(List<PersonInfoItemDefinition> listItemDef, String itemCode){
		PersonInfoItemDefinition itemDef = listItemDef.stream().filter(item-> itemCode.equals(item.getItemCode().v())).findAny().orElse(null);
		if (itemDef != null){
			SingleItem item = (SingleItem)itemDef.getItemTypeState();
			DataTypeState dataType = item.getDataTypeState();
			return dataType.getDataTypeValue();
		}
		return null;
	}
}
