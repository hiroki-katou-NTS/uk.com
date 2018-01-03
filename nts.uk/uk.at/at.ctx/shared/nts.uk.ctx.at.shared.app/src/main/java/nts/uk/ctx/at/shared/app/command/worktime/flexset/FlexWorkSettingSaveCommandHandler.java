/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;

/**
 * The Class FlexWorkSettingSaveCommandHandler.
 */
@Stateless
public class FlexWorkSettingSaveCommandHandler extends CommandHandler<FlexWorkSettingSaveCommand> {

	/** The flex work setting repository. */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	/** The flex policy. */
	@Inject
	private FlexWorkSettingPolicy flexPolicy;
	
	/** The common handler. */
	@Inject
	private WorkTimeCommonSaveCommandHandler commonHandler;
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
		
		//get domain flex work setting by client send
		FlexWorkSetting flexWorkSetting = command.toDomainFlexWorkSetting();
		
		// common handler
		this.commonHandler.handle(command);

		// validate domain
		this.flexPolicy.canRegisterFlexWorkSetting(flexWorkSetting, command.toDomainPredetemineTimeSetting());
		
		// check is add mode
		if(command.isAddMode()){
			this.flexWorkSettingRepository.add(flexWorkSetting);
		}
		else{
			this.flexWorkSettingRepository.update(flexWorkSetting);
		}
	}

}
