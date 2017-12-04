/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.workfixed;

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
public class SaveWorkFixedCommandHandler extends CommandHandler<SaveWorkFixedCommand> {

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
	protected void handle(CommandHandlerContext<SaveWorkFixedCommand> context) {

		// Get Person Id
		String personId = AppContexts.user().personId();

		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get command
		SaveWorkFixedCommand command = context.getCommand();

		// Set PersonId and FixedDate to WorkFixed command
		WorkFixed workFixed = new WorkFixed();
		if (Integer.valueOf(ConfirmClsStatus.Pending.value).equals(command.getConfirmClsStatus())) {
			// Remove FixedDate and PersonId if status = Pending (unchecked)
			workFixed = command.toDomain(companyId, null, null);
		} else {
			workFixed = command.toDomain(companyId, personId, GeneralDate.today());
		}		

		// Save/Update new WorkFixed
		if (command.getIsEdited()) {
			this.repository.update(workFixed);
			return;
		}
		this.repository.add(workFixed);
	}
}
