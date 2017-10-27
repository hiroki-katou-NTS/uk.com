/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ScheduleCreator.
 */
// スケジュール作成対象者
@Getter
public class ScheduleCreator extends AggregateRoot{
	
	/** The execution id. */
	// 実行ID
	private String executionId;
	
	/** The execution status. */
	// 実行状況
	private ExecutionStatus executionStatus;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;

}
