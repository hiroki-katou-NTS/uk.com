/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.workplace.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace.DelWkpMonthCalSetCommand;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace.DelWkpMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew.DeleteWkpStatWorkTimeSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew.DeleteWkpStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004WkpDeleteCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004WkpDeleteCommandHandler extends CommandHandler<Kmk004WkpDeleteCommand> {

	/** The del stat command. */
	@Inject
	private DeleteWkpStatWorkTimeSetCommandHandler delStatCommand;

	/** The del month command. */
	@Inject
	private DelWkpMonthCalSetCommandHandler delMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004WkpDeleteCommand> context) {
		Kmk004WkpDeleteCommand deleteCommand = context.getCommand();

		DeleteWkpStatWorkTimeSetCommand delStatCommand = new DeleteWkpStatWorkTimeSetCommand();
		DelWkpMonthCalSetCommand delMonthCommand = new DelWkpMonthCalSetCommand();
		
		deleteCommand.setWTSettingCommonRemove(false);
		delStatCommand.setYear(deleteCommand.getYear());
		delStatCommand.setWorkplaceId(deleteCommand.getWorkplaceId());
		delMonthCommand.setWorkplaceId(deleteCommand.getWorkplaceId());
		
		this.delStatCommand.handle(delStatCommand);
		if (!delStatCommand.isOverOneYear()) {
			this.delMonthCommand.handle(delMonthCommand);
			deleteCommand.setWTSettingCommonRemove(true);
		}		
	}
	
}
