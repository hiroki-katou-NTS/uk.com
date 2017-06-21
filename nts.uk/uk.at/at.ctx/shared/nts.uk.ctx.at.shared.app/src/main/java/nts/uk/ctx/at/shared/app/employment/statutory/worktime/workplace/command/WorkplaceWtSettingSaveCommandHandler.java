/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.workplace.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceWtSettingSaveCommandHandler.
 */
@Stateless
public class WorkplaceWtSettingSaveCommandHandler extends CommandHandler<WorkplaceWtSettingSaveCommand> {

	/** The repository. */
	@Inject
	private WorkPlaceWtSettingRepository repository;

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
	@Transactional
	protected void handle(CommandHandlerContext<WorkplaceWtSettingSaveCommand> context) {
		// Get Command
		WorkplaceWtSettingSaveCommand command = context.getCommand();

		WorkPlaceWtSetting workPlaceWtSetting = new WorkPlaceWtSetting(command);

		// Validate
		workPlaceWtSetting.validate();

		// Update
		Optional<WorkPlaceWtSetting> optCompanySetting = this.repository.find(companyId, command.getYear().v(),
				command.getWorkPlaceId());
		if (optCompanySetting.isPresent()) {
			this.repository.update(workPlaceWtSetting);
		} else {
			this.repository.create(workPlaceWtSetting);
		}
	}

}
