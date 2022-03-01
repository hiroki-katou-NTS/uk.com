package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAML IdPエンティティID
 */
@StringMaxLength(500)
public class SamlIdpEntityId extends StringPrimitiveValue<SamlIdpEntityId> {
    public SamlIdpEntityId(String rawValue) {
        super(rawValue);
    }
}
