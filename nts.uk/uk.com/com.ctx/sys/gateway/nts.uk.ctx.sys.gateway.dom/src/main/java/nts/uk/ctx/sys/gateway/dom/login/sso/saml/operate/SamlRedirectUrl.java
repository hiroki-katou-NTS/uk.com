package nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLリダイレクトURL
 */
@StringMaxLength(500)
public class SamlRedirectUrl extends StringPrimitiveValue<SamlRedirectUrl> {
    public SamlRedirectUrl(String rawValue) {
        super(rawValue);
    }
}
