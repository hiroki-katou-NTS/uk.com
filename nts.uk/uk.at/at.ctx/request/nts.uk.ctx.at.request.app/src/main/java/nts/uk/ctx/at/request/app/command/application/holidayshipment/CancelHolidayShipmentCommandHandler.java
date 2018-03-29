package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CancelHolidayShipmentCommandHandler extends CommandHandler<DeleteHolidayShipmentCommand> {

	@Inject
	private ProcessCancel processCancel;

	String companyID, employeeID;
	Long version;

	@Override
	protected void handle(CommandHandlerContext<DeleteHolidayShipmentCommand> context) {
		DeleteHolidayShipmentCommand command = context.getCommand();
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();
		version = command.getAppVersion();
		// アルゴリズム「振休振出申請の取消」を実行する
		cancelAppForPaidLeave(command);
	}

	private void cancelAppForPaidLeave(DeleteHolidayShipmentCommand command) {
		boolean isDeleteRec = command.getRecAppID() != null;
		boolean isDeleteAbs = command.getAbsAppID() != null;

		if (isDeleteAbs) {
			// アルゴリズム「取消処理」を実行する
			cancelProcess(companyID, command.getAbsAppID());
		}

		if (isDeleteRec) {
			// アルゴリズム「取消処理」を実行する
			cancelProcess(companyID, command.getRecAppID());
		}
		// ドメインモデル「振休振出同時申請管理」を1件更新する

	}

	private void cancelProcess(String companyID, String appID) {
		// アルゴリズム「詳細画面取消の処理」を実行する
		processCancel.detailScreenCancelProcess(companyID, appID, version);
	}

}
