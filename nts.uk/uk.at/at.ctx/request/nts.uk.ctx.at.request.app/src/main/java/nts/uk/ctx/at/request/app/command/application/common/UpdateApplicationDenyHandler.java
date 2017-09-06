package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterDenialProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessDenial;

import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationDenyHandler extends CommandHandler<UpdateApplicationCommand> {

	@Inject
	private BeforeProcessDenial beforeProcessDenialRepo;
	@Inject 
	private AfterDenialProcess afterDenialProcessRepo;
	
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
		//9-1 .詳細画面否認前の処理
		beforeProcessDenialRepo.detailedScreenProcessBeforeDenial();
		//9.2 
		afterDenialProcessRepo.detailedScreenAfterDenialProcess(companyID, application.getApplicationID());
		
		
	}

}
