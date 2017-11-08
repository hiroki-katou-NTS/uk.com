package nts.uk.ctx.bs.employee.app.command.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.command.employee.LayoutPersonInfoCommand;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
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
//		perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON)
//		if (emp.get())
		
		 Map<String, List<LayoutPersonInfoCommand>> groupByCategory =
				 command.getItems().stream().collect(Collectors.groupingBy(LayoutPersonInfoCommand ::getCategoryId));

//		 Optional<PersonInfoCategory> perInfoCategory = null;
		 groupByCategory.forEach((k,v)->{
			 if (v.size() <=0){
				 return;
			 }
			 Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategory(v.get(0).getCategoryId(),contractCode);
			 if (!perInfoCategory.isPresent()){
				 return;
			 }
			// In case of person
			if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
				// IS FIXED
				if (perInfoCategory.get().getIsFixed() == IsFixed.FIXED) {
					switch (perInfoCategory.get().getCategoryCode().v()) {
						case "CS00003":
							CurrentAddress currentAddress = new CurrentAddress();
							// Add data
							DomainValueFactory.matchInformation(v, currentAddress);
							// Add current address
							currentAddressRepository.addCurrentAddress(currentAddress);
							break;
						case "CS00004":
							Family family = new Family();
							// Map data
							DomainValueFactory.matchInformation(v, family);
							// Add family
							familyRepository.addFamily(family);
							break;
						case "CS00014":
							break;
						case "CS00015":
							break;
					}
				} else {
					
				}
			} 
//				 In case of Employee
			else if(perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
				// IS FIXED
				if (perInfoCategory.get().getIsFixed() == IsFixed.FIXED) {
					
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

}
