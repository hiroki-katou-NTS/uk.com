package nts.uk.ctx.pereg.app.command.optional;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommandHandler;

@Stateless
public class DeleteOptionalCommandHandler extends CommandHandler<PeregUserDefDeleteCommand>
		implements PeregUserDefDeleteCommandHandler {

	@Override
	protected void handle(CommandHandlerContext<PeregUserDefDeleteCommand> context) {
		// TODO Auto-generated method stub
		
	}


}
