package nts.uk.ctx.sys.portal.app.command.layout;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;

/**
 * @author LamDT
 */
@Stateless
@Transactional
public class CreatePortalLayoutCommandHandler extends CommandHandler<CreatePortalLayoutCommand> {

	@Inject
	private LayoutRepository layoutRepository;

	@Override
	protected void handle(CommandHandlerContext<CreatePortalLayoutCommand> context) {		
		layoutRepository.add(context.getCommand().toDomain());
	}

}