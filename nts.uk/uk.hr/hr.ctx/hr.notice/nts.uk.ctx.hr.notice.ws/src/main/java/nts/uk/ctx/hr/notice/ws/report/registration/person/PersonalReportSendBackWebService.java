package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.ApproveReportSendBackCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.RegisterApproveSendBackCommandHandler;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.approve.ApprovalPersonReportDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.approve.ApprovalPersonReportFinder;

@Path("hr/notice/report/regis/person/sendback")
@Produces("application/json")
public class PersonalReportSendBackWebService {

	@Inject
	private ApprovalPersonReportFinder finder;
	
	@Inject
	private RegisterApproveSendBackCommandHandler save;

	@POST
	@Path("get")
	public List<ApprovalPersonReportDto> getListApprovalPersonReport() {
		return finder.getListDomain(1);
	}

	@POST
	@Path("save")
	public void save(ApproveReportSendBackCommand command) {
		this.save.handle(command);
	}

}
