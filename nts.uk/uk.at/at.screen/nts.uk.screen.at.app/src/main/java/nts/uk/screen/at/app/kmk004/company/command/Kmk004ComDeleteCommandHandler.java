/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.company.DelComMonthCalSetCommand;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.company.DelComMonthCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.DeleteComStatWorkTimeSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.DeleteComStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004ComDeleteCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004ComDeleteCommandHandler extends CommandHandler<Kmk004ComDeleteCommand> {

	/** The save com stat work time set command handler. */
	@Inject
	private DeleteComStatWorkTimeSetCommandHandler delStatCommand;

	/** The del flex command. */
	@Inject
	private DelComMonthCalSetCommandHandler delMonthCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004ComDeleteCommand> context) {
		Kmk004ComDeleteCommand deleteCommand = context.getCommand();

		DeleteComStatWorkTimeSetCommand delStatCommand = new DeleteComStatWorkTimeSetCommand();
		DelComMonthCalSetCommand delMonthCommand = new DelComMonthCalSetCommand();
		delStatCommand.setYear(deleteCommand.getYear());
		this.delStatCommand.handle(delStatCommand);
		this.delMonthCommand.handle(delMonthCommand);
	}
}
