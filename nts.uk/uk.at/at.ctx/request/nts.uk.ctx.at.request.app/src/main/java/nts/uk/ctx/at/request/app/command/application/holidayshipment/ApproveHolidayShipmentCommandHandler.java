package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApproveHolidayShipmentCommandHandler extends CommandHandler<HolidayShipmentCommand> {

	String companyID, employeeID, memo;
	Long version;

	@Inject
	private DetailBeforeUpdate detailBefUpdate;
	@Inject
	private DetailAfterApproval_New detailAfAppv;

	@Override
	protected void handle(CommandHandlerContext<HolidayShipmentCommand> context) {

		HolidayShipmentCommand command = context.getCommand();
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();
		version = command.getAppVersion();
		memo = context.getCommand().getMemo();
		// アルゴリズム「振休振出申請の承認」を実行する
		approvalApplication(command);
	}

	private void approvalApplication(HolidayShipmentCommand command) {
		boolean isApprovalRec = command.getRecAppID() != null;
		boolean isApprovalAbs = command.getAbsAppID() != null;
		if (isApprovalRec) {
			// アルゴリズム「承認処理」を実行する
			approvalProcessing(companyID, command.getRecAppID());
		}
		if (isApprovalAbs) {
			// アルゴリズム「承認処理」を実行する
			approvalProcessing(companyID, command.getAbsAppID());

		}
	}

	private void approvalProcessing(String companyID, String appID) {
		// アルゴリズム「詳細画面登録前の処理」を実行する
		detailBefUpdate.processBeforeDetailScreenRegistration(companyID, employeeID, GeneralDate.today(), 1, appID,
				PrePostAtr.PREDICT, version);
		// アルゴリズム「申請個別のエラーチェック」を実行する không xử lý

		// xử lý đồng thời
		// アルゴリズム「申請個別の更新」を実行する
		// アルゴリズム「詳細画面承認後の処理」を実行する
		detailAfAppv.doApproval(companyID, appID, employeeID, memo);

	}

}
