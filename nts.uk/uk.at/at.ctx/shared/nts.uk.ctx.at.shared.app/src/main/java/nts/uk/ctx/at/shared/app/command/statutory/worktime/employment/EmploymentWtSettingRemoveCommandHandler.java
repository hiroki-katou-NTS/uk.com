/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingRemoveCommandHandler.
 */
@Stateless
@Transactional
public class EmploymentWtSettingRemoveCommandHandler extends CommandHandler<EmploymentWtSettingRemoveCommand> {

	/** The repository. */
	@Inject
	private EmploymentWtSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmploymentWtSettingRemoveCommand> context) {
		// Get Command.
		EmploymentWtSettingRemoveCommand command = context.getCommand();
		// Remove.
		this.repository.remove(AppContexts.user().companyId(), command.getYear(), command.getEmploymentCode());
	}

}
