package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

public class ChangeAbsDateToHolidayCommandHandler extends CommandHandler<SaveHolidayShipmentCommand> {

	String companyID, appReason, employeeID;
	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
	AbsenceLeaveAppCommand absCmd;
	SaveHolidayShipmentCommand command;

	@Override
	protected void handle(CommandHandlerContext<SaveHolidayShipmentCommand> context) {

		// tạm thời chưa làm được do không có thông tin về domain
		command = context.getCommand();
		absCmd = command.getAbsCmd();
		// アルゴリズム「関連付けられた振出情報の取得」を実行する
		getChangeInfo();
	}

	private void getChangeInfo() {
		// INPUT.消化対象振休管理の件数分ループ
		// absCmd.getSubDigestions().forEach(x -> {
		// if (x.getPayoutMngDataID() != null) {
		// // Imported(就業.申請承認.休暇残数.振出振休)「振出情報」を取得する chưa làm domain
		// } else {
		// // Imported(就業.申請承認.休暇残数.振出振休)「振出情報」を取得する chưa làm domain
		// }
		// });
	}

}
