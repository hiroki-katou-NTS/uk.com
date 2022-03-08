package nts.uk.ctx.sys.gateway.ws.login.sso.saml;

import nts.uk.ctx.sys.gateway.app.find.login.saml.operate.SamlOperationDto;
import nts.uk.ctx.sys.gateway.app.find.login.saml.operate.SamlOperationFinder;
import nts.uk.ctx.sys.gateway.app.find.login.saml.operate.SamlOperationQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/sys/gateway/login/sso/saml/operation")
@Produces(MediaType.APPLICATION_JSON)
public class SamlOperationWebService {

    @Inject
    private SamlOperationFinder finder;
    
    @Inject
    private SamlOperationQuery samlOperationQuery;

    @POST
    @Path("find/{tenantCode}")
    public SamlOperationDto find(@PathParam("tenantCode") String tenantCode) {
        return finder.find(tenantCode).orElse(null);
    }
    
    @POST
    @Path("find")
    public SamlOperationDto find() {
        return this.samlOperationQuery.find();
    }
}
