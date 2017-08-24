/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.personal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishmentRepository;

/**
 * The Class PersonalEstablishmentSaveCommandHandler.
 */
@Stateless
public class PersonalEstablishmentSaveCommandHandler
		extends CommandHandler<PersonalEstablishmentSaveCommand> {
	
	/** The repository. */
	@Inject
	private PersonalEstablishmentRepository repository;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<PersonalEstablishmentSaveCommand> context) {
		
		// get command
		PersonalEstablishmentSaveCommand command = context.getCommand();
		
		// get domain
		PersonalEstablishment domain = command.toDomain();
		
		// save to domain
		this.repository.savePersonalEstablishment(domain);
	}

}
