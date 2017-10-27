package nts.uk.ctx.bs.person.ws.roles;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.roles.PersonInforRoleDto;
import find.roles.PersonInforRoleFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/roles")
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
