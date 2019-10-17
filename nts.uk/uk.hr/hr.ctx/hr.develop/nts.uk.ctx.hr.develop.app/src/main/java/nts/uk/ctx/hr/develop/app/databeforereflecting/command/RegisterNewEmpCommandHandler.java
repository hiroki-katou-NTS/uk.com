package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import java.time.LocalDate;
import java.time.Period;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;

@Stateless
public class RegisterNewEmpCommandHandler extends CommandHandler<DataBeforeReflectCommand> {

	@Override
	protected void handle(CommandHandlerContext<DataBeforeReflectCommand> context) {
		DataBeforeReflectCommand command = context.getCommand();

	}

	
}
