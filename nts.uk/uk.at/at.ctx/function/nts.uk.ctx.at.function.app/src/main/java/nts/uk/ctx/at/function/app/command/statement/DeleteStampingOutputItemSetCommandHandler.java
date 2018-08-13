package nts.uk.ctx.at.function.app.command.statement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteStampingOutputItemSetCommandHandler.
 */
@Stateless
public class DeleteStampingOutputItemSetCommandHandler extends CommandHandler<DeleteStampingOutputItemSetCommand> {

	/** The stamping output item set repository. */
	@Inject
	private StampingOutputItemSetRepository stampingOutputItemSetRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteStampingOutputItemSetCommand> context) {
		DeleteStampingOutputItemSetCommand command = context.getCommand();
		
		this.stampingOutputItemSetRepository.removeByCidAndCode(AppContexts.user().companyId(), command.getCode());
	}

}
