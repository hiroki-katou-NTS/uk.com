/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.pattern.monthly.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * The Class CopyMonthPatternSettingCommandHandler.
 */
@Stateless
public class CopyMonthPatternSettingCommandHandler
		extends CommandHandler<CopyMonthPatternSettingCommand> {

	/** The working condition item repository. */
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CopyMonthPatternSettingCommand> context) {
		val command = context.getCommand();
		workingConditionItemRepository.copyLastMonthlyPatternSetting(command.getSourceSid(),
				command.getDestSid());
	}

}
