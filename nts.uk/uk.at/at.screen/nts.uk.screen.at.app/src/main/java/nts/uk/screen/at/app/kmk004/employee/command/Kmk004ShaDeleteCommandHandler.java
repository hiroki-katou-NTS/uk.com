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
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee.DelShaMonthCalSetCommand;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee.DelShaMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew.DeleteShainStatWorkTimeSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew.DeleteShainStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004ShaDeleteCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004ShaDeleteCommandHandler extends CommandHandler<Kmk004ShaDeleteCommand> {

	/** The del stat command. */
	@Inject
	private DeleteShainStatWorkTimeSetCommandHandler delStatCommand;

	/** The del month command. */
	@Inject
	private DelShaMonthCalSetCommandHandler delMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004ShaDeleteCommand> context) {
		Kmk004ShaDeleteCommand deleteCommand = context.getCommand();
		
		DeleteShainStatWorkTimeSetCommand delStatCommand = new DeleteShainStatWorkTimeSetCommand();
		DelShaMonthCalSetCommand delMonthCommand = new DelShaMonthCalSetCommand();
		
		deleteCommand.setWTSettingCommonRemove(false);
		delStatCommand.setYear(deleteCommand.getYear());
		delStatCommand.setEmployeeId(deleteCommand.getSid());
		delMonthCommand.setSid(deleteCommand.getSid());
		
		this.delStatCommand.handle(delStatCommand);
		if (!delStatCommand.isOverOneYear()) {
			this.delMonthCommand.handle(delMonthCommand);
			deleteCommand.setWTSettingCommonRemove(true);
		}	
	}
}
