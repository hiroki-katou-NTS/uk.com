package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLクライアントID
 */
@StringMaxLength(50)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class SamlClientId extends StringPrimitiveValue<SamlClientId> {
    public SamlClientId(String rawValue) {
        super(rawValue);
    }
}
