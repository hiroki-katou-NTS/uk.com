package nts.uk.ctx.bs.employee.app.command.person.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemOptionalDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemCurrentAddressDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemFamilyDto;
import nts.uk.ctx.bs.employee.app.find.person.item.ItemPersonDto;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterPerInfoMultiCommandHandler extends CommandHandler<RegisterPerInfoMultiCommand>{

	@Inject
	private EmployeeRepository employeeRepository;
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;
	
	@Inject
	private PersonRepository personRepository;
	
	@Inject 
	private CurrentAddressRepository currentAddressRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterPerInfoMultiCommand> context) {
		RegisterPerInfoMultiCommand command = context.getCommand();
		Employee employee = employeeRepository.getBySid(command.getEmployeeId()).get();
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(command.getPerInfoCtgId(), AppContexts.user().contractCode()).get();
		if(perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			if(perInfoCtg.getIsFixed() == IsFixed.FIXED)
				regEmpCtgItemFixed(command.getEmployeeId(), command.getInfoId(), perInfoCtg, command.getCtgItemFixDto());
			else
				regEmpCtgItemOptional(command.getEmployeeId(), command.getInfoId(), perInfoCtg, command.getCtgItemOptionalDto());
		}else{
			if(perInfoCtg.getIsFixed() == IsFixed.FIXED)
				regPerCtgItemFixed(employee.getPId(), command.getInfoId(), perInfoCtg, command.getCtgItemFixDto());
			else
				regPerCtgItemOptional(employee.getPId(), command.getInfoId(), perInfoCtg, command.getCtgItemOptionalDto());
		}			
	}
	
	private void regEmpCtgItemFixed(String employeeId, String infoId, PersonInfoCategory perInfoCtg, CtgItemFixDto ctgItemFixDto){
		
	}
	
	private void regEmpCtgItemOptional(String employeeId, String infoId, PersonInfoCategory perInfoCtg, CtgItemOptionalDto ctgItemOptionalDto){
		
	}
	
	private void regPerCtgItemFixed(String personId, String infoId, PersonInfoCategory perInfoCtg, CtgItemFixDto ctgItemFixDto){
		switch(perInfoCtg.getCategoryCode().v()){
		case "CS00001":
			ItemPersonDto person = (ItemPersonDto)ctgItemFixDto.getObject();
			personRepository.updatePerson(person.toDomainRequiredField());
			break;
		case "CS00003":
			ItemCurrentAddressDto curAdd = (ItemCurrentAddressDto)ctgItemFixDto.getObject();
			currentAddressRepository.updateCurrentAddress(curAdd.toDomainRequiredField());
			break;
		case "CS00004":
			ItemFamilyDto family = (ItemFamilyDto)ctgItemFixDto.getObject();
			break;
		}
	}
	
	private void regPerCtgItemOptional(String personId, String infoId, PersonInfoCategory perInfoCtg, CtgItemOptionalDto ctgItemOptionalDto){
		
	}
}
