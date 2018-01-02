package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;

@Stateless
@Transactional

public class CreateLateOrLeaveEarlyCommandHandler extends CommandHandler<CreateLateOrLeaveEarlyCommand> {

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
	protected void handle(CommandHandlerContext<CreateLateOrLeaveEarlyCommand> context) {
		String appID = IdentifierUtil.randomUniqueId();
		String appReason = "";

		CreateLateOrLeaveEarlyCommand command = context.getCommand();
		if (!command.getReasonTemp().isEmpty() || !command.getAppReason().isEmpty()) {
			appReason = !command.getReasonTemp().isEmpty()
					? command.getReasonTemp() + System.lineSeparator() + command.getAppReason()
					: command.getAppReason();
		}
		LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(appID,
				command.getApplicationDate(), command.getPrePostAtr(), appReason, command.getEarly1(),
				command.getEarlyTime1(), command.getLate1(), command.getLateTime1(), command.getEarly2(),
				command.getEarlyTime2(), command.getLate2(), command.getLateTime2());
		domainLateOrLeaveEarly.setStartDate(Optional.of(domainLateOrLeaveEarly.getAppDate()));
		domainLateOrLeaveEarly.setEndDate(Optional.of(domainLateOrLeaveEarly.getAppDate()));
		// 共通アルゴリズム「2-1.新規画面登録前の処理」を実行する
		newBeforeRegister.processBeforeRegister(domainLateOrLeaveEarly);
		// 事前制約をチェックする
		// ドメインモデル「遅刻早退取消申請」の新規登録する
		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);
		// 2-2.新規画面登録時承認反映情報の整理
		registerService.newScreenRegisterAtApproveInfoReflect(domainLateOrLeaveEarly.getEmployeeID(),
				domainLateOrLeaveEarly);
		// 共通アルゴリズム「2-3.新規画面登録後の処理」を実行する
		newAfterRegister.processAfterRegister(domainLateOrLeaveEarly);

	}
}
