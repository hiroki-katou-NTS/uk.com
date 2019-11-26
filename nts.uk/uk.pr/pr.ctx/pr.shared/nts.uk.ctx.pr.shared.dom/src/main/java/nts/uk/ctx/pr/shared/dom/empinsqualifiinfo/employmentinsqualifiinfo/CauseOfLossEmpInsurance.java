package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 雇用保険喪失原因
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(40)
public class CauseOfLossEmpInsurance extends StringPrimitiveValue <CauseOfLossEmpInsurance> {
    public CauseOfLossEmpInsurance(String rawValue) {
        super(rawValue);
    }
}
