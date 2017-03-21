package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@RequestScoped
@Transactional
public class UpdateItemMasterCommandHandler extends CommandHandler<UpdateItemMasterCommand> {

	@Override
	protected void handle(CommandHandlerContext<UpdateItemMasterCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
