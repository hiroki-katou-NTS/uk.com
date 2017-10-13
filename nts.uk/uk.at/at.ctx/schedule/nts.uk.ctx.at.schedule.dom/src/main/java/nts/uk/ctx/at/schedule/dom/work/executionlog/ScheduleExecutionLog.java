/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ScheduleExecutionLog.
 */
// スケジュール作成実行ログ
@Getter
public class ScheduleExecutionLog extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The completion status. */
	// 完了状態
	private CompletionStatus completionStatus;
	
	/** The execution id. */
	// 実行ID
	private String executionId;
	
	/** The execution content. */
	// 実行内容
	private ExecutionContent executionContent;
	
	/** The execution date time. */
	// 実行日時
	private ExecutionDateTime executionDateTime;
	
	/** The execution employee id. */
	// 実行社員ID
	private String executionEmployeeId;
	
	/** The period. */
	// 対象期間
	private DatePeriod period;
}
