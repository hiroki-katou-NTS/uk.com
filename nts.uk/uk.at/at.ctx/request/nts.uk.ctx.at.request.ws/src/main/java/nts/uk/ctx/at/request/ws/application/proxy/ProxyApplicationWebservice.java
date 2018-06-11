package nts.uk.ctx.at.request.ws.application.proxy;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.proxy.ProxyApplicationFinder;
import nts.uk.ctx.at.request.app.find.application.proxy.ProxyParamFind;

/**
 * @author sang.nv
 * 代行申請
 */
@Path("at/request/application/proxy")
@Produces("application/json")
public class ProxyApplicationWebservice extends WebService {

	@Inject
	private ProxyApplicationFinder proxyApplicationFinder;

	@POST
	@Path("find")
	public void selectApplicationByType(ProxyParamFind proxyParamFind) {
		this.proxyApplicationFinder.selectApplicationByType(proxyParamFind);
	}
}
