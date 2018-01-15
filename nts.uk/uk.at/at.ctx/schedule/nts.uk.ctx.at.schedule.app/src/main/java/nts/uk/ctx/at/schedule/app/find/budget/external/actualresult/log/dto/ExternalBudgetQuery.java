/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

/**
 * The Class ExternalBudgetQuery.
 */
@Setter
@Getter
public class ExternalBudgetQuery {
    
    /** The start date. */
    private GeneralDateTime startDate;
    
    /** The end date. */
    private GeneralDateTime endDate;
    
    /** The list state. */
    private List<Integer> listState;
}
