package nts.uk.ctx.pereg.app.command.optional;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommandHandler;

@Stateless
public class AddOptionalCommandHandler extends CommandHandler<PeregUserDefAddCommand>
		implements PeregUserDefAddCommandHandler {

	@Override
	protected void handle(CommandHandlerContext<PeregUserDefAddCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
