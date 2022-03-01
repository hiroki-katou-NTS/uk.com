package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLクライアント証明書
 */
@StringMaxLength(1000)
public class SamlClientCertificate extends StringPrimitiveValue<SamlClientCertificate> {
    public SamlClientCertificate(String rawValue) {
        super(rawValue);
    }
}
