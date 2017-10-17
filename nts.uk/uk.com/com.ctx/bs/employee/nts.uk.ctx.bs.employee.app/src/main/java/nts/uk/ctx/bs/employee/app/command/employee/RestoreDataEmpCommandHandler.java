package nts.uk.ctx.bs.employee.app.command.employee;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class RestoreDataEmpCommandHandler extends CommandHandler<EmployeeDeleteToRestoreCommand>{

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteToRestoreCommand> context) {
		
		EmployeeDeleteToRestoreCommand command = context.getCommand();
	}

}
