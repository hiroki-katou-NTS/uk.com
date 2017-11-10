/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktimeset.common.FlowWorkRestTimezone;

/**
 * The Class FlowOffdayWorkTimezone.
 */
//流動勤務の休日出勤用勤務時間帯

/**
 * Gets the work timezone.
 *
 * @return the work timezone
 */
@Getter
public class FlowOffdayWorkTimezone extends DomainObject{

	/** The rest time zone. */
	//休憩時間帯
	private FlowWorkRestTimezone restTimeZone;
	
	/** The work timezone. */
	private List<FlowWorkHolidayTimeZone> workTimezone;
}
