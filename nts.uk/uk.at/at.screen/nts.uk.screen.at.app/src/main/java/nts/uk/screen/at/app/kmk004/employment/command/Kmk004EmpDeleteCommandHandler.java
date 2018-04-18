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
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment.DelEmpMonthCalSetCommand;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment.DelEmpMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew.DeleteEmpStatWorkTimeSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew.DeleteEmpStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004EmpDeleteCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004EmpDeleteCommandHandler extends CommandHandler<Kmk004EmpDeleteCommand> {

	/** The del stat command. */
	@Inject
	private DeleteEmpStatWorkTimeSetCommandHandler delStatCommand;

	/** The del month command. */
	@Inject
	private DelEmpMonthCalSetCommandHandler delMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004EmpDeleteCommand> context) {
		Kmk004EmpDeleteCommand deleteCommand = context.getCommand();

		DeleteEmpStatWorkTimeSetCommand delStatCommand = new DeleteEmpStatWorkTimeSetCommand();
		DelEmpMonthCalSetCommand delMonthCommand = new DelEmpMonthCalSetCommand();
		
		deleteCommand.setWTSettingCommonRemove(false);
		delStatCommand.setYear(deleteCommand.getYear());
		delStatCommand.setEmploymentCode(deleteCommand.getEmploymentCode());
		delMonthCommand.setEmpCode(deleteCommand.getEmploymentCode());
				
		this.delStatCommand.handle(delStatCommand);
		if (!delStatCommand.isOverOneYear()) {
			this.delMonthCommand.handle(delMonthCommand);
			deleteCommand.setWTSettingCommonRemove(true);
		}		
	}

}
