/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceWtSettingRemoveCommandHandler.
 */
@Stateless
public class WorkplaceWtSettingRemoveCommandHandler extends CommandHandler<WorkplaceWtSettingRemoveCommand> {

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
	protected void handle(CommandHandlerContext<WorkplaceWtSettingRemoveCommand> context) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		WorkplaceWtSettingRemoveCommand command = context.getCommand();
		this.repository.remove(companyId, command.getYear(), command.getWorkplaceId());
	}

}
