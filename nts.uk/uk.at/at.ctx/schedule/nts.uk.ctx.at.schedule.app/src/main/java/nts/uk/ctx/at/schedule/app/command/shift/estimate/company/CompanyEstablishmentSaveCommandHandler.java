/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyEstablishmentSaveCommandHandler.
 */
@Stateless
public class CompanyEstablishmentSaveCommandHandler
		extends CommandHandler<CompanyEstablishmentSaveCommand> {
	
	/** The repository. */
	@Inject
	private CompanyEstablishmentRepository repository;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanyEstablishmentSaveCommand> context) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		CompanyEstablishmentSaveCommand command = context.getCommand();
		
		// get domain
		CompanyEstablishment domain = command.toDomain(companyId);
		
		// save to domain
		this.repository.saveCompanyEstablishment(domain);
	}

}
