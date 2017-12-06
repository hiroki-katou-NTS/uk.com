package nts.uk.ctx.sys.auth.ws.role;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.auth.app.find._role.InforRoleSetJobDto;
import nts.uk.ctx.sys.auth.app.find._role.RoleIndividualGrantDto;
import nts.uk.ctx.sys.auth.app.find._role.RoleTypeFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/sys/auth/roletype")
@Produces("application/json")
public class RoleTypeWebservice extends WebService {

	@Inject
	private RoleTypeFinder roleTypeFinder;
	
	
	@POST
	@Path("getallbyroletype")
	public InforRoleSetJobDto getAll(String value){
		try {
			int id = Integer.parseInt(value);
			return this.roleTypeFinder.getAllByRoleType(id);
		} catch (Exception e) {
			return null;
		}
	}

	@POST
	@Path("getrolebyroleId")
	public List<RoleIndividualGrantDto> getRoleByRoleType(String roleId){
		return this.roleTypeFinder.findByRoleId(roleId);
	}
	
	@POST
	@Path("getrolegrantbyuserId")
	public RoleIndividualGrantDto getRolegantByuserId(DataRequest data){
		if(data.getUserId()=="" || data.getRoleId()=="")
			return null;
		return this.roleTypeFinder.findByRoleGrant(data.getUserId(), data.getRoleId());
	}
	
}
