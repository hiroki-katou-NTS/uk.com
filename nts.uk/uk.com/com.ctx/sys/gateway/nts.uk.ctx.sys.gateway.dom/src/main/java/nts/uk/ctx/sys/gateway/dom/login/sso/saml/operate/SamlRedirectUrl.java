package nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLリダイレクトURL
 */
@StringMaxLength(500)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class SamlRedirectUrl extends StringPrimitiveValue<SamlRedirectUrl> {
    public SamlRedirectUrl(String rawValue) {
        super(rawValue);
    }
}
