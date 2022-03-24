package nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAMLのIdPユーザ名
 */
@StringMaxLength(200)
public class SamlIdpUserName extends StringPrimitiveValue<SamlIdpUserName> {

    public SamlIdpUserName(String rawValue) {
        super(rawValue);
    }
}
