package nts.uk.ctx.at.function.app.command.statement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSet;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;

/**
 * The Class UpdateStampingOutputItemSetCommandHandler.
 */
@Stateless
public class UpdateStampingOutputItemSetCommandHandler extends CommandHandler<AddStampingOutputItemSetCommand> {

	/** The stamping output item set repository. */
	@Inject
	private StampingOutputItemSetRepository stampingOutputItemSetRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddStampingOutputItemSetCommand> context) {
		AddStampingOutputItemSetCommand command = context.getCommand();
		this.stampingOutputItemSetRepository.update(new StampingOutputItemSet(command));
	}

}
