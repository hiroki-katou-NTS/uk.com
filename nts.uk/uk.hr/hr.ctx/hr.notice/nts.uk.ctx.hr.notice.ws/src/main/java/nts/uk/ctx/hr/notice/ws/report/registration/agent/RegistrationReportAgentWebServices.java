package nts.uk.ctx.hr.notice.ws.report.registration.agent;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.agent.SaveAgentRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;
import nts.uk.ctx.hr.notice.app.find.report.ReportLayoutDto;
import nts.uk.ctx.hr.notice.app.find.report.ReportParams;
import nts.uk.ctx.hr.notice.app.find.report.ReportItemAgentFinder;

@Path("hr/notice/report/regis/report/agent")
@Produces("application/json")
public class RegistrationReportAgentWebServices {

	
	@Inject
	private SaveAgentRegisPersonReportHandler save;
	
	@Inject
	private ReportItemAgentFinder reportItemAgentFinder;
	
	
	@POST
	@Path("findOne")
	public ReportLayoutDto getLayoutById(ReportParams params) {
		ReportLayoutDto dto = this.reportItemAgentFinder.getDetailReportCls(params);
		return dto;
	}
	
	@POST
	@Path("/save")
	public void save(SaveReportInputContainer inputContainer) {
		save.handle(inputContainer);
	}

}
