/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ExtBudgetFileName.
 * 外部予算実績受入ファイル名
 */
@StringMaxLength(200)
public class ExtBudgetFileName extends StringPrimitiveValue<ExtBudgetFileName> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new ext budget error content.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetFileName(String rawValue) {
        super(rawValue);
    }

}
