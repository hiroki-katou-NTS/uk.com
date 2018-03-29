/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employment.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment.SaveEmpMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew.SaveEmpStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004EmpSaveCommandHanlder.
 */
@Stateless
@Transactional
public class Kmk004EmpSaveCommandHandler extends CommandHandler<Kmk004EmpSaveCommand> {

	/** The save stat command. */
	@Inject
	private SaveEmpStatWorkTimeSetCommandHandler saveStatCommand;

	/** The save month command. */
	@Inject
	private SaveEmpMonthCalSetCommandHandler saveMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004EmpSaveCommand> context) {
		Kmk004EmpSaveCommand addCommand = context.getCommand();
		this.saveStatCommand.handle(addCommand.getSaveStatCommand());
		this.saveMonthCommand.handle(addCommand.getSaveMonthCommand());
	}

}
