package nts.uk.ctx.exio.ws.exi.proccessLog;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.execlog.AddExacExeResultLogCommandHandler;
import nts.uk.ctx.exio.app.command.exi.execlog.ExacExeResultLogCommand;

/**
 * The Class ExiProccessLogWebService
 */
@Path("ctx/exio/ws/exi/proccessLog")
@Produces("application/json")
public class ExiProccessLogWebService extends WebService{
	@Inject
	private AddExacExeResultLogCommandHandler addExacExeResultLogCommandHandler;
	
	/**
	 * @param exacErrorLogCommand
	 */
	@POST
	@Path("addErrorLog")
	public void addErrorLog(ExacExeResultLogCommand exacExeResultLogCommand) {
		this.addExacExeResultLogCommandHandler.handle(exacExeResultLogCommand);
	}
	
}
