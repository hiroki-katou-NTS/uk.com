package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import java.util.Optional;

public interface SamlResponseValidationRepository {

    Optional<SamlResponseValidation> find(String tenantCode);

    void save(SamlResponseValidation validation);
}
