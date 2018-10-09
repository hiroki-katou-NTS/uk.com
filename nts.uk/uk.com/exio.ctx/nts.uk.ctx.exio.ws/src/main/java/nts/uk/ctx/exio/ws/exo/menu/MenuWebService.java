package nts.uk.ctx.exio.ws.exo.menu;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.menu.RoleAuthorityDto;
import nts.uk.ctx.exio.app.find.exo.menu.MenuFinder;

@Path("exio/exo/menu")
@Produces("application/json")
public class MenuWebService {
	@Inject
	private MenuFinder menuFinder;
	
	@POST
	@Path("startMenu")
	public RoleAuthorityDto startMenu(){
		return menuFinder.startMenu();
	}
}
