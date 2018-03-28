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
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace.SaveWkpMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew.SaveWkpStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004WkpSaveCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004WkpSaveCommandHandler extends CommandHandler<Kmk004WkpSaveCommand> {

	/** The save stat command. */
	@Inject
	private SaveWkpStatWorkTimeSetCommandHandler saveStatCommand;

	/** The save month command. */
	@Inject
	private SaveWkpMonthCalSetCommandHandler saveMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004WkpSaveCommand> context) {
		Kmk004WkpSaveCommand addCommand = context.getCommand();
		this.saveStatCommand.handle(addCommand.getSaveStatCommand());
		this.saveMonthCommand.handle(addCommand.getSaveMonthCommand());
	}
}
