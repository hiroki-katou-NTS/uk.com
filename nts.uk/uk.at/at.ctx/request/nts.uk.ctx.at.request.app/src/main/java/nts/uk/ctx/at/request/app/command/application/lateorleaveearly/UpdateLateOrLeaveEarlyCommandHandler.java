package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;
import nts.uk.shr.com.context.AppContexts;

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

	@Inject
	DetailAfterUpdate afterProcessDetailSerivce;

	@Inject
	private ApplicationRepository_New applicationRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateLateOrLeaveEarlyCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateLateOrLeaveEarlyCommand command = context.getCommand();
		String appReason = "";
		Long version = command.getVersion();
		if (!command.getReasonTemp().isEmpty() || !command.getAppReason().isEmpty()) {
			appReason = !command.getReasonTemp().isEmpty()
					? command.getReasonTemp() + System.lineSeparator() + command.getAppReason()
					: command.getAppReason();
		}
		Application_New application = applicationRepository.findByID(companyID, command.getAppID()).get();
		application.setAppReason(new AppReason(appReason));
		application.setVersion(version);
		LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(application,
				command.getEarly1(), command.getEarlyTime1(), command.getLate1(), command.getLateTime1(),
				command.getEarly2(), command.getEarlyTime2(), command.getLate2(), command.getLateTime2());
		domainLateOrLeaveEarly.setVersion(version);

		// 「4-1.詳細画面登録前の処理」を実行する
		detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(companyID,
				// ApplicantSID = EmployeeID
				domainLateOrLeaveEarly.getApplication().getEmployeeID(),
				domainLateOrLeaveEarly.getApplication().getAppDate(),
				domainLateOrLeaveEarly.getApplication().getAppType().value,
				domainLateOrLeaveEarly.getApplication().getAppID(),
				domainLateOrLeaveEarly.getApplication().getPrePostAtr(), domainLateOrLeaveEarly.getVersion());

		// ドメインモデル「遅刻早退取消申請」の更新する
		// Update the domain model 'Cancellation for late arrival cancellation'
		lateOrLeaveEarlyService.updateLateOrLeaveEarly(domainLateOrLeaveEarly);

		// 「4-2.詳細画面登録後の処理」を実行する
		// TODO: Waiting for common change
		afterProcessDetailSerivce.processAfterDetailScreenRegistration(domainLateOrLeaveEarly.getApplication());

	}

}
