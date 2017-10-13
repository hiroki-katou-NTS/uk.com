package nts.uk.ctx.bs.employee.app.command.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;

@Stateless
public class EmployeeDeleteCommandHandler extends CommandHandler<EmployeeDeleteCommand>{
	
	/** The repository. */
	@Inject
	private EmploymentRepository repository;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteCommand> context) {
		
		//get command
		EmployeeDeleteCommand command = context.getCommand();
		
	}

}
