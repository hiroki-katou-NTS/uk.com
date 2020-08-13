package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 勤務場所コード
 */

@StringMaxLength(7)
@ZeroPaddedCode
public class WorkLocationCode extends UpperCaseAlphaNumericPrimitiveValue<WorkLocationCode> {

    public WorkLocationCode(String rawValue) {
        super(rawValue);
    }
}
