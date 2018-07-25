package nts.uk.ctx.sys.assist.ws.systemtype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.command.system.SystemTypeCommandHandler;
import nts.uk.ctx.sys.assist.app.command.system.SystemTypeCommand;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeResult;

@Path("ctx/sys/assist/systemtype")
@Produces("application/json")
public class SystemTypeWebService extends WebService {
	@Inject
	private SystemTypeCommandHandler systemTypeComandHandler;
	
	@POST
	@Path("getsystemtypes")
	public List<SystemTypeResult> getListSystemType(SystemTypeCommand command) {
		return this.systemTypeComandHandler.handle(command);
	}

}
