package nts.uk.ctx.pereg.ws.roles.auth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.roles.auth.SavePersonInfoRoleAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.SavePersonInfoRoleAuthCommandHandler;
import nts.uk.ctx.pereg.app.command.roles.auth.UpdatePersonInfoRoleAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.UpdatePersonInfoRoleAuthCommandHandler;
import nts.uk.ctx.pereg.app.find.roles.auth.PersonInfoRoleAuthDto;

@Path("ctx/pereg/roles/auth")
@Produces("application/json")
public class PersonInfoRoleAuthWebservice extends WebService {

	@Inject
	UpdatePersonInfoRoleAuthCommandHandler update;

	@Inject
	SavePersonInfoRoleAuthCommandHandler save;

	@POST
	@Path("findAll")
	public List<PersonInfoRoleAuthDto> getAllPersonInfoRoleAuth() {
		return new ArrayList<>();
	}

	@POST
	@Path("find/{roleId}")
	public PersonInfoRoleAuthDto getDetailPersonRoleAuth(@PathParam("roleId") String roleId) {
		return new PersonInfoRoleAuthDto();

	}

	@POST
	@Path("get-self-auth")
	public PersonInfoRoleAuthDto getSelfAuth() {
		return new PersonInfoRoleAuthDto();
	}

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
