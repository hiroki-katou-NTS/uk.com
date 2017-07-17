package nts.uk.ctx.sys.portal.app.command.personaltying;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTyingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeletePersonalTyingCommandHandler extends CommandHandler<DeletePersonalTyingCommand>{
	@Inject
	private PersonalTyingRepository respository;

	@Override
	protected void handle(CommandHandlerContext<DeletePersonalTyingCommand> context) {
		DeletePersonalTyingCommand command = context.getCommand();
		
		String companyId = AppContexts.user().companyId();
		respository.delete(companyId,command.getEmployeeId());
		
	}
}
