package nts.uk.ctx.bs.employee.app.command.person.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
		//TODO:  
	}	
	
}
