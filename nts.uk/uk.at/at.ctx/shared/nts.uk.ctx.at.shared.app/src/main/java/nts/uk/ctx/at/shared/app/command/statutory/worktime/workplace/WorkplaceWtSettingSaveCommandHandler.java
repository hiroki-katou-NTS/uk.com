/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceWtSettingSaveCommandHandler.
 */
@Stateless
public class WorkplaceWtSettingSaveCommandHandler extends CommandHandler<WorkplaceWtSettingSaveCommand> {

	/** The repository. */
	@Inject
	private WorkPlaceWtSettingRepository repository;

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

		Optional<WorkPlaceWtSetting> optCompanySetting = this.repository.find(AppContexts.user().companyId(),
				command.getYear().v(), command.getWorkPlaceId());
		// Update
		if (optCompanySetting.isPresent()) {
			this.repository.update(workPlaceWtSetting);
			return;
		}
		// Create
		this.repository.create(workPlaceWtSetting);
	}

}
