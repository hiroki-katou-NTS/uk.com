package nts.uk.ctx.at.record.app.command.divergence.time;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;

// TODO: Auto-generated Javadoc
/**
 * The Class DivergenceTimeSaveCommandHandler.
 */
public class DivergenceTimeSaveCommandHandler extends CommandHandler<DivergenceTimeSaveCommand> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DivergenceTimeSaveCommand> context) {
		DivergenceTimeSaveCommand command = context.getCommand();

		DivergenceTime divergenceTimeDomain = new DivergenceTime(command);
		// DivergenceReasonInputMethod divergenceReasonInputMethod = new
		// DivergenceReasonInputMethod(command);
	}

}
