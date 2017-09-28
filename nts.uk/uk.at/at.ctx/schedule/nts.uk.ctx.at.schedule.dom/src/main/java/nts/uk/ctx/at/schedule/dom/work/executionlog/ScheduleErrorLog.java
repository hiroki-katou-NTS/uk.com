/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * The Class ScheduleErrorLog.
 */
// スケジュール作成エラーログ
@Getter
public class ScheduleErrorLog extends AggregateRoot{
	
	/** The error content. */
	// エラー内容
	private String errorContent;
	
	/** The execution id. */
	// 実行ID
	private String executionId;
	
	/** The date. */
	// 年月日
	private GeneralDate date;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;

}
