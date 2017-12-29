package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class UpdateLateOrLeaveEarlyCommandHandler extends CommandHandler<UpdateLateOrLeaveEarlyCommand> {
	
	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;

	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;
	
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegisterService;
	
	@Inject DetailAfterUpdate afterProcessDetailSerivce;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateLateOrLeaveEarlyCommand> context) {
		UpdateLateOrLeaveEarlyCommand command = context.getCommand();
		String appReason = "";
		if(!command.getReasonTemp().isEmpty() || !command.getAppReason().isEmpty()) {
			appReason = !command.getReasonTemp().isEmpty() ? command.getReasonTemp() +  System.lineSeparator() + command.getAppReason() : command.getAppReason();
		}
		LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(
				command.getAppID(),
        		command.getApplicationDate(),
        		command.getPrePostAtr(),
        		appReason,
        		command.getEarly1(),
        		command.getEarlyTime1(),
        		command.getLate1(),
        		command.getLateTime1(),
        		command.getEarly2(),
        		command.getEarlyTime2(),
        		command.getLate2(),
        		command.getLateTime2());
		domainLateOrLeaveEarly.setVersion(command.getVersion());
		
		
		//「4-1.詳細画面登録前の処理」を実行する 
		detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(
				domainLateOrLeaveEarly.getCompanyID(),
				//ApplicantSID = EmployeeID
				domainLateOrLeaveEarly.getEmployeeID(),
				domainLateOrLeaveEarly.getAppDate(), 9,
				domainLateOrLeaveEarly.getAppID(),
				
				domainLateOrLeaveEarly.getPrePostAtr(), domainLateOrLeaveEarly.getVersion());
		
		//ドメインモデル「遅刻早退取消申請」の更新する
		//Update the domain model 'Cancellation for late arrival cancellation'
		lateOrLeaveEarlyService.updateLateOrLeaveEarly(domainLateOrLeaveEarly);
		
		//「4-2.詳細画面登録後の処理」を実行する
		//TODO: Waiting for common change
		//afterProcessDetailSerivce.processAfterDetailScreenRegistration(domainLateOrLeaveEarly);
		
	}
	

}
