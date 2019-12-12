package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(0)
@IntegerMaxValue(99999999)
public class MonthlyRemuneration extends IntegerPrimitiveValue<MonthlyRemuneration> {
    public MonthlyRemuneration(Integer rawValue) {
        super(rawValue);
    }
}
