package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ExtBudgetActualValue.
 * 外部予算実績受入値
 */
@StringMaxLength(100)
public class ExtBudgetActualValue extends StringPrimitiveValue<ExtBudgetActualValue> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new ext budget error content.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetActualValue(String rawValue) {
        super(rawValue);
    }

}
