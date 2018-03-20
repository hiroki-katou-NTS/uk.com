package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class DivergenceReasonSelectSaveCommandHandler.
 */
@Stateless
public class DivergenceReasonSelectSaveCommandHandler extends CommandHandler<DivergenceReasonSelectSaveCommand> {

	/** The div reason select repo. */
	@Inject
	DivergenceReasonSelectRepository divReasonSelectRepo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DivergenceReasonSelectSaveCommand> context) {
		// get command

		DivergenceReasonSelectSaveCommand command = context.getCommand();

		// convert to domain
		DivergenceReasonSelect domain = new DivergenceReasonSelect(command);

		// Save

		this.divReasonSelectRepo.update(domain);

	}

}
