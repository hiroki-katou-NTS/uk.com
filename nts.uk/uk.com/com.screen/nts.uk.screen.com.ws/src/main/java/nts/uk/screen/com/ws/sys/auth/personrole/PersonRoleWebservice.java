package nts.uk.screen.com.ws.sys.auth.personrole;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.sys.auth.personrole.delete.DeletePersonRoleCommand;
import nts.uk.screen.com.app.command.sys.auth.personrole.delete.DeletePersonRoleCommandHandler;
import nts.uk.screen.com.app.command.sys.auth.personrole.register.RegisterPersonRoleCommand;
import nts.uk.screen.com.app.command.sys.auth.personrole.register.RegisterPersonRoleCommandHandler;

@Path("ctx/com/screen/person/role")
@Produces("application/json")
public class PersonRoleWebservice extends WebService {

	@Inject
	private RegisterPersonRoleCommandHandler commandHandler;
	
	@Inject
	private DeletePersonRoleCommandHandler deleteHandler;

	@POST
	@Path("register")
	public void registerAuth(RegisterPersonRoleCommand command) {
		commandHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delteAuth(DeletePersonRoleCommand command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("check")
	public void checkRole(String roleId) {
		deleteHandler.checkRoleUse(roleId);
	}

}
