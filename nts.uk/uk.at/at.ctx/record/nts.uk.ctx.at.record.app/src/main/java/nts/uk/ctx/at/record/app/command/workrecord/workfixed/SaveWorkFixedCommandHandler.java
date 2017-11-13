/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.workfixed;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkFixedSaveCommandHandler.
 */
@Stateless
public class SaveWorkFixedCommandHandler extends CommandHandler<WorkFixedCommand> {

	/** The repository. */
	@Inject
	private WorkfixedRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkFixedCommand> context) {

		// Get PersonId
		String personId = AppContexts.user().personId();

		// Get command
		WorkFixedCommand command = context.getCommand();

		// Set PersonId and FixedDate to WorkFixed command
		WorkFixed workFixed = command.toDomain(personId, GeneralDate.today());

		// Find exist WorkFixed
		Optional<WorkFixed> opWorkFixed = this.repository.findByWorkPlaceIdAndClosureId(command.getWkpId(),
				command.getClosureId(), command.getCid());

		// Save new WorkFixed
		if (ConfirmClsStatus.Confirm.value == command.getConfirmClsStatus().intValue()) {
			if (opWorkFixed.isPresent()) {
				this.repository.update(workFixed);
				return;
			}
			this.repository.add(workFixed);
		}
		// Update WorkFixed
		else {
			if (!opWorkFixed.isPresent()) {
				return;
			}
			this.repository.update(workFixed);
		}
	}
}
