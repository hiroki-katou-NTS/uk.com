/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingRepository;

/**
 * The Class MonthlyPatternSettingAddCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingSaveCommandHandler
		extends CommandHandler<MonthlyPatternSettingSaveCommand> {

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
	protected void handle(CommandHandlerContext<MonthlyPatternSettingSaveCommand> context) {
		
		//get command
		MonthlyPatternSettingSaveCommand command = context.getCommand();
		
		// command to domain
		MonthlyPatternSetting domain = command.toDomain();
		
		// find data by employee id
		Optional<MonthlyPatternSetting> monthlyPatternSetting = this.repository
				.findById(domain.getEmployeeId());
		
		// check exist data
		if (monthlyPatternSetting.isPresent()) {
			this.repository.update(domain);
			return;
		}
		// add data if not exist data
		this.repository.add(domain);
	}

}
