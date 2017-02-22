package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@RequestScoped
@Transactional
public class AddItemMasterCommandHandler extends CommandHandler<AddItemMasterCommand> {

	@Override
	protected void handle(CommandHandlerContext<AddItemMasterCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
