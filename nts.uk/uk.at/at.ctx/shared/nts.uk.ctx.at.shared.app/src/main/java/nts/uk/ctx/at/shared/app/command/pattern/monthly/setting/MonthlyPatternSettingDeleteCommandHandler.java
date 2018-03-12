/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.pattern.monthly.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * The Class MonthlyPatternSettingAddCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingDeleteCommandHandler
		extends CommandHandler<MonthlyPatternSettingDeleteCommand> {

	/** The repository. */
	@Inject
	private WorkingConditionItemRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternSettingDeleteCommand> context) {
		
		//get command
		MonthlyPatternSettingDeleteCommand command = context.getCommand();
		
		// call repository remove
		this.repository.deleteMonthlyPattern(command.getHistoryId());
	}

}
