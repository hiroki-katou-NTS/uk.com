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
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class DelRegisPersonReportHandler extends CommandHandler<Integer>{
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Inject
	private ReportItemRepository reportItemRepo;

	@Override
	protected void handle(CommandHandlerContext<Integer> context) {
		Integer reportId = context.getCommand();
		String cid = AppContexts.user().companyId();
		Optional<RegistrationPersonReport> domainReportOpt = repo.getDomainByReportId(cid, reportId);
		if (!domainReportOpt.isPresent()) {
			return;
		}
		
		repo.remove(cid, reportId);
		reportItemRepo.deleteByReportId(cid, reportId);
		
	}
	
}
