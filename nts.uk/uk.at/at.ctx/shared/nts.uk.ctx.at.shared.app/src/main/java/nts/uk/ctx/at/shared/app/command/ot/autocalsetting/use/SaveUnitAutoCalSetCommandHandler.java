/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.use;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class SaveUnitAutoCalSetCommandHandler.
 */
@Stateless
public class SaveUnitAutoCalSetCommandHandler extends CommandHandler<UseUnitAutoCalSettingCommand> {


    /** The use unit auto cal setting repository. */
    @Inject
    private UseUnitAutoCalSettingRepository useUnitAutoCalSettingRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UseUnitAutoCalSettingCommand> context) {
		// Get context info
        String companyId = AppContexts.user().companyId();

        // Get command
        UseUnitAutoCalSettingCommand command = context.getCommand();
        
        // Find details
        Optional<UseUnitAutoCalSetting> result = this.useUnitAutoCalSettingRepository.getAllUseUnitAutoCalSetting(companyId);

        // Check exist
        if (!result.isPresent()) {
            throw new BusinessException("Msg_3");
        }
        
        // Convert to domain
        UseUnitAutoCalSetting useUnitAutoCalSetting = command.toDomain(companyId);

        // Alway has 30 items and allow update only
		this.useUnitAutoCalSettingRepository.update(useUnitAutoCalSetting);
		
	}

}
