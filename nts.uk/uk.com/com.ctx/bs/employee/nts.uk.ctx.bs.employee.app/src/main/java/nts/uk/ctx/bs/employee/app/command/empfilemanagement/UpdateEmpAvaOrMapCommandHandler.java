package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;

@Stateless
public class UpdateEmpAvaOrMapCommandHandler extends CommandHandler<EmpAvaOrMapCommand>{

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;
	
	@Override
	protected void handle(CommandHandlerContext<EmpAvaOrMapCommand> context) {
		EmpAvaOrMapCommand command = context.getCommand();
		this.empFileManagementRepository.update(PersonFileManagement.createFromJavaType(command.getEmployeeId(),
				command.getFileId(), command.getFileType(), null));
	}

}
