/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.joggingworkset;

import java.util.List;

import lombok.Getter;

//時差勤務の休日出勤用勤務時間帯
@Getter
public class TimeDiffDayOffWorkTimezone {
	//休憩時間帯
	private TimeDiffRestTimezone restTimezone;
	
	//勤務時間帯
	private List<DayOffTimezoneSetting> workTimezone;
}
