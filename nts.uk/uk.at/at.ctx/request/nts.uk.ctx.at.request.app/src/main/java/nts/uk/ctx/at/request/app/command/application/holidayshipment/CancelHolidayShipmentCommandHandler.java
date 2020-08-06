package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CancelHolidayShipmentCommandHandler extends CommandHandler<HolidayShipmentCommand> {

	@Inject
	private ProcessCancel processCancel;
	@Inject
	private CompltLeaveSimMngRepository CompLeaveRepo;

	

	@Override
	protected void handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		 
		HolidayShipmentCommand command = context.getCommand();
		String companyID = AppContexts.user().companyId();
		// アルゴリズム「振休振出申請の取消」を実行する
		cancelAppForPaidLeave(companyID, command);
	}

	public void cancelAppForPaidLeave(String companyID, HolidayShipmentCommand command) {
		boolean isCancelRec = command.getRecAppID() != null;
		boolean isCancelAbs = command.getAbsAppID() != null;

		if (isCancelRec) {
			// アルゴリズム「取消処理」を実行する
			// cancelProcess(companyID, command.getRecAppID(), command.getAppVersion());
		}

		if (isCancelAbs) {
			// アルゴリズム「取消処理」を実行する
			// cancelProcess(companyID, command.getAbsAppID(), command.getAppVersion());
			// ドメインモデル「振休振出同時申請管理」を1件更新する
			Optional<CompltLeaveSimMng> compltLeaveSimMngOpt = CompLeaveRepo.findByAbsID(command.getAbsAppID());
			if (compltLeaveSimMngOpt.isPresent()) {
				CompltLeaveSimMng compltLeaveSimMng = compltLeaveSimMngOpt.get();
				compltLeaveSimMng.setSyncing(SyncState.ASYNCHRONOUS);
				CompLeaveRepo.update(compltLeaveSimMng);
			}

		}

	}

	private void cancelProcess(String companyID, String appID, int version) {
		// アルゴリズム「詳細画面取消の処理」を実行する
		// processCancel.detailScreenCancelProcess(companyID, appID, version);
	}

}
