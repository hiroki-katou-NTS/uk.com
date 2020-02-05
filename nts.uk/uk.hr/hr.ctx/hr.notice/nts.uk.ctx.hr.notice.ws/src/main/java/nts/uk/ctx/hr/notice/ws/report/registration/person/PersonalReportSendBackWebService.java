package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.DelRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.RemoveReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveDraftRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.PersonalReportDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportFinder;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.approve.ApprovalPersonReportDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.approve.ApprovalPersonReportFinder;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.shr.com.context.AppContexts;

@Path("hr/notice/report/regis/person")
@Produces("application/json")
public class PersonalReportSendBackWebService {

	@Inject
	private ApprovalPersonReportFinder finder;

	@POST
	@Path("get")
	public List<ApprovalPersonReportDto> getListApprovalPersonReport() {
		return finder.getListDomain(1);
	}

	@POST
	@Path("save")
	public void save(SaveReportInputContainer inputContainer) {
		//this.save.handle(inputContainer);
	}

}
