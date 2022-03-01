package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import com.onelogin.saml2.util.Constants;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlSetting;
import nts.gul.security.saml.ValidSamlResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * SAMLの検証
 */
@AllArgsConstructor
@ToString
@Slf4j
public class SamlResponseValidation implements DomainAggregate {

    @Getter
    private final String tenantCode;

    @Getter
    private SamlIdpEntityId idpEntityId;

    @Getter
    private SamlClientId clientId;

    @Getter
    private SamlClientCertificate clientCertificate;

    public Optional<ValidSamlResponse> validate(HttpServletRequest request) {

        val samlSetting = toSamlSetting();

        try {
            val validated = SamlResponseValidator.validate(request, samlSetting);
            return Optional.of(validated);
        } catch (SamlResponseValidator.ValidateException e) {
            log.error("failed to validate SAML response", e);
            return Optional.empty();
        }
    }

    @SneakyThrows
    private SamlSetting toSamlSetting() {

        val samlSetting = new SamlSetting();

        samlSetting.setIDPEntityId(idpEntityId.v());
        samlSetting.setIdpx509Certificate(clientCertificate.v());
        samlSetting.setSPEntityId(clientId.v());
        samlSetting.setSignatureAlgorithm(Constants.RSA_SHA256); // 今はこれだけサポート(KeyCloak default)

        return samlSetting;
    }

}
