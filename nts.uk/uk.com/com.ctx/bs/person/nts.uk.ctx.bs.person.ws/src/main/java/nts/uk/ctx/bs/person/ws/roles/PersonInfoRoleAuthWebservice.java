package nts.uk.ctx.bs.person.ws.roles;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.roles.auth.PersonInfoRoleAuthDto;
import find.roles.auth.PersonInfoRoleAuthFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/roles/auth")
@Produces("application/json")
public class PersonInfoRoleAuthWebservice extends WebService {
	@Inject
	PersonInfoRoleAuthFinder personInfoRoleAuthFinder;

	@POST
	@Path("findAll")
	public List<PersonInfoRoleAuthDto> getAllPersonInfoRoleAuth() {
		return personInfoRoleAuthFinder.getAllPersonInfoRoleAuth();

	}
	
	@POST
	@Path("find/{roleId}")
	public PersonInfoRoleAuthDto getDetailPersonRoleAuth(@PathParam("roleId") String roleId) {
		PersonInfoRoleAuthDto  roleDto=  personInfoRoleAuthFinder.getDetailPersonRoleAuth(roleId).orElse(new PersonInfoRoleAuthDto("",0,0,0,0,0,0));
		return roleDto;

	}

}
