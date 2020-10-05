/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class SaveComAutoCalSetCommandHandler.
 */
@Stateless
public class SaveComAutoCalSetCommandHandler extends CommandHandler<ComAutoCalSetCommand> {

    /** The com auto cal setting repo. */
    @Inject
    private ComAutoCalSettingRepository comAutoCalSettingRepo;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ComAutoCalSetCommand> context) {
		// Get context info
        String companyId = AppContexts.user().companyId();

        // Find details
        Optional<ComAutoCalSetting> result = this.comAutoCalSettingRepo.getAllComAutoCalSetting(companyId);

        // Get command
        ComAutoCalSetCommand command = this.getCommandBySaveCondition(result.get(), context.getCommand());            
        
        // Convert to domain
        ComAutoCalSetting comAutoCalSetting = command.toDomain(companyId);

        // Alway has 30 items and allow update only
		this.comAutoCalSettingRepo.update(comAutoCalSetting);
	}
	
	/**
	 * Gets the command by save condition.
	 *
	 * @param command the command
	 * @return the command by save condition
	 */
	public ComAutoCalSetCommand getCommandBySaveCondition(ComAutoCalSetting oldValue, ComAutoCalSetCommand command) {		
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
