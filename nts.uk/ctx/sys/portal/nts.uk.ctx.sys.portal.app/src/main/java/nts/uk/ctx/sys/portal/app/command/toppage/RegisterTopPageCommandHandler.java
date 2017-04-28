package nts.uk.ctx.sys.portal.app.command.toppage;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;

/**
 * The Class RegisterTopPageCommandHandler.
 */
@Stateless
public class RegisterTopPageCommandHandler extends CommandHandler<RegisterTopPageCommand> {

	/** The top page repository. */
	@Inject
	TopPageRepository topPageRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterTopPageCommand> context) {
		RegisterTopPageCommand command = context.getCommand();
		// to Domain
		TopPage topPage = command.toDomain();
		// add
		topPageRepository.add(topPage);
	}

}
