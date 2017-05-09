package nts.uk.ctx.sys.portal.app.command.toppage;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteTopPageCommandHandler.
 */
@Stateless
public class DeleteTopPageCommandHandler extends CommandHandler<DeleteTopPageCommand> {

	/** The top page repository. */
	@Inject
	TopPageRepository topPageRepository;
	
	@Inject
	TopPageService topPageService; 

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteTopPageCommand> context) {
		DeleteTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyID();
		topPageService.removeTopPage(command.getTopPageCode(), companyId);
	}

}
