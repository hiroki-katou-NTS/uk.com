/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class FlexWorkSettingSaveCommandHandler.
 */
@Stateless
public class FlexWorkSettingSaveCommandHandler extends CommandHandler<FlexWorkSettingSaveCommand> {

	/** The flex work setting repository. */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	
	/** The work time setting repository. */
	@Inject 
	private WorkTimeSettingRepository workTimeSettingRepository; 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<FlexWorkSettingSaveCommand> context) {

		// get command
		FlexWorkSettingSaveCommand command = context.getCommand();
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get domain flex work setting by client send
		//FlexWorkSetting flexWorkSetting = command.toDomainFlexWorkSetting();
		
		// get work time setting by client send
		WorkTimeSetting workTimeSetting = command.toDomainWorkTimeSetting(companyId);
		
		this.workTimeSettingRepository.save(workTimeSetting);
		
		// call repository save flex work setting
		//this.flexWorkSettingRepository.saveFlexWorkSetting(flexWorkSetting);
	}

}
