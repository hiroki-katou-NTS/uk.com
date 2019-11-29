package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * FD番号
 */
@StringMaxLength(3)
@ZeroPaddedCode
@StringCharType(CharType.NUMERIC)
public class FdNumber extends CodePrimitiveValue<FdNumber> {

    private static final long serialVersionUID = 1L;

    public FdNumber(String rawValue) {
        super(rawValue);
    }
}
