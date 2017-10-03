/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.actuallock;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistory;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AcLockHistSaveCommandHandler.
 */
@Stateless
public class AcLockHistSaveCommandHandler extends CommandHandler<ActualLockHistSaveCommand>{

	/** The repository. */
	@Inject
	private ActualLockHistoryRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ActualLockHistSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();

		// Get Command
		ActualLockHistSaveCommand command = context.getCommand();

		ActualLockHistory actualLockHist = new ActualLockHistory(command);

		// Find exist actualLockHistory
		Optional<ActualLockHistory> optionActualLock = this.repository.findByLockDate(companyId,
				command.getClosureId().value, command.getLockDateTime());

		// Check exist actualLockHistory
		if (optionActualLock.isPresent()) {
			// Update
			this.repository.update(actualLockHist);
			return;
		}
		// Create
		this.repository.add(actualLockHist);
	}

}
