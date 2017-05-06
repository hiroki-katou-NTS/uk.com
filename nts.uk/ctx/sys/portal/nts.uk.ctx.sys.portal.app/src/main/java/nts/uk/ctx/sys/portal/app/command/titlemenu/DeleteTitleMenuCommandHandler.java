package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteTitleMenuCommandHandler extends CommandHandler<DeleteTitleMenuCommand> {
	@Inject
	private TitleMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DeleteTitleMenuCommand> context) {
		String companyID = AppContexts.user().companyID();
		repository.remove(companyID, context.getCommand().getTitleMenuCD());
	}
}