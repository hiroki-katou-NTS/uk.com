package nts.uk.ctx.sys.gateway.app.find.login.saml.operate;

import lombok.Value;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlRedirectUrl;

@Value
public class SamlOperationDto {

    String tenantCode;

    boolean useSingleSignOn;

    String idpRedirectUrl;

    public static SamlOperationDto fromDomain(SamlOperation domain) {
        return new SamlOperationDto(domain.getTenantCode(), domain.isUseSingleSignOn(), domain.getIdpRedirectUrl().v());
    }
}
