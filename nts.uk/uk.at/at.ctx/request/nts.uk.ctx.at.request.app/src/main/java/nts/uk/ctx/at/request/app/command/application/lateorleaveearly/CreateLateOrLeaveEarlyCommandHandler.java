package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegisterImpl;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;

import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional

public class CreateLateOrLeaveEarlyCommandHandler extends CommandHandler<CreateApplicationLateOrLeaveEarlyCommand> {

	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;
	
	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;
	
	@Inject
	private NewBeforeRegister newBeforeProcessRegisterSerivce;
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerService;
	
	@Inject
	private NewAfterRegister newAfterRegister;
	

	@Override
	protected void handle(CommandHandlerContext<CreateApplicationLateOrLeaveEarlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		CreateApplicationLateOrLeaveEarlyCommand command = context.getCommand();
        LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(
        		command.lateOrLeaveEarlyCommand.getApplicationDate(),
        		command.lateOrLeaveEarlyCommand.getReasonTemp() + ":" + command.lateOrLeaveEarlyCommand.getAppReason(),
        		command.lateOrLeaveEarlyCommand.getEarly1(),
        		command.lateOrLeaveEarlyCommand.getEarlyTime1(),
        		command.lateOrLeaveEarlyCommand.getLate1(),
        		command.lateOrLeaveEarlyCommand.getLateTime1(),
        		command.lateOrLeaveEarlyCommand.getEarly2(),
        		command.lateOrLeaveEarlyCommand.getEarlyTime2(),
        		command.lateOrLeaveEarlyCommand.getLate2(),
        		command.lateOrLeaveEarlyCommand.getLateTime2());
        
      //approval phase
      		List<AppApprovalPhase> appApprovalPhases = context.getCommand().getAppApprovalPhaseCmds()
      				.stream().map(appApprovalPhaseCmd -> new AppApprovalPhase(
      						companyId, 
      						"", 
      						"", 
      						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class) , 
      						appApprovalPhaseCmd.dispOrder, 
      						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class) ,
      						//Frame
      						appApprovalPhaseCmd.getListFrame().stream().map(approvalFrame -> new ApprovalFrame(
      								companyId, 
      								"", 
      								approvalFrame.dispOrder, 
      								approvalFrame.listApproveAccepted.stream().map(approveAccepted -> ApproveAccepted.createFromJavaType(
      										companyId, 
      										"", 
      										approveAccepted.approverSID,
      										ApprovalAtr.UNAPPROVED.value,
      										approveAccepted.confirmATR,
      										null,
      										approveAccepted.reason,
      										approveAccepted.representerSID
      										)).collect(Collectors.toList())
      								)).collect(Collectors.toList())
      						))
      				.collect(Collectors.toList());
      		//get new Application Item
      		Application newApp = Application.createFromJavaType(
      				companyId, 
      				command.appCommand.getPrePostAtr(),
      				command.appCommand.getInputDate(), 
      				command.appCommand.getEnteredPersonSID(),
      				command.appCommand.getReversionReason(), 
      				command.appCommand.getApplicationDate(),
      				command.appCommand.getAppReasonID() + ":" + command.appCommand.getApplicationReason(),
      				command.appCommand.getApplicationType(), 
      				command.appCommand.getApplicantSID(),
      				command.appCommand.getReflectPlanScheReason(), 
      				command.appCommand.getReflectPlanTime(),
      				command.appCommand.getReflectPerState(), 
      				command.appCommand.getReflectPlanEnforce(),
      				command.appCommand.getReflectPerScheReason(), 
      				command.appCommand.getReflectPerTime(),
      				command.appCommand.getReflectPerState(), 
      				command.appCommand.getReflectPlanEnforce(),
      				command.appCommand.getStartDate(), 
      				command.appCommand.getEndDate(), 
      				appApprovalPhases);
        
        // 2-1.譁ｰ隕冗判髱｢逋ｻ骭ｲ蜑阪蜃ｦ逅縲
        // TODO: Change GeneralDate.today() to StartDate and EndDate
	
	  	newBeforeProcessRegisterSerivce.processBeforeRegister(domainLateOrLeaveEarly.getCompanyID(),
				AppContexts.user().employeeId(), GeneralDate.today(), domainLateOrLeaveEarly.getPrePostAtr(), 1, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		//2-2.譁ｰ隕冗判髱｢逋ｻ骭ｲ譎よ価隱榊渚譏諠蝣ｱ縺ｮ謨ｴ逅縲 
	  	registerService.newScreenRegisterAtApproveInfoReflect(domainLateOrLeaveEarly.getApplicantSID(), newApp);
	
		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);
		/**
		 * 2-3.譁ｰ隕冗判髱｢逋ｻ骭ｲ蠕後蜃ｦ逅
		 * @param companyID 莨夂､ｾID
		 * @param appID 逕ｳ隲紀D
		 */
		newAfterRegister.processAfterRegister(newApp);
		
		
	
	/*	Optional<ApplicationSetting> applicationSetting = applicationSettingRepository.getApplicationSettingByComID(domainLateOrLeaveEarly.getCompanyID());
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(domainLateOrLeaveEarly.getCompanyID(), 9);
		if(applicationSetting.get().getManualSendMailAtr().USE != null ){
		if(appTypeDiscreteSetting.get().getSendMailWhenRegisterFlg().NOTCAN != null ){
			//Mo man KDL 030
			
		}
		}*/
		
		
	
	}
}
