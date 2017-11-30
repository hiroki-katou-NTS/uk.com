package nts.uk.screen.com.ws.sys.auth.roleset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.sys.auth.roleset.DeleteRoleSetCommandBase;
import nts.uk.screen.com.app.command.sys.auth.roleset.DeleteRoleSetCommandBaseHandler;
import nts.uk.screen.com.app.command.sys.auth.roleset.RegisterRoleSetCommandBaseHandler;
import nts.uk.screen.com.app.command.sys.auth.roleset.RoleSetCommandBase;
import nts.uk.screen.com.app.command.sys.auth.roleset.UpdateRoleSetCommandBaseHandler;


@Path("screen/sys/auth/roleset")
@Produces("application/json")
public class RoleSetWebservice extends WebService {


	@Inject
	private RegisterRoleSetCommandBaseHandler registerRoleSetCommandBaseHandler;

	@Inject
	private UpdateRoleSetCommandBaseHandler updateRoleSetCommandBaseHandler;

	@Inject
	private DeleteRoleSetCommandBaseHandler deleteRoleSetCommandBaseHandler;

	@POST
	@Path("addroleset")
	public JavaTypeResult<String> addRoleSet(RoleSetCommandBase command) {
		return new JavaTypeResult<String>(this.registerRoleSetCommandBaseHandler.handle(command));
	}

	@POST
	@Path("updateroleset")
	public void updateRoleSet(RoleSetCommandBase command) {
		this.updateRoleSetCommandBaseHandler.handle(command);
	}

	@POST
	@Path("deleteroleset")
	public void removeSelectionItem(DeleteRoleSetCommandBase command) {
		this.deleteRoleSetCommandBaseHandler.handle(command);
	}
	
}
