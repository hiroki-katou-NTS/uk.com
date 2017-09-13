package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterDetailScreenProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenProcessAfterOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationReleaseHandler extends CommandHandler<UpdateApplicationCommand>{

	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private AfterDetailScreenProcess afterDetailScreenProcessRepo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommand appCommand = context.getCommand();
		Application application = Application.createFromJavaType(
				companyID,
				appCommand.getApplicationID(),
				appCommand.getPrePostAtr(),
				appCommand.getInputDate(), 
				appCommand.getEnteredPersonSID(), 
				appCommand.getReversionReason(), 
				appCommand.getApplicationDate(), 
				appCommand.getApplicationReason(),
				appCommand.getApplicationType(), 
				appCommand.getApplicantSID(), 
				appCommand.getReflectPlanScheReason(), 
				appCommand.getReflectPlanTime(), 
				appCommand.getReflectPlanState(), 
				appCommand.getReflectPlanEnforce(), 
				appCommand.getReflectPerScheReason(), 
				appCommand.getReflectPerTime(), 
				appCommand.getReflectPerState(), 
				appCommand.getReflectPerEnforce());
		
		//10.1
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing();
		//10.2
		DetailScreenProcessAfterOutput output = afterDetailScreenProcessRepo.getDetailScreenProcessAfter(application);
		
	}

}
