package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLクライアント証明書
 */
@StringMaxLength(1000)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class SamlClientCertificate extends StringPrimitiveValue<SamlClientCertificate> {
    public SamlClientCertificate(String rawValue) {
        super(rawValue);
    }
}
