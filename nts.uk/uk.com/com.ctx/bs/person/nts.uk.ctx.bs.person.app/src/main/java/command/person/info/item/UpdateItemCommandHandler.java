package command.person.info.item;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class UpdateItemCommandHandler extends CommandHandler<UpdateItemCommand> {

	@Override
	protected void handle(CommandHandlerContext<UpdateItemCommand> context) {
		
	}

}
