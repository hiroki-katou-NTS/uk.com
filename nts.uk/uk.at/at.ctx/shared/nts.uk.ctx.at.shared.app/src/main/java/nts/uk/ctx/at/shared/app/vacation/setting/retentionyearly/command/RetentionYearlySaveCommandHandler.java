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
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class RetentionYearlySaveCommandHandler.
 */
@Stateless
public class RetentionYearlySaveCommandHandler extends CommandHandler<RetentionYearlySaveCommand> {

	/** The repository. */
	@Inject
	private RetentionYearlySettingRepository repository;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RetentionYearlySaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Get Command
		RetentionYearlySaveCommand command = context.getCommand();

		RetentionYearlySetting retentionYearlySetting = command.toDomain(companyId);

		// validate domain
		retentionYearlySetting.validate();

//		this.repository.update(retentionYearlySetting);
		
		Optional<RetentionYearlySetting> data = this.repository.findByCompanyId(retentionYearlySetting.getCompanyId());
		if (data.isPresent()) {
			this.repository.update(retentionYearlySetting);
		} else {
			this.repository.insert(retentionYearlySetting);
		}
	}
}
