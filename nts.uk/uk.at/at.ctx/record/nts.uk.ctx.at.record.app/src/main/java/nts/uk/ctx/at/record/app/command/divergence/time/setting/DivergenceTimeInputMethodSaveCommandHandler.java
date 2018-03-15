package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class DivergenceTimeInputMethodSaveCommandHandler.
 */
public class DivergenceTimeInputMethodSaveCommandHandler extends CommandHandler<DivergenceTimeInputMethodSaveCommand> {

	/** The divergence time repo. */
	@Inject
	DivergenceTimeRepository divergenceTimeRepo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DivergenceTimeInputMethodSaveCommand> context) {
		// get command
		// DivergenceTimeInputMethodSaveCommand command = context.getCommand();

		// DivergenceTimeInputMethod domain = new

		// convert to domain

		// update
		// this.divergenceTimeRepo.update(command);
	}

}
