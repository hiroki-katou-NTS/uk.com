/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.flexset.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;

/**
 * The Class FixedWorkSettingSaveCommandHandler.
 */
@Stateless
public class FixedWorkSettingSaveCommandHandler extends CommandHandler<FixedWorkSettingSaveCommand> {

	/** The fixed work setting repository. */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	
	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<FixedWorkSettingSaveCommand> context) {
		
		// get command
		FixedWorkSettingSaveCommand command = context.getCommand();
		
		//get domain fixed work setting by client send
		FixedWorkSetting fixedWorkSetting = command.toDomainFixedWorkSetting();
		
		// common handler
		this.commonHandler.handler(command);
		
		// call repository save flex work setting
		this.fixedWorkSettingRepository.save(fixedWorkSetting);
	}

}
