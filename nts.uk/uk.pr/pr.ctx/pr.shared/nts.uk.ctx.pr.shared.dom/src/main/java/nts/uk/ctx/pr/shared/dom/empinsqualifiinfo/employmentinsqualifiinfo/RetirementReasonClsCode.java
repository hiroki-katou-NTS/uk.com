package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 雇用保険喪失原因
 */
@StringMaxLength(2)
public class RetirementReasonClsCode extends StringPrimitiveValue<RetirementReasonClsCode> {
    public RetirementReasonClsCode(String rawValue) {
        super(rawValue);
    }
}
