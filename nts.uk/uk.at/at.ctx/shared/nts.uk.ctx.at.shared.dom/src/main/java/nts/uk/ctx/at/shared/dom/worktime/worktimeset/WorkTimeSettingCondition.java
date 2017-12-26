/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import lombok.Value;

/**
 * The Class WorkTimeCondition.
 */
@Value
public class WorkTimeSettingCondition {

	/** The work time daily atr. */
	private Integer workTimeDailyAtr;

	/** The work time method set. */
	private Integer workTimeMethodSet;

	/** The is abolish. */
	private Boolean isAbolish;

}
