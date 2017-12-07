package nts.uk.ctx.sys.auth.ws.role;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

@Path("ctx/sys/auth/role/")
@Produces("application/json")
public class WorkPlaceWebservice extends WebService {
	
	@POST
	@Path("getListOfDescriptionWorkPlacePermissionByRoleId/{roleId}")
	public List<DescriptionFunctionPermissionDto> getListOfDescriptionWorkPlaceByRoleId(@PathParam("roleId") int roleId) {
		return new ArrayList<DescriptionFunctionPermissionDto>();
	}

    @POST
    @Path("getListOfAvialabilityWorkPlacePermissionByRoleId/{roleId}")
    public ArrayList<AvialabilityFunctionPermissionDto> getListOfAvialabiltyWorkPlaceByRoleId(@PathParam("roleId") int roleId) {
        return new ArrayList<AvialabilityFunctionPermissionDto>();
    }
	
}
