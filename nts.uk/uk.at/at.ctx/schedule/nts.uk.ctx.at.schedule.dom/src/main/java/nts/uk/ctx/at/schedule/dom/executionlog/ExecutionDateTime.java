/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDateTime;

/**
 * The Class ExecutionDateTime.
 */
// 実行日時
@Getter
public class ExecutionDateTime extends DomainObject{

	/** The execution start date. */
	// 開始日時
	private GeneralDateTime executionStartDate;
	
	/** The execution end date. */
	// 終了日時
	private GeneralDateTime executionEndDate;

	/**
	 * Instantiates a new execution date time.
	 *
	 * @param executionStartDate the execution start date
	 * @param executionEndDate the execution end date
	 */
	public ExecutionDateTime(GeneralDateTime executionStartDate, GeneralDateTime executionEndDate) {
		this.executionStartDate = executionStartDate;
		this.executionEndDate = executionEndDate;
	}
	
	
}
