package nts.uk.ctx.sys.gateway.ws.contractcheck;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;

@Path("ctx/sys/gateway/contractcheck")
@Produces("application/json")
@Stateless
public class ContractCheckWebService extends WebService{
	
	@Inject
	private TenantAuthenticationRepository tenanrep;

	@POST
	@Path("check/{contractCode}")
	public boolean check(@PathParam("contractCode") String contractCode) {
			Optional<TenantAuthentication> tenanAuth = tenanrep.find(contractCode, GeneralDate.today());
			
			// During the usage period
			if (tenanAuth.isPresent())
				return true;
			else
				return false;
		
	}
}
