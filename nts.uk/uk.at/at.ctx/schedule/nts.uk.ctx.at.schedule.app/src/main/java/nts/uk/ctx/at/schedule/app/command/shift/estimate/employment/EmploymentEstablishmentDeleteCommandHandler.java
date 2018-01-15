/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishmentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentEstablishmentDeleteCommandHandler.
 */
@Stateless
public class EmploymentEstablishmentDeleteCommandHandler
		extends CommandHandler<EmploymentEstablishmentDeleteCommand> {
	
	/** The repository. */
	@Inject
	private EmploymentEstablishmentRepository repository;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmploymentEstablishmentDeleteCommand> context) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		EmploymentEstablishmentDeleteCommand command = context.getCommand();
		
		// call repository remove data
		this.repository.removeEmploymentEstablishment(companyId, command.getEmploymentCode(),
				command.getTargetYear());
	}

}
