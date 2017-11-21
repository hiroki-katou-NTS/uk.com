package nts.uk.ctx.pereg.app.command.optional;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommandHandler;

@Stateless
public class UpdateOptionalCommandHandler extends CommandHandler<PeregUserDefUpdateCommand>
		implements PeregUserDefUpdateCommandHandler {

	@Override
	protected void handle(CommandHandlerContext<PeregUserDefUpdateCommand> context) {
		// TODO Auto-generated method stub
		
	}


}
