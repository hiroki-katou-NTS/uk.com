package nts.uk.ctx.sys.portal.app.command.webmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveWebMenuCommandHander extends CommandHandler<RemoveWebMenuCommand> {
	
	@Inject
	private WebMenuRepository webMenuRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveWebMenuCommand> context) {
		
		RemoveWebMenuCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		webMenuRepository.remove(companyId, command.getWebMenuCd());
	}
	

}
