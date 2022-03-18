package nts.uk.ctx.sys.gateway.ws.login.sso.saml;

import nts.uk.ctx.sys.gateway.app.command.login.saml.assoc.AssociateIdpUserWithEmployeeCommand;
import nts.uk.ctx.sys.gateway.app.command.login.saml.assoc.AssociateIdpUserWithEmployeeCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/sys/gateway/login/sso/saml/associate")
@Produces(MediaType.APPLICATION_JSON)
public class SamlAssociationWebService {

    @Inject
    private AssociateIdpUserWithEmployeeCommandHandler associateHandler;

    @POST
    @Path("loginemployee")
    public void associateWithLoginEmployee(AssociateIdpUserWithEmployeeCommand command) {
        associateHandler.handle(command);
    }
}
