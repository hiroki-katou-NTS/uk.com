package nts.uk.shr.infra.web.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.RequestInfo;
import nts.uk.shr.infra.web.util.MenuRequestContainer;
import nts.uk.shr.infra.web.util.data.MenuRequestInfo;

@Path("/menu")
@Produces("application/json")
public class MenuRequestWebService {
	
	@POST
	@Path("requested")
	public void menuRequested() {
		RequestInfo reInfo = AppContexts.requestedWebApi();
		MenuRequestContainer.requestFromMenu(new MenuRequestInfo(reInfo.getRequestIpAddress(), GeneralDateTime.now(), reInfo.getFullRequestPath()));
	}
}
