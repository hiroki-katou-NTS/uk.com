package command.roles.auth.category;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
@Stateless
@Transactional
public class UpdatePersonInfoCategoryAuthCommandHandler extends CommandHandler<UpdatePersonInfoCategoryAuthCommand> {

	@Override
	protected void handle(CommandHandlerContext<UpdatePersonInfoCategoryAuthCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
