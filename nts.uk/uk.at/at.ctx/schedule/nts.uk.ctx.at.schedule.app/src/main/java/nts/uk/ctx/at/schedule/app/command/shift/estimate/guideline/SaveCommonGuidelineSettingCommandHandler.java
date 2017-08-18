/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.CommonGuidelineSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.CommonGuidelineSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCommonGuidelineSettingCommandHandler.
 */
@Stateless
public class SaveCommonGuidelineSettingCommandHandler
		extends CommandHandler<CommonGuidelineSettingCommand> {

	/** The common guideline setting repo. */
	@Inject
	private CommonGuidelineSettingRepository commonGuidelineSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CommonGuidelineSettingCommand> context) {
		// Get the current company id.
		String companyId = AppContexts.user().companyId();

		// Get command.
		CommonGuidelineSettingCommand command = context.getCommand();

		// Find exist setting.
		Optional<CommonGuidelineSetting> result = this.commonGuidelineSettingRepo
				.findByCompanyId(companyId);

		// Convert data.
		CommonGuidelineSetting commonGuidelineSetting = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.commonGuidelineSettingRepo.add(commonGuidelineSetting);
		} else {
			this.commonGuidelineSettingRepo.update(commonGuidelineSetting);
		}
	}

}
