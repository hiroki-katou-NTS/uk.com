package nts.uk.ctx.pereg.app.command.roles.auth.functionauth.delete;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteFuncAuthCommandHandler extends CommandHandler<DeleteFuncAuthCommand> {

	@Inject
	private PersonInfoAuthorityRepository personInfoAuthRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteFuncAuthCommand> context) {
		String companyId = AppContexts.user().companyId();
		String roleId = context.getCommand().getRoleId();
		personInfoAuthRepo.delete(companyId, roleId);
	}

}
