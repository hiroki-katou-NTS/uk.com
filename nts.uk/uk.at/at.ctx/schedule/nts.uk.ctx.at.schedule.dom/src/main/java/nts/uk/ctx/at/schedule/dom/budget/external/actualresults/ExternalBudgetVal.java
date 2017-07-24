/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ExternalBudgetVal.
 *
 * @param <T> the generic type
 */
@Setter
@Getter
public class ExternalBudgetVal<T> {
    
    /** The object. */
    private T object;
}
