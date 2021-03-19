/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ExtBudgetErrorContent.
 * 外部予算実績エラー内容
 */
@StringMaxLength(300)
public class ExtBudgetErrorContent extends StringPrimitiveValue<ExtBudgetErrorContent> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new ext budget error content.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetErrorContent(String rawValue) {
        super(rawValue);
    }

}
