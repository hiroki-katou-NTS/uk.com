package nts.uk.ctx.hr.notice.ws.report.registration.agent;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.hr.notice.app.command.report.regis.agent.SaveAgentRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;

@Path("hr/notice/report/regis/report/agent")
@Produces("application/json")
public class RegistrationReportAgentWebServices {

	
	@Inject
	private SaveAgentRegisPersonReportHandler save;
	
	
	@POST
	@Path("/save")
	public JavaTypeResult<Integer> save(SaveReportInputContainer inputContainer) {
		return new JavaTypeResult<Integer>(this.save.handle(inputContainer));
	}

}
