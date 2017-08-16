package command.person.info.item;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class AddItemCommandHandler extends CommandHandler<AddItemCommand> {

	@Override
	protected void handle(CommandHandlerContext<AddItemCommand> context) {
		//AddItemCommand addItemCommand = context.getCommand();
		
	}

	

}
