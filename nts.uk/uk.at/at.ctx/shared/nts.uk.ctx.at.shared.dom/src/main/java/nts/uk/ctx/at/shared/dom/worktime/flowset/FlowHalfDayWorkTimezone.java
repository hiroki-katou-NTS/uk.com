/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Class FlowHalfDayWorkTimezone.
 */
//流動勤務の平日出勤用勤務時間帯
@Getter
public class FlowHalfDayWorkTimezone extends DomainObject{

	/** The work time zone. */
	//勤務時間帯
	private FlowWorkTimezoneSetting workTimeZone;
	
	/** The rest timezone. */
	//休憩時間帯
	private FlowWorkRestTimezone restTimezone;
	
	/**
	 * Instantiates a new flow half day work timezone.
	 *
	 * @param memento the memento
	 */
	public FlowHalfDayWorkTimezone(FlHalfDayWtzGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimeZone = memento.getWorkTimeZone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlHalfDayWtzSetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimeZone(this.workTimeZone);
	}
}
