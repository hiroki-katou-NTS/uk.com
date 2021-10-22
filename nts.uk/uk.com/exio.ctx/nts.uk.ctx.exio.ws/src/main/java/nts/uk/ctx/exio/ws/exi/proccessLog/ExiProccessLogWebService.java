package nts.uk.ctx.exio.ws.exi.proccessLog;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.execlog.AddExacExeResultLogCommandHandler;
import nts.uk.ctx.exio.app.command.exi.execlog.ExacExeResultLogCommand;

/**
 * The Class ExiProccessLogWebService
 */
@Path("exio/exi/proccessLog")
@Produces("application/json")
public class ExiProccessLogWebService extends WebService {
	@Inject
	private AddExacExeResultLogCommandHandler addExacExeResultLogCommandHandler;

	/**
	 * @param exacErrorLogCommand
	 */
	@POST
	@Path("addExtResultLog")
	public JavaTypeResult<String> addErrorLog(ExacExeResultLogCommand exacExeResultLogCommand) {
		return new JavaTypeResult<String>(this.addExacExeResultLogCommandHandler.handle(exacExeResultLogCommand));
	}

}
