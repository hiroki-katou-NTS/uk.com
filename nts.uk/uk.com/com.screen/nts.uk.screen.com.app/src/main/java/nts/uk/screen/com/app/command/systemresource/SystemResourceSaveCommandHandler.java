package nts.uk.screen.com.app.command.systemresource;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository;

@Stateless
public class SystemResourceSaveCommandHandler extends CommandHandler<SystemResourceSaveCommand> {
	
	@Inject
	private SystemResourceQueryRepository systemResourceRepository;
	
	@Override
	protected void handle(CommandHandlerContext<SystemResourceSaveCommand> context) {
		
	}

}
