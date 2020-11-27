package nts.uk.ctx.workflow.ws.agent;

import java.util.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.app.command.agent.*;
import nts.uk.ctx.workflow.app.find.agent.AgentDto;
import nts.uk.ctx.workflow.app.find.agent.AgentFinder;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmpInfoImport;

@Path("workflow/agent")
@Produces("application/json")
public class AgentWebService extends WebService {

	@Inject
	private AgentFinder agentFinder;

	@Inject
	private AddAgentCommandHandler addAgentCommandHandler;

	@Inject
	private UpdateAgentCommandHandler updateAgentCommandHandler;

	@Inject
	private DeleteAgentCommandHandler deleteAgentCommandHandler;
	
	@Inject
	private EmployeeAdapter employeeAdapter;

	@Inject
	private SendMailToApproverCommandHandler sendMailToApproverCommandHandler;

	@Inject
	private AgentReportExportService exportService;

	@Path("find/{employeeId}")
	@POST
	public List<AgentDto> findAll(@PathParam("employeeId") String employeeId) {
		return agentFinder.findAllEmploy(employeeId);

	}

	@Path("find")
	@POST
	public AgentDto find(AgentParam param) {
		return agentFinder.getAgentDto(param.getEmployeeId(), param.getRequestId());

	}

	@Path("findByCid")
	@POST
	public List<AgentDto> findByCid() {
		return agentFinder.findByCid();

	}

	@Path("add")
	@POST
	public JavaTypeResult<String> add(AgentCommandBase command) {
		return new JavaTypeResult<String>(this.addAgentCommandHandler.handle(command));
	}

	@Path("update")
	@POST
	public void update(AgentCommandBase command) {
		this.updateAgentCommandHandler.handle(command);
	}

	@Path("delete")
	@POST
	public void delete(DeleteAgentCommand command) {
		this.deleteAgentCommandHandler.handle(command);
	}

	@Path("findByDate")
	@POST
	public List<AgentDto> findAllDate(DateParam dateParam) {
		return agentFinder.findAll(dateParam.getStartDate(),dateParam.getEndDate());

	}
	
	@Path("findEmpInfo")
	@POST
	public EmpInfoImport findEmpName(String sId) {
		List<EmpInfoImport> result = employeeAdapter.getEmpInfo(Collections.singletonList(sId));
		return CollectionUtil.isEmpty(result) ? null : result.get(0);
	}

	@Path("sendMail")
	@POST
	public void sendEmail(SendEmailCommand command) {
		this.sendMailToApproverCommandHandler.handle(command);
	}

	@POST
	@Path("report/generate")
	public ExportServiceResult generate(List<Map<String, String>> query) {
		return this.exportService.start(query);
	}
}
