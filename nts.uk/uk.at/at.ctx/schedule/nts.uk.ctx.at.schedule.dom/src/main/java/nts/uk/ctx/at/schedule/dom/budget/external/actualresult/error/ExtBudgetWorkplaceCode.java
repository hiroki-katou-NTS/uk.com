package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ExtBudgetWorkplaceCode.
 * 外部予算実績受入値職場コード
 */
@StringMaxLength(100)
public class ExtBudgetWorkplaceCode extends StringPrimitiveValue<ExtBudgetWorkplaceCode> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new ext budget error content.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetWorkplaceCode(String rawValue) {
        super(rawValue);
    }

}
