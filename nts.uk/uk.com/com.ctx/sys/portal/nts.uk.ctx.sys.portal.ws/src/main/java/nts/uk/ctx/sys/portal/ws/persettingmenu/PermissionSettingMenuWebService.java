package nts.uk.ctx.sys.portal.ws.persettingmenu;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.uk.ctx.sys.portal.app.find.persettingmenu.PermissionSettingMenuDto;
import nts.uk.ctx.sys.portal.app.find.persettingmenu.PermissionSettingMenuFinder;
import nts.uk.ctx.sys.portal.dom.permissionmenu.RoleType;

@Path("sys/portal/standardmenu")
@Produces("application/json")
public class PermissionSettingMenuWebService {
	@Inject
	private PermissionSettingMenuFinder perSettingFinder;
	
	@POST
	@Path("find/for/person/role")
	public List<PermissionSettingMenuDto> findForPersonRole(){
		return perSettingFinder.findByRoleType(RoleType.PERSONAL_INFO.value);
	} 
}
