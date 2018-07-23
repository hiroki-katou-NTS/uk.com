package nts.uk.ctx.sys.auth.app.command.registration.user;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;

@Stateless
@Transactional
public class DeleteRegistrationUserCommandHandler extends CommandHandlerWithResult<DeleteRegistrationUserCommand, String> {

	@Override
	protected String handle(CommandHandlerContext<DeleteRegistrationUserCommand> context) {
		// TODO Auto-generated method stub
		return null;
	}


}
