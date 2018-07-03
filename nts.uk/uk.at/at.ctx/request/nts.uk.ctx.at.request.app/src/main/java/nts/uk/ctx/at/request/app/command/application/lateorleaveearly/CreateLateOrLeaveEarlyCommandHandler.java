package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional

public class CreateLateOrLeaveEarlyCommandHandler extends CommandHandlerWithResult<CreateLateOrLeaveEarlyCommand, ProcessResult> {

	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;

	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;

	@Inject
	private NewAfterRegister_New newAfterRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService_New registerService;

	@Inject
	private NewBeforeRegister_New newBeforeRegister;	

	@Override
	protected ProcessResult handle(CommandHandlerContext<CreateLateOrLeaveEarlyCommand> context) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		String appReason = "";
		CreateLateOrLeaveEarlyCommand command = context.getCommand();
		if (!command.getReasonTemp().isEmpty() || !command.getAppReason().isEmpty()) {
			appReason = !command.getReasonTemp().isEmpty()
					? command.getReasonTemp() + System.lineSeparator() + command.getAppReason()
					: command.getAppReason();
		}
		Application_New application = Application_New.firstCreate(
				companyID, EnumAdaptor.valueOf(command.getPrePostAtr(), PrePostAtr.class), 
				command.getApplicationDate(), ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, employeeID, new AppReason(appReason));
		LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(
				application,
				command.getActualCancel(),
				command.getEarly1(), command.getEarlyTime1(), 
				command.getLate1(), command.getLateTime1(), 
				command.getEarly2(), command.getEarlyTime2(), 
				command.getLate2(), command.getLateTime2());
		
		
		// 共通アルゴリズム「2-1.新規画面登録前の処理」を実行する
		newBeforeRegister.processBeforeRegister(domainLateOrLeaveEarly.getApplication(),0);
		// 事前制約をチェックする
		// ドメインモデル「遅刻早退取消申請」の新規登録する
		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);
		// 2-2.新規画面登録時承認反映情報の整理
		registerService.newScreenRegisterAtApproveInfoReflect(domainLateOrLeaveEarly.getApplication().getEmployeeID(),
				domainLateOrLeaveEarly.getApplication());
		// 共通アルゴリズム「2-3.新規画面登録後の処理」を実行する
		return newAfterRegister.processAfterRegister(domainLateOrLeaveEarly.getApplication());

	}
}
