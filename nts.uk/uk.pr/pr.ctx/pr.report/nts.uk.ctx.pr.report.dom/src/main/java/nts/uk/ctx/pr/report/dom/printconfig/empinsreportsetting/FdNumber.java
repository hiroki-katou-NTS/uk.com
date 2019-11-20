package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * FD番号
 */

@IntegerRange(min = 0, max = 9999)
public class FdNumber extends IntegerPrimitiveValue<FdNumber> {

    private static final long serialVersionUID = 1L;

    public FdNumber(int rawValue) {
        super(rawValue);
    }
}
