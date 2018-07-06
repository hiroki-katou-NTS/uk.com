/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkScheduleCommandSaveHandler.
 * @author HoangDD
 */
@Stateless
public class OutputItemDailyWorkScheduleSaveHandler extends CommandHandler<OutputItemDailyWorkScheduleCommand>{

	/** The repository. */
	@Inject
	private OutputItemDailyWorkScheduleRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutputItemDailyWorkScheduleCommand> context) {
		OutputItemDailyWorkScheduleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		OutputItemDailyWorkSchedule domain = new OutputItemDailyWorkSchedule(command);

		if (command.isNewMode()) {
			if (repository.findByCidAndCode(companyId, domain.getItemCode().v()).isPresent()) {
				throw new BusinessException("Msg_3");
			}
			repository.add(domain);
		} else {
			repository.update(domain);
		}
	}
}
