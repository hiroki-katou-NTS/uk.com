package command.roles.auth.item;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
@Stateless
@Transactional
public class PersonInfoItemAuthCommandHandler extends CommandHandler<PersonInfoItemAuthCommand> {

	@Override
	protected void handle(CommandHandlerContext<PersonInfoItemAuthCommand> context) {
	}

}
