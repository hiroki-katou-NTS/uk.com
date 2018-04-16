package nts.uk.ctx.at.shared.app.command.flex;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddFlexWorkSettingCommandHandler.
 *
 * @author HoangNDH
 */
@Stateless
public class AddFlexWorkSettingCommandHandler extends CommandHandler<AddFlexWorkSettingCommand> {
	
	/** The repository. */
	@Inject
	FlexWorkMntSetRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddFlexWorkSettingCommand> context) {
		AddFlexWorkSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		Optional<FlexWorkSet> optFlexWorkSet = repository.find(companyId);
		FlexWorkSet setting = FlexWorkSet.createFromJavaType(companyId, command.getManagingFlexWork());
		if (optFlexWorkSet.isPresent()) {
			repository.update(setting);
		}
		else {
			repository.add(setting);
		}
	}
	
}
