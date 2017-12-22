package nts.uk.ctx.sys.portal.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.sys.portal.app.command.layout.RegistryPortalLayoutCommand;
import nts.uk.ctx.sys.portal.app.command.layout.RegistryPortalLayoutCommandHandler;
import nts.uk.ctx.sys.portal.app.find.layout.LayoutDto;
import nts.uk.ctx.sys.portal.app.find.layout.PortalLayoutFinder;

/**
 * @author LamDT
 */
@Path("sys/portal/layout")
@Produces({"application/json","text/plain"})
public class PortalLayoutWebservice {

	@Inject
	PortalLayoutFinder portalLayoutFinder;

	@Inject
	RegistryPortalLayoutCommandHandler registryPortalLayoutCommandHandler;

	@POST
	@Path("active")
	public LayoutDto activeLayoutSetting(String layoutID) {
		return portalLayoutFinder.findLayout(layoutID);
	}

	@POST
	@Path("registry")
	public String registryLayoutSetting(RegistryPortalLayoutCommand command) {
		return registryPortalLayoutCommandHandler.handle(command);
	}

}
