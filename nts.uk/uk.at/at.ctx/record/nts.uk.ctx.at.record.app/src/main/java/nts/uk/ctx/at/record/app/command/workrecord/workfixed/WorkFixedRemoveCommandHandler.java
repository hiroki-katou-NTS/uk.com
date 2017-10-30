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
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;

/**
 * The Class WorkFixedRemoveCommandHandler.
 */
@Stateless
public class WorkFixedRemoveCommandHandler extends CommandHandler<WorkFixedRemoveCommand> {
	
	/** The workfixed repository. */
	@Inject
	private  WorkfixedRepository workfixedRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkFixedRemoveCommand> context) {
		
		// get command
		WorkFixedRemoveCommand command = context.getCommand();
		
		Optional<WorkFixed> optWorkFixed = this.workfixedRepository.findByWorkPlaceIdAndClosureId(command.getWorkPlaceId(), command.getClosureId());
		if(!optWorkFixed.isPresent()){
			return;
		}
		this.workfixedRepository.remove(command.getWorkPlaceId(), command.getClosureId());
	}
}
