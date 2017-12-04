/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeDiffDayOffWorkTimezone.
 */
//時差勤務の休日出勤用勤務時間帯

/**
 * Gets the work timezone.
 *
 * @return the work timezone
 */
@Getter
public class TimeDiffDayOffWorkTimezone extends DomainObject{
	
	/** The rest timezone. */
	//休憩時間帯
	private DiffTimeRestTimezone restTimezone;
	
	/** The work timezone. */
	//勤務時間帯
	private List<DayOffTimezoneSetting> workTimezone;
}
