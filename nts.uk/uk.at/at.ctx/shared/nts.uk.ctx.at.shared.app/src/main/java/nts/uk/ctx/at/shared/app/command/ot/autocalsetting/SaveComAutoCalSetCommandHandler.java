package nts.uk.ctx.at.shared.app.command.ot.autocalsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class SaveComAutoCalSetCommandHandler extends CommandHandler<ComAutoCalSetCommand> {


    /** The total times repo. */
    @Inject
    private ComAutoCalSettingRepository comAutoCalSettingRepo;
	
	@Override
	protected void handle(CommandHandlerContext<ComAutoCalSetCommand> context) {
		// Get context info
        String companyId = AppContexts.user().companyId();

        // Get command
        ComAutoCalSetCommand command = context.getCommand();
        
        // Find details
        Optional<ComAutoCalSetting> result = this.comAutoCalSettingRepo.getAllComAutoCalSetting(companyId);

        // Check exist
        if (!result.isPresent()) {
            throw new BusinessException("Msg_3");
        }
        
        // Convert to domain
        ComAutoCalSetting comAutoCalSetting = command.toDomain(companyId);

        // Alway has 30 items and allow update only
		this.comAutoCalSettingRepo.update(comAutoCalSetting);
	}

}
