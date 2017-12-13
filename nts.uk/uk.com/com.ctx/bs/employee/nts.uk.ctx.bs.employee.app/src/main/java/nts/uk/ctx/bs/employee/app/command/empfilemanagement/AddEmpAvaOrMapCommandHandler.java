package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;

@Stateless
public class AddEmpAvaOrMapCommandHandler extends CommandHandler<EmpAvaOrMapCommand>{

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;
	
	@Inject
	private EmployeeRepository emplRepo;
	
	@Override
	protected void handle(CommandHandlerContext<EmpAvaOrMapCommand> context) {
		EmpAvaOrMapCommand command = context.getCommand();
		Employee employee = emplRepo.getBySid(command.getEmployeeId()).get();
		this.empFileManagementRepository.insert(PersonFileManagement.createFromJavaType(employee.getPId(),
				command.getFileId(), command.getFileType(), null));
	}

}
