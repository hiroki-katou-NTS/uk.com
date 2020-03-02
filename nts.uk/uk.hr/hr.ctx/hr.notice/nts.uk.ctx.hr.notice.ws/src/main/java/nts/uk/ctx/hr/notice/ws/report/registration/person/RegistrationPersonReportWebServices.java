package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.RegistrationPersonReportApprovalAllCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.RegistrationPersonReportApprovalAllCommandHandler;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.PersonReportQuery;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportFinder;

@Path("hr/notice/report/regis/person/report")
@Produces("application/json")
public class RegistrationPersonReportWebServices {

	@Inject
	private RegistrationPersonReportFinder finder;

	@Inject
	private RegistrationPersonReportApprovalAllCommandHandler handler;

	@POST
	@Path("search")
	public List<RegistrationPersonReportDto> findPersonReport(PersonReportQuery query) {
		return this.finder.findPersonReport(query);
	}

	@POST
	@Path("approval-all")
	public void approvalAll(RegistrationPersonReportApprovalAllCommand command) {
		this.handler.handle(command);
	}

	@POST
	@Path("start-page")
	public List<PersonalReportClassificationDto> startPage() {
		return this.finder.startPage();
	}

}
