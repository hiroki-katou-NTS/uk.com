package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegistrationPersonReportApprovalAllCommandHandler
		extends CommandHandler<RegistrationPersonReportApprovalAllCommand> {

	@Inject
	private ApprovalPersonReportRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RegistrationPersonReportApprovalAllCommand> context) {
		RegistrationPersonReportApprovalAllCommand cmd = context.getCommand();

		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();
		
		List<ApprovalPersonReport> reportList =
				this.repo.getByJHN003(cId, sId, cmd.getAppDate().getStartDate(), cmd.getAppDate().getEndDate(),
						cmd.getReportId(), cmd.getApprovalStatus(), cmd.getInputName());
		
		if (reportList.size() > 99) {
			throw new BusinessException("Msgj_46");
		}
		
		
	}

}
