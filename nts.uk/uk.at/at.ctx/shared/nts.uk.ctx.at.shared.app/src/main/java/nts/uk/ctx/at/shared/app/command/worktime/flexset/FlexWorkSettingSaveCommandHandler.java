/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;

/**
 * The Class FlexWorkSettingSaveCommandHandler.
 */
@Stateless
public class FlexWorkSettingSaveCommandHandler extends CommandHandler<FlexWorkSettingSaveCommand> {

	/** The flex work setting repository. */
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
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
		
		// get domain flex work setting by client send
		FlexWorkSetting flexWorkSetting = command.toDomainFlexWorkSetting();
		
		// call repository save flex work setting
		this.flexWorkSettingRepository.saveFlexWorkSetting(flexWorkSetting);
	}

}
