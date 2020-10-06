/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpAutoCalSetCommandHandler.
 */
@Stateless
public class SaveWkpAutoCalSetCommandHandler extends CommandHandler<WkpAutoCalSetCommand> {

	/** The wkp auto cal setting repo. */
	@Inject
	private WkpAutoCalSettingRepository wkpAutoCalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WkpAutoCalSetCommand> context) {
		// Get context info
		String companyId = AppContexts.user().companyId();

		// Get command
		WkpAutoCalSetCommand command = context.getCommand();

		// Find details
		Optional<WkpAutoCalSetting> result = this.wkpAutoCalSettingRepo.getWkpAutoCalSetting(companyId,
				command.getWkpId());

		// check add or update
		if (!result.isPresent()) {
			WkpAutoCalSetting wkpAutoCalSetting = command.toDomain(companyId);
			this.wkpAutoCalSettingRepo.add(wkpAutoCalSetting);
		} else {
			WkpAutoCalSetCommand saveCommand = this.getCommandBySaveCondition(result.get(), command);
			WkpAutoCalSetting wkpAutoCalSetting = saveCommand.toDomain(companyId);
			this.wkpAutoCalSettingRepo.update(wkpAutoCalSetting);
		}
	}

	/**
	 * Gets the command by save condition.
	 *
	 * @param command
	 *            the command
	 * @return the command by save condition
	 */
	public WkpAutoCalSetCommand getCommandBySaveCondition(WkpAutoCalSetting oldValue, WkpAutoCalSetCommand command) {
		// Check normal OT time
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getEarlyOtTime().getCalAtr()) {
			command.getNormalOTTime().getEarlyOtTime().setUpLimitOtSet(oldValue.getNormalOTTime().getEarlyOtTime().getUpLimitORtSet().value);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getEarlyMidOtTime().getCalAtr()) {
			command.getNormalOTTime().getEarlyMidOtTime().setUpLimitOtSet(oldValue.getNormalOTTime().getEarlyMidOtTime().getUpLimitORtSet().value);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getNormalOtTime().getCalAtr()) {
			command.getNormalOTTime().getNormalOtTime().setUpLimitOtSet(oldValue.getNormalOTTime().getNormalOtTime().getUpLimitORtSet().value);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getNormalMidOtTime().getCalAtr()) {
			command.getNormalOTTime().getNormalMidOtTime().setUpLimitOtSet(oldValue.getNormalOTTime().getNormalMidOtTime().getUpLimitORtSet().value);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getLegalOtTime().getCalAtr()) {
			command.getNormalOTTime().getLegalOtTime().setUpLimitOtSet(oldValue.getNormalOTTime().getLegalOtTime().getUpLimitORtSet().value);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getLegalMidOtTime().getCalAtr()) {
			command.getNormalOTTime().getLegalMidOtTime().setUpLimitOtSet(oldValue.getNormalOTTime().getLegalMidOtTime().getUpLimitORtSet().value);
		}
		
		// Check flex OT time	
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getFlexOTTime().getFlexOtTime().getCalAtr()) {
			command.getFlexOTTime().getFlexOtTime().setUpLimitOtSet(oldValue.getFlexOTTime().getFlexOtTime().getUpLimitORtSet().value);
		}
		
		// Check rest time
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getRestTime().getRestTime().getCalAtr()) {
			command.getRestTime().getRestTime().setUpLimitOtSet(oldValue.getRestTime().getRestTime().getUpLimitORtSet().value);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getRestTime().getLateNightTime().getCalAtr()) {
			command.getRestTime().getLateNightTime().setUpLimitOtSet(oldValue.getRestTime().getLateNightTime().getUpLimitORtSet().value);
		}
		return command;
	}
}
