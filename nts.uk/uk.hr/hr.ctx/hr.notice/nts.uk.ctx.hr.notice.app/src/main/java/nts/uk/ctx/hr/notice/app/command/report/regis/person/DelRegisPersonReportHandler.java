/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class DelRegisPersonReportHandler extends CommandHandler<RemoveReportCommand>{
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveReportCommand> context) {
		RemoveReportCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		Optional<RegistrationPersonReport> domainReportOpt = repo.getDomainByReportId(cid, command.reportId);
		if (!domainReportOpt.isPresent()) {
			return;
		}
		
		repo.remove(cid, command.reportId);
	}
	
}
