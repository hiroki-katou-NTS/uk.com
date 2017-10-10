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
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ActualLockSaveCommandHandler.
 */
@Stateless
public class ActualLockSaveCommandHandler extends CommandHandler<ActualLockSaveCommand>{

	/** The repository. */
	@Inject
	private ActualLockRepository repository;
	
//	/** The Constant ADD. */
// 	public static final int ADD = 1;
//	 
// 	/** The Constant UPDATE. */
// 	public static final int UPDATE = 2;

	/* (non-Javadoc)
 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
 */
@Override
	protected void handle(CommandHandlerContext<ActualLockSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		ActualLockSaveCommand command = context.getCommand();
		
		ActualLock actualLock = new ActualLock(command);
		
		// Find exist actualLock
		Optional<ActualLock> optionActualLock = this.repository.findById(companyId, command.getClosureId().value);
		
		// Check exist actualLock
		if (optionActualLock.isPresent()) {
			// Update
			this.repository.update(actualLock);
			return;
		}
		// Create
		this.repository.add(actualLock);
	}
}
