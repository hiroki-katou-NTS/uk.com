/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.personal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishmentRepository;

/**
 * The Class CompanyEstablishmentDeleteCommandHandler.
 */
@Stateless
public class PersonalEstablishmentDeleteCommandHandler
		extends CommandHandler<PersonalEstablishmentDeleteCommand> {
	
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
	protected void handle(CommandHandlerContext<PersonalEstablishmentDeleteCommand> context) {
		// get command
		PersonalEstablishmentDeleteCommand command = context.getCommand();

		// call repository remove data
		this.repository.removePersonalEstablishment(command.getEmployeeId(),
				command.getTargetYear());
	}

}
