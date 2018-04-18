package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterDeny;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessDenial;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DenyHolidayShipmentCommandHandler extends CommandHandler<HolidayShipmentCommand> {

	String companyID, employeeID, memo;
	Long version;

	@Inject
	private BeforeProcessDenial beforeDenialProc;
	@Inject
	private DetailAfterDeny detailAfterDeny;

	@Override
	protected void handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		HolidayShipmentCommand command = context.getCommand();
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();
		version = command.getAppVersion();
		memo = command.getMemo();
		// アルゴリズム「振休振出申請の否認」を実行する
		denyApplication(command);

	}

	private void denyApplication(HolidayShipmentCommand command) {
		boolean isDenyRec = command.getRecAppID() != null;
		boolean isDenyAbs = command.getAbsAppID() != null;
		if (isDenyRec) {
			// アルゴリズム「否認処理」を実行する
			denyProcess(companyID, command.getRecAppID());
		}

		if (isDenyAbs) {
			// アルゴリズム「否認処理」を実行する
			denyProcess(companyID, command.getAbsAppID());
		}

	}

	private void denyProcess(String companyID, String appID) {
		// アルゴリズム「詳細画面否認前の処理」を実行する
		beforeDenialProc.detailedScreenProcessBeforeDenial(companyID, appID, version);
		// アルゴリズム「詳細画面否認後の処理」を実行する
		detailAfterDeny.doDeny(companyID, appID, employeeID, memo);
	}

}
