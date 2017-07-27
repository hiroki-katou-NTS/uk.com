package command.roles;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class PersonInfoRoleCommandHandler extends CommandHandler<PersonInfoRoleCommand>{

	@Override
	protected void handle(CommandHandlerContext<PersonInfoRoleCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
