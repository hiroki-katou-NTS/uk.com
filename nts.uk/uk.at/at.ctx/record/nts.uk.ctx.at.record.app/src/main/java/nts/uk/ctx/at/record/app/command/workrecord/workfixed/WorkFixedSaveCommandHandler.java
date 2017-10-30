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
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkFixedSaveCommandHandler.
 */
@Stateless
public class WorkFixedSaveCommandHandler extends CommandHandler<WorkFixedSaveCommand> {
	
	/** The repository. */
	@Inject
	private WorkfixedRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkFixedSaveCommand> context) {
		
		// get Person Id
		String personId = AppContexts.user().personId();
		
		// get current date
		GeneralDate fixedDate = GeneralDate.today();
	
		// get command
		WorkFixedSaveCommand command = context.getCommand();	
		
		// set person id and fixed date to work fixed
		WorkFixed workFixed = command.toDomain(personId, fixedDate);
		
		//find exist work fixed
		Optional<WorkFixed> optionalWorkFixed = this.repository.findByWorkPlaceIdAndClosureId(command.getWkpId(), command.getClosureId());
		
		// check exist work fixed 
		if(optionalWorkFixed.isPresent()){
			// update 
			this.repository.update(workFixed);
			return;
		}
		this.repository.add(workFixed);
		
	}
}
