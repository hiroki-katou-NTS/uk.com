package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(2)
public class CauseOfLossEmpInsurance extends StringPrimitiveValue <CauseOfLossEmpInsurance> {
    public CauseOfLossEmpInsurance(String rawValue) {
        super(rawValue);
    }
}
