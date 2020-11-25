/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingRemoveCommandHandler.
 */
@Stateless
@Transactional
public class CompanyWtSettingRemoveCommandHandler extends CommandHandler<CompanyWtSettingRemoveCommand> {

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanyWtSettingRemoveCommand> context) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();
		// Get Command
		CompanyWtSettingRemoveCommand command = context.getCommand();
		
		monthlyWorkTimeSetRepo.removeCompany(companyId, command.getYear());
	}

}
