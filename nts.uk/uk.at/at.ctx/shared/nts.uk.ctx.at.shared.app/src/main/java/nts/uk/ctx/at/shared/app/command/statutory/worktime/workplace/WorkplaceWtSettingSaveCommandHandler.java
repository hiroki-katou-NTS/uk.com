/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class WorkplaceWtSettingSaveCommandHandler.
 */
@Stateless
public class WorkplaceWtSettingSaveCommandHandler extends CommandHandler<WorkplaceWtSettingSaveCommand> {

	/** The repository. */
//	@Inject
//	private WorkPlaceWtSettingRepository repository;

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
//		WorkplaceWtSettingSaveCommand command = context.getCommand();
//
//		WorkPlaceWtSetting workPlaceWtSetting = new WorkPlaceWtSetting(command);
//
//		Optional<WorkPlaceWtSetting> optCompanySetting = this.repository.find(AppContexts.user().companyId(),
//				command.getYear().v(), command.getWorkPlaceId());
//		// Update
//		if (optCompanySetting.isPresent()) {
//			this.repository.update(workPlaceWtSetting);
//			return;
//		}
//		// Create
//		this.repository.create(workPlaceWtSetting);
	}

}
