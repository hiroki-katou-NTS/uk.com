package nts.uk.ctx.pr.proto.ws;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.AsyncTaskStatus;
import nts.uk.ctx.core.app.company.command.AddCompanyCommand;
import nts.uk.ctx.core.app.company.command.AddCompanyCommandHandler;
@Path("/company/")
public class CompanyAsyncWebService extends WebService{
	@Inject
	AddCompanyCommandHandler addCompanyCommandHandler;
	@Path("/add/{command}")
	@POST
	@Produces("text/html")
	public AsyncTaskInfo addCompany(AddCompanyCommand command) {
		return this.addCompanyCommandHandler.handle(command);
	}
	@Path("/testadd")
	@GET
	public String sample() {
		AddCompanyCommand command = new AddCompanyCommand();
		command.setCompanyCode("1234");
		command.setCompanyName("Nittsu System");
		AsyncTaskInfo taskInfo = this.addCompanyCommandHandler.handle(command);
		//AsyncTaskStatus status = taskInfo.getStatus();
		String str = "<p>taskInfo: </p>" + "<p>Task Id = " + taskInfo.getId() + "</p>" ;
		return str;
	}
}