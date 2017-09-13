package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeProcessRegister;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationApproveHandler extends CommandHandler<UpdateApplicationCommand> {

	@Inject
	private ApplicationRepository appRepo;
	
	//4-1.詳細画面登録前の処理
	@Inject 
	private DetailBeforeProcessRegister beforeRegisterRepo;
	//8-2.詳細画面承認後の処理
	@Inject 
	private AfterApprovalProcess afterApprovalProcessRepo;
	
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
		//if approve
		//4-1.
		beforeRegisterRepo.processBeforeDetailScreenRegistration(
				companyID, 
				application.getApplicantSID(), 
				application.getApplicationDate(), 
				1, 
				appCommand.getApplicationID(),
				application.getPrePostAtr());

		//8.2.1.
		afterApprovalProcessRepo.invidialApplicationErrorCheck(appCommand.getApplicationID());
		//8.2.2.
		afterApprovalProcessRepo.invidialApplicationUpdate(appCommand.getApplicationID());
		//8-2.
		afterApprovalProcessRepo.detailScreenAfterApprovalProcess(companyID, 
				appCommand.getApplicationID(), 
				application);
	
//		Optional<Application> app = appRepo.getAppById(companyID, appCommand.getApplicationID());
//		
//		
//		if(app.isPresent()) {
//			appRepo.updateApplication(application);
//		}else {
//			throw new BusinessException("K ton tai");
//		}
		
	}

}
