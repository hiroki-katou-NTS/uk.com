package nts.uk.ctx.sys.gateway.ws.login.sso.saml;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.find.login.saml.validate.SamlResponseValidationDto;
import nts.uk.ctx.sys.gateway.app.find.login.saml.validate.SamlResponseValidationQuery;

@Path("ctx/sys/gateway/login/sso/saml/validation")
@Produces(MediaType.APPLICATION_JSON)
public class SamlResponseValidationWebService extends WebService {

	@Inject
	private SamlResponseValidationQuery samlResponseValidationQuery;
	
	@POST
    @Path("find")
    public SamlResponseValidationDto find() {
        return this.samlResponseValidationQuery.find();
    }
}
