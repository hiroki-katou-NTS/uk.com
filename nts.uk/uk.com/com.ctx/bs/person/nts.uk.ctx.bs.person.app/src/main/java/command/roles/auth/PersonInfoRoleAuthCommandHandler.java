package command.roles.auth;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class PersonInfoRoleAuthCommandHandler extends CommandHandler<PersonInfoRoleAuthCommand>{

	@Override
	protected void handle(CommandHandlerContext<PersonInfoRoleAuthCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
