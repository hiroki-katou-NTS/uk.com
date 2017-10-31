/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.actuallock;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistory;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ActualLockSaveCommandHandler.
 */
@Stateless
public class ActualLockSaveCommandHandler extends CommandHandlerWithResult<ActualLockSaveCommand, Boolean> {

	/** The repository. */
	@Inject
	private ActualLockRepository repository;

	@Inject
	private ActualLockHistoryRepository lockHistRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected Boolean handle(CommandHandlerContext<ActualLockSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();

		// Get Command
		ActualLockSaveCommand command = context.getCommand();

		ActualLock actualLock = new ActualLock(command);

		// Find exist actualLock
		Optional<ActualLock> optionActualLock = this.repository.findById(companyId, command.getClosureId().value);

		// Check exist actualLock
		if (optionActualLock.isPresent()) {
			
			// FindAll ActualLock By ClosureId
			List<ActualLockHistory> histList = this.lockHistRepo.findByClosureId(companyId,
					command.getClosureId().value);
			
			// Check Empty
			if (!CollectionUtil.isEmpty(histList)) {
				// Get Last Update ActualLock by ActualLockHist
				ActualLockHistory firstItem = histList.get(0);
				
				// Compare Setting of last Update to selected value on screen
				if ((firstItem.getDailyLockState().value == command.getDailyLockState().value)
						&& (firstItem.getMonthlyLockState().value == command.getMonthyLockState().value)) {
					return false;
				} 
			}
			
		} else {
			// Create ActualLock
			this.repository.add(actualLock);
		}
		
		// Update ActualLock
		this.repository.update(actualLock);
		
		ActualLockHistory actualLockHist = new ActualLockHistory(command);
		
		// Register ActualLockHist
		this.lockHistRepo.add(actualLockHist);
		
		// Return true
		return true;
	}
}
