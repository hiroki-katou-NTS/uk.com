/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.sixtyhours.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ComSubstVacationSaveCommandHandler.
 */
@Stateless
public class Emp60HourVacationSaveCommandHandler
		extends CommandHandler<Emp60HourVacationSaveCommand> {

	/** The repository. */
	@Inject
	private Emp60HourVacationRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Emp60HourVacationSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Get Command
		Emp60HourVacationSaveCommand command = context.getCommand();

		Emp60HourVacation com60HourVacation = command.toDomain(companyId);

		// Update into db
		this.repository.update(com60HourVacation);

	}

}
