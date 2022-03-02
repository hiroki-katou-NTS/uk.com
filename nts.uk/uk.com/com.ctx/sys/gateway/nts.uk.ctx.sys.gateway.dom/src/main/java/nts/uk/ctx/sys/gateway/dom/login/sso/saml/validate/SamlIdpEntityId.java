package nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * SAML IdPエンティティID
 */
@StringMaxLength(500)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class SamlIdpEntityId extends StringPrimitiveValue<SamlIdpEntityId> {
    public SamlIdpEntityId(String rawValue) {
        super(rawValue);
    }
}
