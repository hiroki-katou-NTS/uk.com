package nts.uk.ctx.pereg.ws.roles.auth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.roles.auth.SavePersonInfoRoleAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.SavePersonInfoRoleAuthCommandHandler;
import nts.uk.ctx.pereg.app.command.roles.auth.UpdatePersonInfoRoleAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.UpdatePersonInfoRoleAuthCommandHandler;

@Path("ctx/pereg/roles/auth")
@Produces("application/json")
public class PersonInfoRoleAuthWebservice extends WebService {

	@Inject
	UpdatePersonInfoRoleAuthCommandHandler update;

	@Inject
	SavePersonInfoRoleAuthCommandHandler save;

	@POST
	@Path("update")
	public void update(UpdatePersonInfoRoleAuthCommand command) {
		this.update.handle(command);
	}

	@POST
	@Path("save")
	public void save(SavePersonInfoRoleAuthCommand command) {
		this.save.handle(command);
	}

}
