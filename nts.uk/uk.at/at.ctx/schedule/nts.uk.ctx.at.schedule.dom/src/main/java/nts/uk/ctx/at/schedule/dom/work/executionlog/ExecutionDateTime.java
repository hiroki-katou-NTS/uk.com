/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.work.executionlog;

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
}
