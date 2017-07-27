package command.roles.auth.category;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
@Stateless
@Transactional
public class PersonInfoCategoryAuthCommandHandler extends CommandHandler<PersonInfoCategoryAuthCommand> {

	@Override
	protected void handle(CommandHandlerContext<PersonInfoCategoryAuthCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
