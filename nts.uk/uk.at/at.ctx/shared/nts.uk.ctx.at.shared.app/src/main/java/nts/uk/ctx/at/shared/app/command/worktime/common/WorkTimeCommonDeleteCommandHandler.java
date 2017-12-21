/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkTimeCommonDeleteCommandHandler.
 */
public class WorkTimeCommonDeleteCommandHandler extends CommandHandler<WorkTimeCommonDeleteCommand> {

	/** The work time setting repository. */
	@Inject 
	private WorkTimeSettingRepository workTimeSettingRepository; 
	
	/** The predetemine time setting repository. */
	@Inject 
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository; 
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkTimeCommonDeleteCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		
		WorkTimeCommonDeleteCommand command = context.getCommand();

		String workTimeCode = command.getWorkTimeCode();

		//remove pred
		this.predetemineTimeSettingRepository.remove(companyId,workTimeCode);
		
		//remove worktimeset
		this.workTimeSettingRepository.remove(companyId,workTimeCode);
	}

}
