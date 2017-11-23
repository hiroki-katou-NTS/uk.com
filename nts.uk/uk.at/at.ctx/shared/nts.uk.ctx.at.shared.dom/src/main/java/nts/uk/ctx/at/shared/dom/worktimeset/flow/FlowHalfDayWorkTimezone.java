/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktimeset.common.FlowWorkRestTimezone;

/**
 * The Class FlowHalfDayWorkTimezone.
 */
//流動勤務の平日出勤用勤務時間帯
@Getter
public class FlowHalfDayWorkTimezone extends DomainObject{

	/** The rest timezone. */
	//休憩時間帯
	private FlowWorkRestTimezone restTimezone;
	
	/** The work time zone. */
	//勤務時間帯
	private FlowWorkTimezoneSetting workTimeZone;
}
