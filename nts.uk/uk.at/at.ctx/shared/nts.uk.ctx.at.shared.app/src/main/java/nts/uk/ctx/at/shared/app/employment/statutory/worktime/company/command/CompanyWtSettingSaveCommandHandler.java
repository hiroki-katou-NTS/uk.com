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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentSaveCommandHandler.
 */
@Stateless
@Transactional
public class CompanyWtSettingSaveCommandHandler extends CommandHandler<CompanyWtSettingSaveCommand> {

	/** The repository. */
	@Inject
	private CompanyWtSettingRepository repository;

	/** The company id. */
	String companyId = AppContexts.user().companyId();

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanyWtSettingSaveCommand> context) {
		// Get Command
		CompanyWtSettingSaveCommand command = context.getCommand();

		CompanyWtSetting companySetting = new CompanyWtSetting(command);

		// Validate
		companySetting.validate();

		// Update
		Optional<CompanyWtSetting> optCompanySetting = this.repository.find(companyId, command.getYear().v());
		if(optCompanySetting.isPresent()) {
			this.repository.update(companySetting);
		}
		else {
			this.repository.create(companySetting);
		}
	}

}
