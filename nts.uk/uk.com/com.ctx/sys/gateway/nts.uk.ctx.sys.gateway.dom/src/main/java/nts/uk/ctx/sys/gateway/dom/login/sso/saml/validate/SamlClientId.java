package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLクライアントID
 */
@StringMaxLength(50)
public class SamlClientId extends StringPrimitiveValue<SamlClientId> {
    public SamlClientId(String rawValue) {
        super(rawValue);
    }
}
