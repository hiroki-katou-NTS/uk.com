package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(0)
@IntegerMaxValue(99)
public class InsuranceGrade extends IntegerPrimitiveValue<InsuranceGrade> {
    public InsuranceGrade(Integer rawValue) {
        super(rawValue);
    }
}
