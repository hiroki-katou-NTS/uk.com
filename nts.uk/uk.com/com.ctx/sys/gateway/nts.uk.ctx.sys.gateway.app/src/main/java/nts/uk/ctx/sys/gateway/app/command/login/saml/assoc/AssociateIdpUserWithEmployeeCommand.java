package nts.uk.ctx.sys.gateway.app.command.login.saml.assoc;

import lombok.Value;

@Value
public class AssociateIdpUserWithEmployeeCommand {

    String idpUserName;
}
