/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpJobAutoCalSetCommandHandler.
 */
@Stateless
public class SaveWkpJobAutoCalSetCommandHandler extends CommandHandler<WkpJobAutoCalSetCommand> {

	/** The wkp job auto cal setting repo. */
	@Inject
	private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WkpJobAutoCalSetCommand> context) {
		// Get context info
		String companyId = AppContexts.user().companyId();

		// Get command
		WkpJobAutoCalSetCommand command = this.getCommandBySaveCondition(context.getCommand());

		// Find details
		Optional<WkpJobAutoCalSetting> result = this.wkpJobAutoCalSettingRepo.getWkpJobAutoCalSetting(companyId,
				command.getWkpId(), command.getJobId());

		// Convert to domain
		WkpJobAutoCalSetting AutoCalSetting = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.wkpJobAutoCalSettingRepo.add(AutoCalSetting);
		} else {
			this.wkpJobAutoCalSettingRepo.update(AutoCalSetting);
		}
	}

	/**
	 * Gets the command by save condition.
	 *
	 * @param command
	 *            the command
	 * @return the command by save condition
	 */
	public WkpJobAutoCalSetCommand getCommandBySaveCondition(WkpJobAutoCalSetCommand command) {
		// Check normal OT time
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getEarlyOtTime().getCalAtr()) {
			command.getNormalOTTime().getEarlyOtTime().setUpLimitOtSet(null);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getEarlyMidOtTime().getCalAtr()) {
			command.getNormalOTTime().getEarlyMidOtTime().setUpLimitOtSet(null);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getNormalOtTime().getCalAtr()) {
			command.getNormalOTTime().getNormalOtTime().setUpLimitOtSet(null);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getNormalMidOtTime().getCalAtr()) {
			command.getNormalOTTime().getNormalMidOtTime().setUpLimitOtSet(null);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getLegalOtTime().getCalAtr()) {
			command.getNormalOTTime().getLegalOtTime().setUpLimitOtSet(null);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getNormalOTTime().getLegalMidOtTime().getCalAtr()) {
			command.getNormalOTTime().getLegalMidOtTime().setUpLimitOtSet(null);
		}

		// Check flex OT time
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getFlexOTTime().getFlexOtTime().getCalAtr()) {
			command.getFlexOTTime().getFlexOtTime().setUpLimitOtSet(null);
		}

		// Check rest time
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getRestTime().getRestTime().getCalAtr()) {
			command.getRestTime().getRestTime().setUpLimitOtSet(null);
		}
		if (AutoCalAtrOvertime.APPLYMANUALLYENTER.value == command.getRestTime().getLateNightTime().getCalAtr()) {
			command.getRestTime().getLateNightTime().setUpLimitOtSet(null);
		}
		return command;
	}
}
