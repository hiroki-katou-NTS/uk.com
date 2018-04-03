package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class ChangeAbsDateCommandHandler extends CommandHandler<SaveHolidayShipmentCommand> {

	@Override
	protected void handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {
		// TODO Auto-generated method stub

	}

}
