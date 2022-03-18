package nts.uk.ctx.sys.gateway.app.find.login.saml.validate;

import lombok.Value;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidation;

@Value
public class SamlResponseValidationDto {

    private String tenantCode;
    
    private String clientId;
    
    private String idpEntityId;
    
    private String idpCertificate;
    
    public static SamlResponseValidationDto fromDomain(SamlResponseValidation domain) {
        return new SamlResponseValidationDto(
        		domain.getTenantCode(),
        		domain.getClientId().v(),
        		domain.getIdpEntityId().v(),
        		domain.getIdpCertificate().v());
    }
}
