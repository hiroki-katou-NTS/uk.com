package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegisterImpl;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;

import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional

public class CreateLateOrLeaveEarlyCommandHandler extends CommandHandler<CreateLateOrLeaveEarlyCommand> {

	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;
	
	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;
	
	@Inject
	private NewBeforeRegister newBeforeProcessRegisterSerivce;
	

	@Override
	protected void handle(CommandHandlerContext<CreateLateOrLeaveEarlyCommand> context) {
		CreateLateOrLeaveEarlyCommand command = context.getCommand();
        LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(
        		command.getApplicationDate(),
        		command.getReasonTemp() +  ":" + command.getAppReason(),
        	//	command.getActualCancelAtr(),
        		command.getEarly1(),
        		command.getEarlyTime1(),
        		command.getLate1(),
        		command.getLateTime1(),
        		command.getEarly2(),
        		command.getEarlyTime2(),
        		command.getLate2(),
        		command.getLateTime2());
        
        // 2-1.譁ｰ隕冗判髱｢逋ｻ骭ｲ蜑阪�ｮ蜃ｦ逅�縲�
        // TODO: Change GeneralDate.today() to StartDate and EndDate
	/*Chua co ListJobEntryHist ben Hung
	 * 	newBeforeProcessRegisterSerivce.processBeforeRegister(domainLateOrLeaveEarly.getCompanyID(),
				AppContexts.user().employeeId(), GeneralDate.today(), domainLateOrLeaveEarly.getPrePostAtr(), 1, 9);*/
		//2-2.譁ｰ隕冗判髱｢逋ｻ骭ｲ譎よ価隱榊渚譏�諠�蝣ｱ縺ｮ謨ｴ逅�縲� registerApproveInfoservice.newScreenRegisterAtApproveInfoReflect(domainLateOrLeaveEarly.get, application);
	
		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);
		/**
		 * 2-3.譁ｰ隕冗判髱｢逋ｻ骭ｲ蠕後�ｮ蜃ｦ逅�
		 * @param companyID 莨夂､ｾID
		 * @param appID 逕ｳ隲紀D
		 */
	//	afterProcessRegisterImpl.processAfterRegister(domainLateOrLeaveEarly.getCompanyID(),domainLateOrLeaveEarly.getAppID());
		
		/**繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲後Γ繝ｼ繝ｫ繧帝�∽ｿ｡縺吶ｋ縲阪ｒ螳溯｡後☆繧� (Th盻ｱc thi x盻ｭ lﾃｽ 縲後Γ繝ｼ繝ｫ繧帝�∽ｿ｡縺吶ｋ縲�) */
	/*	Optional<ApplicationSetting> applicationSetting = applicationSettingRepository.getApplicationSettingByComID(domainLateOrLeaveEarly.getCompanyID());
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(domainLateOrLeaveEarly.getCompanyID(), 9);
		if(applicationSetting.get().getManualSendMailAtr().USE != null ){
		if(appTypeDiscreteSetting.get().getSendMailWhenRegisterFlg().NOTCAN != null ){
			//Mo man KDL 030
			
		}
		}*/
		
		
	
	}
}
