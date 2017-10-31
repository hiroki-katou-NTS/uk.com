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
public class WorkFixedCommandHandler extends CommandHandler<WorkFixedCommand> {
	
	/** The repository. */
	@Inject
	private WorkfixedRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkFixedCommand> context) {
		
		// get Person Id
		String personId = AppContexts.user().personId();
		
		// get current date
		GeneralDate fixedDate = GeneralDate.today();
	
		// get command
		WorkFixedCommand command = context.getCommand();	
		
		// set person id and fixed date to work fixed
		WorkFixed workFixed = command.toDomain(personId, fixedDate);
		
		//find exist work fixed
		Optional<WorkFixed> opWorkFixed = this.repository.findByWorkPlaceIdAndClosureId(command.getWkpId(), command.getClosureId());
		
		if (command.getIsSave()) {
			// Save new WorkFixed
			if(opWorkFixed.isPresent()){
				//TODO: No need for updating old WorkFixed?
				//this.repository.update(workFixed);
				return;
			}
			this.repository.add(workFixed);
		} else {			
			// Remove WorkFixed			
			if(!opWorkFixed.isPresent()){
				return;
			}
			this.repository.remove(command.getWkpId(), command.getClosureId());
		}	
	}
}
