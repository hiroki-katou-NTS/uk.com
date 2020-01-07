package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;

@Stateless
public class RegistrationPersonReportApprovalAllCommandHandler
		extends CommandHandler<RegistrationPersonReportApprovalAllCommand> {
	
	@Inject
	private ApprovalPersonReportRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RegistrationPersonReportApprovalAllCommand> context) {

	}

}
