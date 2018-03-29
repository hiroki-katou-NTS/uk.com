/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employee.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee.SaveShaMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew.SaveShainStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004ShaSaveCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004ShaSaveCommandHandler extends CommandHandler<Kmk004ShaSaveCommand> {

	/** The save stat command. */
	@Inject
	private SaveShainStatWorkTimeSetCommandHandler saveStatCommand;

	/** The save month command. */
	@Inject
	private SaveShaMonthCalSetCommandHandler saveMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004ShaSaveCommand> context) {
		Kmk004ShaSaveCommand addCommand = context.getCommand();
		
		this.saveStatCommand.handle(addCommand.getSaveStatCommand());
		this.saveMonthCommand.handle(addCommand.getSaveMonthCommand());
	}

}
