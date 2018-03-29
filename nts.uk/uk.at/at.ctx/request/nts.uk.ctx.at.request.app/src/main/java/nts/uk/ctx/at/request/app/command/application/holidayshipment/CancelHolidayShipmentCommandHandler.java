package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


@Stateless
public class CancelHolidayShipmentCommandHandler extends CommandHandler<SaveHolidayShipmentCommand> {

	@Inject
	private SaveHolidayShipmentCommandHandler saveHanler;

	@Override
	protected void handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {

	}

}
