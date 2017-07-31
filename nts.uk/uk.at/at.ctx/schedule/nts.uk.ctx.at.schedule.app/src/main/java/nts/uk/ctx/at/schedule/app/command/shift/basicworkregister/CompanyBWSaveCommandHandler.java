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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyBWSaveCommandHandler.
 */
@Stateless
public class CompanyBWSaveCommandHandler extends CommandHandler<CompanyBWSaveCommand> {

	/** The repository. */
	@Inject
	private CompanyBasicWorkRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanyBWSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id user login
		String companyId = loginUserContext.companyId();

		// Get Command
		CompanyBWSaveCommand command = context.getCommand();
		command.getCompanyBasicWork().setCompanyId(companyId);
		
//		// Get workdayDivision
//		Integer workdayDivision = command.getCompanyBasicWork().getBasicWorkSetting().get(0).getWorkDayDivision();
		
		// Find if exist
		Optional<CompanyBasicWork> optional = this.repository.findAll(companyId);
		
		// Convert to Domain
		CompanyBasicWork companyBasicWork = command.toDomain();

//		// Validate
//		companyBasicWork.validate();

		// Check exist
		if (optional.isPresent()) {
			this.repository.update(companyBasicWork);
		} else {
			this.repository.insert(companyBasicWork);
		}
	}

}
