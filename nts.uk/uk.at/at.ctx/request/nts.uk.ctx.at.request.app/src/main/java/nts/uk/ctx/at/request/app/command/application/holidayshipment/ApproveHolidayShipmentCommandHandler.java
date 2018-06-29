package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApproveHolidayShipmentCommandHandler
		extends CommandHandlerWithResult<HolidayShipmentCommand, List<String>> {

	@Inject
	private DetailBeforeUpdate detailBefUpdate;
	@Inject
	private DetailAfterApproval_New detailAfAppv;

	@Override
	protected List<String> handle(CommandHandlerContext<HolidayShipmentCommand> context) {
		HolidayShipmentCommand command = context.getCommand();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		Long version = command.getAppVersion();
		String memo = context.getCommand().getMemo();
		List<String> refAppIds = new ArrayList<>();
		// アルゴリズム「振休振出申請の承認」を実行する
		approvalApplication(command, companyID, employeeID, version, memo, refAppIds);

		return refAppIds;
	}

	private void approvalApplication(HolidayShipmentCommand command, String companyID, String employeeeID, Long version,
			String memo, List<String> refAppIds) {
		boolean isApprovalRec = command.getRecAppID() != null;
		boolean isApprovalAbs = command.getAbsAppID() != null;

		if (isApprovalRec) {
			// アルゴリズム「承認処理」を実行する
			approvalProcessing(companyID, command.getRecAppID(), employeeeID, version, memo, refAppIds);
		}
		if (isApprovalAbs) {
			// アルゴリズム「承認処理」を実行する
			approvalProcessing(companyID, command.getAbsAppID(), employeeeID, version, memo, refAppIds);
		}
	}

	private void approvalProcessing(String companyID, String appID, String employeeID, Long version, String memo,
			List<String> refAppIds) {
		// アルゴリズム「詳細画面登録前の処理」を実行する
		detailBefUpdate.processBeforeDetailScreenRegistration(companyID, employeeID, GeneralDate.today(), 1, appID,
				PrePostAtr.PREDICT, version);
		// アルゴリズム「申請個別のエラーチェック」を実行する không xử lý

		// xử lý đồng thời
		// アルゴリズム「申請個別の更新」を実行する
		// アルゴリズム「詳細画面承認後の処理」を実行する
		ProcessResult result = detailAfAppv.doApproval(companyID, appID, employeeID, memo);

		if (StringUtils.isNotEmpty(result.getReflectAppId())) {
			refAppIds.add(result.getReflectAppId());
		}

	}

}
