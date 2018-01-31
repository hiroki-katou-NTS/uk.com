/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.pattern.monthly.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * The Class MonthlyPatternSettingAddCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingSaveCommandHandler
		extends CommandHandler<MonthlyPatternSettingSaveCommand> {

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
	protected void handle(CommandHandlerContext<MonthlyPatternSettingSaveCommand> context) {

		//get command
		MonthlyPatternSettingSaveCommand command = context.getCommand();	
		
		// update
		this.repository.updateMonthlyPattern(command.getHistoryId(), new MonthlyPatternCode(command.getMonthlyPatternCode()));
	}

}
