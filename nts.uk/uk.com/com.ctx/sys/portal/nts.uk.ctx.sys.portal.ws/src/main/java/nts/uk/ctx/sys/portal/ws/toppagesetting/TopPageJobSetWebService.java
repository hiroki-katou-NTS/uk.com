package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.portal.app.command.toppagesetting.UpdateTopPageSettingCommand;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.UpdateTopPageSettingCommandHandler;

/**
 * 
 * @author sonnh1
 *
 */
@Path("sys/portal/toppagesetting")
@Produces("application/json")
public class TopPageJobSetWebService {

	@Inject
	UpdateTopPageSettingCommandHandler updateTopPageSettingCommandHandler;

	@POST
	@Path("update")
	public void update(List<UpdateTopPageSettingCommand> command) {
		this.updateTopPageSettingCommandHandler.handle(command);
	}
}
