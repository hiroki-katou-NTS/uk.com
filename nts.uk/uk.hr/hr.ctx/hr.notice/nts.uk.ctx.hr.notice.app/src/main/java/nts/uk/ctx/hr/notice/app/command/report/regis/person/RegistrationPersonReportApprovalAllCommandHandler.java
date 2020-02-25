package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.ActionApprove;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.ApproveReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.RegisterApproveHandler;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class RegistrationPersonReportApprovalAllCommandHandler
		extends CommandHandler<RegistrationPersonReportApprovalAllCommand> {

	@Inject
	private ApprovalPersonReportRepository repo;

	@Inject
	private RegisterApproveHandler approve;

	@Override
	protected void handle(CommandHandlerContext<RegistrationPersonReportApprovalAllCommand> context) {
		RegistrationPersonReportApprovalAllCommand cmd = context.getCommand();

		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();

		// [届出の承認]チェックON条件の検索で抽出した<LIST>届出ID,ルートインスタンスIDを取得する
		List<ApprovalPersonReport> reportList = this.repo.getByJHN003(cId, sId, cmd.getAppDate().getStartDate(),
				cmd.getAppDate().getEndDate(), cmd.getReportId(), cmd.getApprovalStatus(), cmd.getInputName());

		if (reportList.size() > 99) {
			throw new BusinessException("MsgJ_46");
		}
		reportList.forEach(x -> {
			ApproveReportCommand approveCmd = new ApproveReportCommand(String.valueOf(x.getReportID()),
					x.getRootSatteId(), TextResource.localize("JHN003_A1_1_1"), ActionApprove.APPROVE.value);

			// アルゴリズム[承認処理]を実行する
			this.approve.approveReport(cId, approveCmd);

		});

	}

}
