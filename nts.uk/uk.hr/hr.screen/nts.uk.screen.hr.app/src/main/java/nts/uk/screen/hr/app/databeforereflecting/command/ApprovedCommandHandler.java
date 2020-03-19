package nts.uk.screen.hr.app.databeforereflecting.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.ActionApprove;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.ApproveReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.RegisterApproveHandler;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class ApprovedCommandHandler extends CommandHandler<ApprovedCommand> {
	
	@Inject
	private RegisterApproveHandler approve;

	@Override
	protected void handle(CommandHandlerContext<ApprovedCommand> context) {

		String cId = AppContexts.user().companyId();

//		ApproveReportCommand approveCmd = new ApproveReportCommand(String.valueOf(x.getReportID()), x.getRootSatteId(),
//				TextResource.localize("JHN003_A1_1_1"), ActionApprove.APPROVE.value);
		// アルゴリズム[承認処理]を実行する
//		this.approve.approveReport(cId, approveCmd);
	}

}
