/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingRemoveCommandHandler.
 */
@Stateless
@Transactional
public class CompanySettingRemoveCommandHandler extends CommandHandler<CompanySettingRemoveCommand> {

	/** The repository. */
	@Inject
	private CompanySettingRepository repository;

	/** The company id. */
	String companyId = AppContexts.user().companyId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanySettingRemoveCommand> context) {
		// Get Command
		CompanySettingRemoveCommand command = context.getCommand();
		this.repository.remove(companyId, command.getYear());
	}

}
