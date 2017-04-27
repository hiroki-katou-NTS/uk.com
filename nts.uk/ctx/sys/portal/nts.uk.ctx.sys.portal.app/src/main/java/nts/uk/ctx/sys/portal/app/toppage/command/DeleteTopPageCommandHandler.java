package nts.uk.ctx.sys.portal.app.toppage.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;

/**
 * The Class DeleteTopPageCommandHandler.
 */
@Stateless
public class DeleteTopPageCommandHandler extends CommandHandler<DeleteTopPageCommand> {

	/** The top page repository. */
	@Inject
	TopPageRepository topPageRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteTopPageCommand> context) {
		DeleteTopPageCommand command = context.getCommand();
		//remove
		topPageRepository.remove(command.getCompanyId(),command.getTopPageCode());
	}

}
