package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 退職解雇理由名称
 */
@StringMaxLength(40)
public class RetirementReasonClsCode extends StringPrimitiveValue<RetirementReasonClsCode> {
    public RetirementReasonClsCode(String rawValue) {
        super(rawValue);
    }
}
