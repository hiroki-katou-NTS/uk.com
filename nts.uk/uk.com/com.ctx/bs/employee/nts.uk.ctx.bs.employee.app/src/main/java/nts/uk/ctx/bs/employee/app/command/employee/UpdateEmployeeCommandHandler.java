package nts.uk.ctx.bs.employee.app.command.employee;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

public class UpdateEmployeeCommandHandler extends CommandHandler<UpdateEmployeeCommand>
	implements PeregUpdateCommandHandler<UpdateEmployeeCommand>{

	@Override
	public String targetCategoryId() {
		return "CS00002";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateEmployeeCommand> context) {
		val command = context.getCommand();
	}

}
