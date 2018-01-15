/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.shr.com.context.AppContexts;

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

		String companyId = AppContexts.user().companyId();
		
		// get command
		FlexWorkSettingSaveCommand command = context.getCommand();

		// get domain flex work setting by client send
		FlexWorkSetting flexWorkSetting = command.toDomainFlexWorkSetting();

		// common handler
		this.commonHandler.handle(command);

		// validate domain
		this.flexPolicy.canRegisterFlexWorkSetting(flexWorkSetting, command.toDomainPredetemineTimeSetting());

		// check is add mode
		if (command.isAddMode()) {
			flexWorkSetting.restoreDefaultData(ScreenMode.valueOf(command.getScreenMode()));
			this.flexWorkSettingRepository.add(flexWorkSetting);
		} 
		else {
			Optional<FlexWorkSetting> opFlexWorkSetting = this.flexWorkSettingRepository.find(companyId,
					command.getWorktimeSetting().worktimeCode);
			if (opFlexWorkSetting.isPresent()) {
				flexWorkSetting.restoreData(ScreenMode.valueOf(command.getScreenMode()),
						command.getWorktimeSetting().getWorkTimeDivision(), opFlexWorkSetting.get());
				this.flexWorkSettingRepository.update(flexWorkSetting);
			}
		}
	}

}
