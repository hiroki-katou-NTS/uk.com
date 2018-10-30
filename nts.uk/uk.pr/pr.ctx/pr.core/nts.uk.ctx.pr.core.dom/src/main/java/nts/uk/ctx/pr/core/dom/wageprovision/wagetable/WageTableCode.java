package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class WageTableCode extends StringPrimitiveValue<WageTableCode> {

    private static final long serialVersionUID = 1L;

    public WageTableCode(String rawValue) {
        super(rawValue);
    }
}
