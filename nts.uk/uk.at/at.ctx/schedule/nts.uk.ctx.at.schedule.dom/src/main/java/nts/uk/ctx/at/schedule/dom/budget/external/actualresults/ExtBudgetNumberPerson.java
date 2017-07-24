package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class ExtBudgetNumberPerson.
 * 外部予算実績人数
 */
@IntegerRange(min = 0, max = 99999)
public class ExtBudgetNumberPerson extends IntegerPrimitiveValue<ExtBudgetNumberPerson> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget number person.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetNumberPerson(Integer rawValue) {
        super(rawValue);
    }

}