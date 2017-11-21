package nts.uk.screen.com.app.command.systemresource;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository;
import nts.uk.shr.com.context.AppContexts;

public class SystemResourceSaveCommandHandler extends CommandHandler<SystemResourceSaveCommand> {
	
	@Inject
	private SystemResourceQueryRepository systemResourceRepository;
	
	@Override
	protected void handle(CommandHandlerContext<SystemResourceSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
	}

}
