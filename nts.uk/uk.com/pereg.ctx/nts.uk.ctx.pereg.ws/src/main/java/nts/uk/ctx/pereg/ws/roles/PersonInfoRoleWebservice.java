package nts.uk.ctx.pereg.ws.roles;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.roles.PersonInforRoleDto;
import nts.uk.ctx.pereg.app.find.roles.PersonInforRoleFinder;

@Path("ctx/pereg/roles")
@Produces("application/json")
public class PersonInfoRoleWebservice extends WebService {
	@Inject
	PersonInforRoleFinder personInfoRoleFinder;

	@POST
	@Path("findAll")
	public List<PersonInforRoleDto> getAllPersonRole() {
		return personInfoRoleFinder.getAllPersonRole();

	}

	@POST
	@Path("find/{roleId}")
	public PersonInforRoleDto getDetailPersonRole(@PathParam("roleId") String roleId) {
		return personInfoRoleFinder.getDetailPersonRole(roleId).get();

	}

}
