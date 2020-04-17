package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 健康保険等級
 */
@IntegerMinValue(1)
@IntegerMaxValue(99)
public class InsuranceGrade extends IntegerPrimitiveValue<InsuranceGrade> {
    public InsuranceGrade(Integer rawValue) {
        super(rawValue);
    }
}
