package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateApplicationCommandHandler extends CommandHandler<CreateApplicationCommand> {
	
	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	protected void handle(CommandHandlerContext<CreateApplicationCommand> context) {
		String companyID = AppContexts.user().companyId();
		CreateApplicationCommand appCommand = context.getCommand();
		Application application = appCommand.toDomain();
//		Application application = Application.createFromJavaType(
//				companyID,
//				appCommand.getAppReasonID(),
//				appCommand.getPrePostAtr(), 
//				appCommand.getInputDate(), 
//				appCommand.getEnteredPersonSID(), 
//				appCommand.getReversionReason(), 
//				appCommand.getApplicationDate(), 
//				appCommand.getApplicationReason(),
//				appCommand.getApplicationType(), 
//				appCommand.getApplicantSID(), 
//				appCommand.getReflectPlanScheReason(), 
//				appCommand.getReflectPlanTime(), 
//				appCommand.getReflectPlanState(), 
//				appCommand.getReflectPlanEnforce(), 
//				appCommand.getReflectPerScheReason(), 
//				appCommand.getReflectPerTime(), 
//				appCommand.getReflectPerState(), 
//				appCommand.getReflectPerEnforce(),
//				appCommand.getStartDate(),
//				appCommand.getEndDate());
		Optional<Application> app = appRepo.getAppById(companyID, appCommand.getApplicationID());
		if(app.isPresent()) {
			throw new BusinessException("Msg_3");
		}else {
			appRepo.addApplication(application);
		}
	}
}
