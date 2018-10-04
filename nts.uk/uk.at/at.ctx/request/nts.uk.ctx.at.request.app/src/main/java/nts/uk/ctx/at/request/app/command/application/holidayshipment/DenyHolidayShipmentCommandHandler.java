package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterDeny;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessDenial;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DenyHolidayShipmentCommandHandler extends CommandHandlerWithResult<HolidayShipmentCommand, ProcessResult> {

	@Inject
	private BeforeProcessDenial beforeDenialProc;
	@Inject
	private DetailAfterDeny detailAfterDeny;

	@Override
	protected ProcessResult handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		HolidayShipmentCommand command = context.getCommand();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		Long version = command.getAppVersion();
		String memo = command.getMemo();
		// アルゴリズム「振休振出申請の否認」を実行する
		return denyApplication(command, companyID, employeeID, memo, version);

	}

	private ProcessResult denyApplication(HolidayShipmentCommand command, String companyID, String employeeID,
			String memo, Long version) {
		boolean isDenyRec = command.getRecAppID() != null;
		boolean isDenyAbs = command.getAbsAppID() != null;
		ProcessResult result = null;
		if (isDenyRec) {
			// アルゴリズム「否認処理」を実行する
			result = denyProcess(companyID, command.getRecAppID(), employeeID, memo, version);
		}

		if (isDenyAbs) {
			// アルゴリズム「否認処理」を実行する
			result = denyProcess(companyID, command.getAbsAppID(), employeeID, memo, version);
		}

		return result;

	}

	private ProcessResult denyProcess(String companyID, String appID, String employeeID, String memo, Long version) {
		// アルゴリズム「詳細画面否認前の処理」を実行する
		beforeDenialProc.detailedScreenProcessBeforeDenial(companyID, appID, version);
		// アルゴリズム「詳細画面否認後の処理」を実行する
		return detailAfterDeny.doDeny(companyID, appID, employeeID, memo);
	}

}
