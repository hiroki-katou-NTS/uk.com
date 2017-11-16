package nts.uk.ctx.sys.auth.ws.role;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.find.person.role.PersonInformationRoleFinder;
import nts.uk.ctx.sys.auth.app.find.person.role.dto.RoleDto;

@Path("ctx/sys/auth/role")
@Produces("application/json")
public class RoleWebservice extends WebService {
	@Inject
	private PersonInformationRoleFinder personInforRoleFinder;
	
	@POST
	@Path("getlistrolebytype/{roleType}")
	public List<RoleDto> getRoleSet(@PathParam("roleSetCd") int roleType) {
		return this.personInforRoleFinder.getListRoleByRoleType(roleType);
	}

}
