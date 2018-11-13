package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 明細書コード
 */
@StringMaxLength(2)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class SpecCode extends CodePrimitiveValue<SpecName> {

    private static final long serialVersionUID = 1L;

    public SpecCode(String rawValue) {
        super(rawValue);

    }
}