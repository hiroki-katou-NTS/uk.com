package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
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
		Application application = new  Application(
				companyID,
				appCommand.getApplicationID(),
				appCommand.getAppReasonID(),
				EnumAdaptor.valueOf(appCommand.getPrePostAtr(),PrePostAtr.class),
				appCommand.getInputDate(), 
				appCommand.getEnteredPersonSID(), 
				new AppReason(appCommand.getReversionReason()), 
				appCommand.getApplicationDate(), 
				new AppReason(appCommand.getApplicationReason()),
				EnumAdaptor.valueOf(appCommand.getApplicationType(),ApplicationType.class), 
				appCommand.getApplicantSID(), 
				EnumAdaptor.valueOf(appCommand.getReflectPlanScheReason(),ReflectPlanScheReason.class), 
				appCommand.getReflectPlanTime(), 
				EnumAdaptor.valueOf(appCommand.getReflectPlanState(),ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(appCommand.getReflectPlanEnforce(),ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(appCommand.getReflectPerScheReason(),ReflectPerScheReason.class), 
				appCommand.getReflectPerTime(), 
				EnumAdaptor.valueOf(appCommand.getReflectPerState(),ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(appCommand.getReflectPerEnforce(),ReflectPlanPerEnforce.class),
				appCommand.getStartDate(),
				appCommand.getEndDate());
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
