package nts.uk.ctx.sys.gateway.ws.system;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.shr.com.operation.SystemOperationSettingAdapter;

@Path("ctx/sys/gateway/system")
@Produces("application/json")
@Stateless
public class SystemOperationSettingWebService extends WebService {
	
	@Inject
	private SystemOperationSettingAdapter finder;
	
	@POST
	@Path("is-display-warning")
	public boolean isDisplayWarningMsg() {
		return this.finder.getSetting().isDisplay();
	}
}
