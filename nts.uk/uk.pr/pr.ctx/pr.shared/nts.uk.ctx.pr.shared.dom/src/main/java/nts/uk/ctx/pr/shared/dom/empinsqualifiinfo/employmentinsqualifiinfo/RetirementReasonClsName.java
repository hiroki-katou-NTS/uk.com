package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 退職解雇理由名称
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(40)
public class RetirementReasonClsName extends StringPrimitiveValue <RetirementReasonClsName> {
    public RetirementReasonClsName(String rawValue) {
        super(rawValue);
    }
}
