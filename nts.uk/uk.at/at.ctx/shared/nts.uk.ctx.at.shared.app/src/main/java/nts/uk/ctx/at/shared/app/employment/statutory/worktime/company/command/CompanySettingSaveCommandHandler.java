/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentSaveCommandHandler.
 */
@Stateless
@Transactional
public class CompanySettingSaveCommandHandler extends CommandHandler<CompanySettingSaveCommand> {

	/** The repository. */
	@Inject
	private CompanySettingRepository repository;

	/** The company id. */
	String companyId = AppContexts.user().companyId();

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanySettingSaveCommand> context) {
		// Get Command
		CompanySettingSaveCommand command = context.getCommand();

		CompanySetting companySetting = new CompanySetting(command);

		// Validate
		companySetting.validate();

		// Update
		Optional<CompanySetting> optCompanySetting = this.repository.find(companyId);
		if(optCompanySetting.isPresent()) {
			this.repository.update(companySetting);
		}
		else {
			this.repository.create(companySetting);
		}
	}

}
