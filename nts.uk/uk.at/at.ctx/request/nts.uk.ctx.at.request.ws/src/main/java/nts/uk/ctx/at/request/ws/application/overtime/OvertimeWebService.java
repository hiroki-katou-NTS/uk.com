package nts.uk.ctx.at.request.ws.application.overtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.overtime.CreateOvertimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.CreateOvertimeCommandHandler;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.overtime.GetOvertime;
import nts.uk.ctx.at.request.app.find.overtime.dto.OverTimeDto;

@Path("at/request/application/overtime")
@Produces("application/json")
public class OvertimeWebService extends WebService{

	@Inject
	private GetOvertime overtimeFinder;
	@Inject
	private CreateOvertimeCommandHandler createHandler;
	
	@POST
	@Path("getOvertimeByUI")
	public OverTimeDto getOvertimeByUIType(Param param) {
		return this.overtimeFinder.getOvertimeByUIType(param.getUrl(), param.getAppDate(), param.getUiType());
	}
	
	@POST
	@Path("create")
	public void createOvertime(CreateOvertimeCommand command){
		createHandler.handle(command);
	}
}

@Value
class Param{
	private String url;
	private String appDate;
	private int uiType;
}