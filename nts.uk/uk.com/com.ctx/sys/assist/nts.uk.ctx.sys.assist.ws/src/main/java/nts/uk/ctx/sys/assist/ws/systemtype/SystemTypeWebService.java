package nts.uk.ctx.sys.assist.ws.systemtype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.comand.system.SystemTypeComandHandler;
import nts.uk.ctx.sys.assist.app.comand.system.SystemTypeCommand;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeResult;

@Path("ctx/sys/assist/systemtype")
@Produces("application/json")
public class SystemTypeWebService {
	@Inject
	private SystemTypeComandHandler systemTypeComandHandler;
	
	@POST
	@Path("getlistsystemtype")
	public List<SystemTypeResult> getListSystemType(SystemTypeCommand command) {
		return this.systemTypeComandHandler.handle(command);
	}

}
