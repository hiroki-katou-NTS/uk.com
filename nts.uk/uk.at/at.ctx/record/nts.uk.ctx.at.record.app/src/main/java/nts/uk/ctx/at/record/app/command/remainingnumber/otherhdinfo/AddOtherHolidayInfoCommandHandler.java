package nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

public class AddOtherHolidayInfoCommandHandler extends CommandHandlerWithResult<AddOtherHolidayInfoCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<AddOtherHolidayInfoCommand> {

	@Override
	public String targetCategoryCd() {
		return "CS00035";
	}

	@Override
	public Class<?> commandClass() {
		return AddOtherHolidayInfoCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddOtherHolidayInfoCommand> context) {
		return null;
	}

}
