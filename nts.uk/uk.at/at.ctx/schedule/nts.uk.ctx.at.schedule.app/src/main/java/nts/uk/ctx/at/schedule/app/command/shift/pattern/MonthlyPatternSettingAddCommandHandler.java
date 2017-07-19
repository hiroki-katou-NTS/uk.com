/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository;

/**
 * The Class MonthlyPatternSettingAddCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingAddCommandHandler
		extends CommandHandler<MonthlyPatternSettingAddCommand> {

	/** The repository. */
	@Inject
	private MonthlyPatternSettingRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternSettingAddCommand> context) {
		
		//get command
		MonthlyPatternSettingAddCommand command = context.getCommand();
		
		MonthlyPatternSetting domain = command.toDomain();
		
		this.repository.add(domain);
	}

}
