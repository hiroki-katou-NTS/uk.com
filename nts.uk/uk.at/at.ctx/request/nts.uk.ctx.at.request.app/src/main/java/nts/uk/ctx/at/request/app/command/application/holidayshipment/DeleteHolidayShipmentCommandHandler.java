package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteHolidayShipmentCommandHandler
		extends CommandHandlerWithResult<HolidayShipmentCommand, ProcessResult> {

	@Inject
	private AfterProcessDelete afterDelete;
	@Inject
	private AppHdsubRecRepository CompLeaveRepo;

	@Override
	protected ProcessResult	 handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		HolidayShipmentCommand command = context.getCommand();
		String companyID = AppContexts.user().companyId();
		int version = command.getAppVersion();
		// アルゴリズム「振休振出申請の削除」を実行する
		return deleteAppForPaidLeave(command, companyID, version);

	}

	private ProcessResult deleteAppForPaidLeave(HolidayShipmentCommand command, String companyID, int version) {

		boolean isDeleteRec = command.getRecAppID() != null;
		boolean isDeleteAbs = command.getAbsAppID() != null;
		ProcessResult result = null;

		if (isDeleteAbs) {
			// アルゴリズム「削除処理」を実行する
			result = deleteProcess(companyID, command.getAbsAppID(), version);
		}

		if (isDeleteRec) {
			// アルゴリズム「削除処理」を実行する
			result = deleteProcess(companyID, command.getRecAppID(), version);
		}
		if (isDeleteAbs && isDeleteRec) {
			// ドメインモデル「振休振出同時申請管理」を1件削除する
			CompLeaveRepo.remove(command.getAbsAppID(), command.getRecAppID());
		} else {
			if (isDeleteAbs) {
				// ドメインモデル「振休振出同時申請管理」を1件更新する
				Optional<AppHdsubRec> compltLeaveSimMngOpt = CompLeaveRepo.findByAbsID(command.getAbsAppID());
				if (compltLeaveSimMngOpt.isPresent()) {
					AppHdsubRec compltLeaveSimMng = compltLeaveSimMngOpt.get();
					compltLeaveSimMng.setSyncing(SyncState.ASYNCHRONOUS);
					CompLeaveRepo.update(compltLeaveSimMng);
				}
			}

		}
		return result;

	}

	private ProcessResult deleteProcess(String companyID, String appID, int version) {
		// アルゴリズム「詳細画面削除後の処理」を実行する
		// refactor 4 error
		/*return this.afterDelete.screenAfterDelete(companyID, appID, version).getProcessResult();*/
		return null;
	}

}
