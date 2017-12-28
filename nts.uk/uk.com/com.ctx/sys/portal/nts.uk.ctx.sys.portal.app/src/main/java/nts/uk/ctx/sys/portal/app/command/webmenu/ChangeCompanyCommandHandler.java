package nts.uk.ctx.sys.portal.app.command.webmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

@Stateless
public class ChangeCompanyCommandHandler extends CommandHandler<ChangeCompanyCommand> {

	@Inject
	private LoginUserContextManager contextManager;
	
	@Override
	protected void handle(CommandHandlerContext<ChangeCompanyCommand> context) {
		ChangeCompanyCommand command = context.getCommand();
		LoginUserContext userCtx = AppContexts.user();
		// TODO: Get companyCode, employeeId, employeeCode
//		contextManager.changeCompany(userCtx.userId(), userCtx.personId(), userCtx.contractCode(),
//				command.getCompanyId(), null, null, null);
	}

}
