package nts.uk.ctx.bs.employee.app.command.employee.deletemanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.deleteempmanagement.DeleteEmpManagement;
import nts.uk.ctx.bs.employee.dom.deleteempmanagement.DeleteEmpRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RestoreDataEmpCommandHandler extends CommandHandler<EmployeeDeleteToRestoreCommand>{

	@Inject
	EmployeeRepository empRepo;
	
	@Inject
	PersonRepository personRepo;
	
	@Inject
	DeleteEmpRepository deleteEmpRepo;
	
	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteToRestoreCommand> context) {
		
		
		String cid = AppContexts.user().companyId();
		EmployeeDeleteToRestoreCommand command = context.getCommand();
		
		// get Employee
		Employee emp = empRepo.findBySid(cid, command.getSId()).get();
		//set new sid
		emp.setSCd(new EmployeeCode(command.getNewCode()));
		// update employee
		empRepo.updateEmployee(emp);
		
		
		//get Person
		Person person = personRepo.getByPersonId(emp.getPId().toString()).get();
		PersonNameGroup nameGroup = person.getPersonNameGroup();
		nameGroup.setBusinessName(new BusinessName(command.getNewName()));
		person.setPersonNameGroup(nameGroup);
		
		//update person
		personRepo.update(person);
		
		// remove Employee Delete Management
		DeleteEmpManagement delEmp = deleteEmpRepo.getBySid(command.getSId()).get();
		deleteEmpRepo.remove(delEmp);
		
	}

}
