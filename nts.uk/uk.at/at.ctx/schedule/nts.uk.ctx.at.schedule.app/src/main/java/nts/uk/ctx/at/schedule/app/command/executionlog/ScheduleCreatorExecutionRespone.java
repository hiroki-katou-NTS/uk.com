/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.Getter;
import lombok.Setter;
import nts.arc.task.AsyncTaskInfo;

/**
 * The Class ExecutionInfor.
 */
@Getter
@Setter
public class ScheduleCreatorExecutionRespone {
    
    /** The task infor. */
    public AsyncTaskInfo taskInfor;
    
    /** The execute id. */
    public String executeId;
}
