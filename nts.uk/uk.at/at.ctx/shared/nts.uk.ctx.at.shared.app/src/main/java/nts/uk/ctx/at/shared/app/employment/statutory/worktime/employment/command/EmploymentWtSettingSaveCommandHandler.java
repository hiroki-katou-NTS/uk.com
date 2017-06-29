/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.employment.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentSaveCommandHandler.
 */
@Stateless
@Transactional
public class EmploymentWtSettingSaveCommandHandler extends CommandHandler<EmploymentWtSettingSaveCommand> {

	/** The repository. */
	@Inject
	private EmploymentWtSettingRepository repository;

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
	protected void handle(CommandHandlerContext<EmploymentWtSettingSaveCommand> context) {
		// Get Command
		EmploymentWtSettingSaveCommand command = context.getCommand();

		EmploymentWtSetting companySetting = new EmploymentWtSetting(command);

		// Update
		Optional<EmploymentWtSetting> optCompanySetting = this.repository.find(companyId, command.getYear().v(),
				command.getEmploymentCode());
		if (optCompanySetting.isPresent()) {
			this.repository.update(companySetting);
		} else {
			this.repository.create(companySetting);
		}
	}

}
