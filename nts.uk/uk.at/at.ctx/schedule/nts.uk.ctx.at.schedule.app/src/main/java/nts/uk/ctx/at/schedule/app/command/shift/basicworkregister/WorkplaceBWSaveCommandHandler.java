/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;

/**
 * The Class WorkplaceBWSaveCommandHandler.
 */
@Stateless
public class WorkplaceBWSaveCommandHandler extends CommandHandler<WorkplaceBWSaveCommand> {

	/** The repository. */
	@Inject
	private WorkplaceBasicWorkRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkplaceBWSaveCommand> context) {

		// Get Command
		WorkplaceBWSaveCommand command = context.getCommand();
		
		// Get Workplace Id
		String workplaceId = command.getWorkPlaceId().v();

		Optional<WorkplaceBasicWork> optional = this.repository.findById(workplaceId);
		
		// Convert to Domain
		WorkplaceBasicWork workplaceBasicWork = new WorkplaceBasicWork(command);
		
		// Check if exist
		if(optional.isPresent()) {
			this.repository.update(workplaceBasicWork);
		} else {
			this.repository.insert(workplaceBasicWork);
		}
	}
}
