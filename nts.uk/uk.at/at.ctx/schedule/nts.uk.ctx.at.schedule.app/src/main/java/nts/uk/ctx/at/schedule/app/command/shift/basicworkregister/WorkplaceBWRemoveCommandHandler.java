/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;

/**
 * The Class WorkplaceBWRemoveCommandHandler.
 */
@Stateless
public class WorkplaceBWRemoveCommandHandler extends CommandHandler<WorkplaceBWRemoveCommand> {

	/** The repository. */
	@Inject
	private WorkplaceBasicWorkRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkplaceBWRemoveCommand> context) {
		// Get Command
		WorkplaceBWRemoveCommand command = context.getCommand();

		// Get WorkplaceId
		String workplaceId = command.getWorkplaceId();

		// Remove
		this.repository.remove(workplaceId);
	}
}
