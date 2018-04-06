package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterRelease;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReleaseHolidayShipmentCommandHandler extends CommandHandler<HolidayShipmentCommand> {

	String companyID, employeeID;
	Long version;

	@Inject
	private BeforeProcessReleasing beforeReleaseProc;
	@Inject
	private DetailAfterRelease afterRelease;

	@Override
	protected void handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		HolidayShipmentCommand command = context.getCommand();
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();
		version = command.getAppVersion();
		// アルゴリズム「振休振出申請の承認解除」を実行する
		cancellationApproval(command);
	}

	private void cancellationApproval(HolidayShipmentCommand command) {
		boolean isReleaseRec = command.getRecAppID() != null;
		boolean isReleaseAbs = command.getAbsAppID() != null;
		if (isReleaseAbs) {
			// アルゴリズム「承認解除処理」を実行する
			releaseProcessing(companyID, command.getAbsAppID());
		}

		if (isReleaseRec) {
			// アルゴリズム「承認解除処理」を実行する
			releaseProcessing(companyID, command.getRecAppID());
		}

	}

	private void releaseProcessing(String companyID, String appID) {
		// アルゴリズム「詳細画面解除前の処理」を実行する
		this.beforeReleaseProc.detailScreenProcessBeforeReleasing(companyID, appID, version);
		// アルゴリズム「詳細画面解除後の処理」を実行する
		this.afterRelease.detailAfterRelease(companyID, appID, employeeID, "");
	}

}
