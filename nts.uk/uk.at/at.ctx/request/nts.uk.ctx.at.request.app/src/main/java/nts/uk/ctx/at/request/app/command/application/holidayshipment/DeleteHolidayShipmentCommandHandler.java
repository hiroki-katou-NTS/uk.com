package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteHolidayShipmentCommandHandler extends CommandHandler<HolidayShipmentCommand> {

	@Inject
	private AfterProcessDelete afterDelete;
	@Inject
	private CompltLeaveSimMngRepository CompLeaveRepo;

	String companyID, employeeID;
	Long version;

	@Override
	protected void handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		HolidayShipmentCommand command = context.getCommand();
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();
		version = command.getAppVersion();
		// アルゴリズム「振休振出申請の削除」を実行する
		deleteAppForPaidLeave(command);

	}

	private void deleteAppForPaidLeave(HolidayShipmentCommand command) {

		boolean isDeleteRec = command.getRecAppID() != null;
		boolean isDeleteAbs = command.getAbsAppID() != null;

		if (isDeleteAbs) {
			// アルゴリズム「削除処理」を実行する
			deleteProcess(companyID, command.getAbsAppID());
		}

		if (isDeleteRec) {
			// アルゴリズム「削除処理」を実行する
			deleteProcess(companyID, command.getRecAppID());
		}
		if (isDeleteAbs && isDeleteRec) {
			// ドメインモデル「振休振出同時申請管理」を1件削除する
			CompLeaveRepo.remove(command.getAbsAppID(), command.getRecAppID());
		} else {
			if (isDeleteAbs) {
				// ドメインモデル「振休振出同時申請管理」を1件更新する
				Optional<CompltLeaveSimMng> compltLeaveSimMngOpt = CompLeaveRepo.findByAbsID(command.getAbsAppID());
				if (compltLeaveSimMngOpt.isPresent()) {
					CompltLeaveSimMng compltLeaveSimMng = compltLeaveSimMngOpt.get();
					compltLeaveSimMng.setSyncing(SyncState.ASYNCHRONOUS);
					CompLeaveRepo.update(compltLeaveSimMng);
				}
			}

		}

	}

	private void deleteProcess(String companyID, String appID) {
		// アルゴリズム「詳細画面削除後の処理」を実行する
		this.afterDelete.screenAfterDelete(companyID, appID, version);
	}

}
