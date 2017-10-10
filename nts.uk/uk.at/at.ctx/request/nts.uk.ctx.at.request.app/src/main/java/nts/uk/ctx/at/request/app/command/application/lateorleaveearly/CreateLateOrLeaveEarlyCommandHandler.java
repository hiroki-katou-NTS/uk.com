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
        		command.getReasonTemp() +  System.lineSeparator() + command.getAppReason(),
        	//	command.getActualCancelAtr(),
        		command.getEarly1(),
        		command.getEarlyTime1(),
        		command.getLate1(),
        		command.getLateTime1(),
        		command.getEarly2(),
        		command.getEarlyTime2(),
        		command.getLate2(),
        		command.getLateTime2());
        
        // 2-1.新規画面登録前の処理」
        // TODO: Change GeneralDate.today() to StartDate and EndDate
		newBeforeProcessRegisterSerivce.processBeforeRegister(domainLateOrLeaveEarly.getCompanyID(),
				AppContexts.user().employeeId(), GeneralDate.today(), domainLateOrLeaveEarly.getPrePostAtr(), 1, 9);
		//2-2.新規画面登録時承認反映情報の整理」 registerApproveInfoservice.newScreenRegisterAtApproveInfoReflect(domainLateOrLeaveEarly.get, application);
	
		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);
		/**
		 * 2-3.新規画面登録後の処理
		 * @param companyID 会社ID
		 * @param appID 申請ID
		 */
	//	afterProcessRegisterImpl.processAfterRegister(domainLateOrLeaveEarly.getCompanyID(),domainLateOrLeaveEarly.getAppID());
		
		/**アルゴリズム「メールを送信する」を実行する (Thực thi xử lý 「メールを送信する」) */
	/*	Optional<ApplicationSetting> applicationSetting = applicationSettingRepository.getApplicationSettingByComID(domainLateOrLeaveEarly.getCompanyID());
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(domainLateOrLeaveEarly.getCompanyID(), 9);
		if(applicationSetting.get().getManualSendMailAtr().USE != null ){
		if(appTypeDiscreteSetting.get().getSendMailWhenRegisterFlg().NOTCAN != null ){
			//Mo man KDL 030
			
		}
		}*/
		
		
	
	}
}
