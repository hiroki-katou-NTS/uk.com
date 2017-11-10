/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class TimeDiffHalfDayWorkTimezone.
 */
//時差勤務の平日出勤用勤務時間帯
@Getter
public class TimeDiffHalfDayWorkTimezone extends DomainObject{

	/** The rest timezone. */
	//休憩時間帯
	private DiffTimeRestTimezone restTimezone;
	
	/** The work timezone. */
	//勤務時間帯
	private DiffTimezoneSet workTimezone;
	
	/** The Am pm cls. */
	//午前午後区分
	private AmPmClassification AmPmCls;
}
