package nts.uk.ctx.at.schedule.dom.budget.premium;


import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * Primitive: 単価
 */
@IntegerMinValue(0)
@IntegerMaxValue(9999999)
public class WorkingHoursUnitPrice extends IntegerPrimitiveValue<WorkingHoursUnitPrice> {

    private static final long serialVersionUID = 1L;
    public WorkingHoursUnitPrice(Integer rawValue) {
        super(rawValue);
    }
}
