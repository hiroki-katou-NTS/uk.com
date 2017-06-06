/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentSaveCommandHandler.
 */
@Stateless
public class EmploymentSaveCommandHandler extends CommandHandler<EmploymentSaveCommand> {

	/** The repository. */
	@Inject
	private EmploymentSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmploymentSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Get Command
		EmploymentSaveCommand command = context.getCommand();

		EmploymentSetting employmentSetting = command.toDomain(companyId);

		// Validate
		employmentSetting.validate();

		// Update
//		this.repository.update(employmentSetting);
		
		Optional<EmploymentSetting> data = this.repository.find(companyId, employmentSetting.getEmploymentCode());
		if(data.isPresent()) {
			this.repository.update(employmentSetting);
		}
		else {
			this.repository.insert(employmentSetting);
		}
	}

}
