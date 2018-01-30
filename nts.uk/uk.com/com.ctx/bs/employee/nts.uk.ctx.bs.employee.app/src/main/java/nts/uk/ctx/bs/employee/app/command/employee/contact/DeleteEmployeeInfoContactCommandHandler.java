package nts.uk.ctx.bs.employee.app.command.employee.contact;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteEmployeeInfoContactCommandHandler extends CommandHandler<DeleteEmployeeInfoContactCommand>
	implements PeregDeleteCommandHandler<DeleteEmployeeInfoContactCommand>{
	
	@Inject
	private EmployeeInfoContactRepository employeeInfoContactRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00023";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteEmployeeInfoContactCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteEmployeeInfoContactCommand> context) {
		
		val command = context.getCommand();
		employeeInfoContactRepository.delete(command.getSid());
		
	}


}
