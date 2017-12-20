package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteApplicationCommandHandler extends CommandHandler<DeleteApplicationCommand> {

	@Inject
	private ApplicationRepository appRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteApplicationCommand> context) {
		String companyID = AppContexts.user().companyId();
		Optional<Application> app = appRepo.getAppById(companyID, 
				context.getCommand().getApplicationID());
		if(app.isPresent()) {
			appRepo.deleteApplication(companyID, context.getCommand().getApplicationID(), context.getCommand().getVersion());
		}
		
	}
	
	
}
