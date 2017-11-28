/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class FixHalfDayWorkTimezone.
 */
@Getter
public class FixHalfDayWorkTimezone extends DomainObject {
	
	/** The rest time zone. */
	//休憩時間帯 
	private FixRestTimezoneSet restTimeZone;
	
	/** The work timezone. */
	//勤務時間帯
	private FixedWorkTimezoneSet workTimezone;
	
	/** The day atr. */
	//午前午後区分
	private AmPmClassification dayAtr;
}
