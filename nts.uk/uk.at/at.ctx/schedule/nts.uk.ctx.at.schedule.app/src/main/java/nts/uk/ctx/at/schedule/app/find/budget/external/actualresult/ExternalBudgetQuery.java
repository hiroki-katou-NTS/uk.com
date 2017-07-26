/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ExternalBudgetQuery.
 */
@Setter
@Getter
public class ExternalBudgetQuery {
    
    /** The start date. */
    private GeneralDate startDate;
    
    /** The list state. */
    private List<Integer> listState;
}
