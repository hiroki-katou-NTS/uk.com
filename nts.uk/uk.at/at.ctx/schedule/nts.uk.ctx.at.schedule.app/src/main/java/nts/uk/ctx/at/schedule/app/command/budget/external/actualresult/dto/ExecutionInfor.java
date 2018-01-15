/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto;

import lombok.Builder;
import nts.arc.task.AsyncTaskInfo;

/**
 * The Class ExecutionInfor.
 */
@Builder
public class ExecutionInfor {
    
    /** The task infor. */
    public AsyncTaskInfo taskInfor;
    
    /** The execute id. */
    public String executeId;
}
