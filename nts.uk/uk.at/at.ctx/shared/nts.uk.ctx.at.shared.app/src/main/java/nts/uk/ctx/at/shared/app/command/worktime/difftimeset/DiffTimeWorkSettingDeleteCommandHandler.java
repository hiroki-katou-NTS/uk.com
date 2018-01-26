/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonDeleteCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DiffTimeWorkSettingDeleteCommandHandler.
 */
public class DiffTimeWorkSettingDeleteCommandHandler extends CommandHandler<DiffTimeWorkSettingDeleteCommand> {

	/** The diff time work setting repository. */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;
	
	/** The work time common delete command handler. */
	@Inject
	private WorkTimeCommonDeleteCommandHandler workTimeCommonDeleteCommandHandler;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DiffTimeWorkSettingDeleteCommand> context) {
		
		DiffTimeWorkSettingDeleteCommand command = context.getCommand();
		
		String workTimeCode = command.getWorkTimeCode();
		
		String companyId = AppContexts.user().companyId();
				
		//remove difftime
		this.diffTimeWorkSettingRepository.remove(companyId,workTimeCode);
		
		//remove common
		this.workTimeCommonDeleteCommandHandler.handle(command);
	}
	
	
}
