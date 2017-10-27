/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.usagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveUsageSettingCommandHandler.
 */
@Stateless
public class SaveUsageSettingCommandHandler extends CommandHandler<UsageSettingCommand> {

	/** The common guideline setting repo. */
	@Inject
	private UsageSettingRepository commonGuidelineSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UsageSettingCommand> context) {
		// Get the current company id.
		String companyId = AppContexts.user().companyId();

		// Get command.
		UsageSettingCommand command = context.getCommand();

		// Find exist setting.
		Optional<UsageSetting> result = this.commonGuidelineSettingRepo.findByCompanyId(companyId);

		// Convert data.
		UsageSetting domain = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.commonGuidelineSettingRepo.add(domain);
		} else {
			this.commonGuidelineSettingRepo.update(domain);
		}
	}

}
