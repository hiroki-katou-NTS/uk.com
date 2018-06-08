package nts.uk.ctx.pereg.ws.roles.functionauth;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.roles.auth.functionauth.RegisterFuncAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.functionauth.RegisterFuncAuthCommandHandler;
import nts.uk.ctx.pereg.app.find.roles.functionauth.PersonInfoAuthDto;
import nts.uk.ctx.pereg.app.find.roles.functionauth.PersonInfoAuthFinder;

@Path("ctx/pereg/functions/auth")
@Produces("application/json")
public class FunctionAuthWebservice extends WebService {
	
	@Inject
	private PersonInfoAuthFinder authFinder;
	
	@Inject
	private RegisterFuncAuthCommandHandler commandHandler;

	@POST
	@Path("find-all")
	public List<PersonInfoAuthDto> getAllFunctionAuth(String roleId) {
		return authFinder.getListAuth(roleId);

	}
	
	@POST
	@Path("register")
	public void registerAuth(RegisterFuncAuthCommand command) {
		commandHandler.handle(command);
	}

}
