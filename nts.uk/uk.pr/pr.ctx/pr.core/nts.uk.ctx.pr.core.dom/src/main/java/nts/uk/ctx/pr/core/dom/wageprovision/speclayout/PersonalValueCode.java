package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 個人金額コード
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class PersonalValueCode extends CodePrimitiveValue<PersonalValueCode> {

        private static final long serialVersionUID = 1L;
        public PersonalValueCode(String rawValue) {
            super(rawValue);
        }
}
