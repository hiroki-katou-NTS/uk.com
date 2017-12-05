/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FixedWorkTimezoneSet.
 */
// 固定勤務時間帯設定
@Getter
public class FixedWorkTimezoneSet extends DomainObject {

	/** The lst working timezone. */
	// 就業時間帯
	private List<EmTimeZoneSet> lstWorkingTimezone;

	/** The lst OT timezone. */
	// 残業時間帯
	private List<OverTimeOfTimeZoneSet> lstOTTimezone;
	
	
	/**
	 * Instantiates a new fixed work timezone set.
	 *
	 * @param memento the memento
	 */
	public FixedWorkTimezoneSet(FixedWorkTimezoneSetGetMemento memento) {
		this.lstWorkingTimezone = memento.getLstWorkingTimezone();
		this.lstOTTimezone = memento.getLstOTTimezone();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FixedWorkTimezoneSetSetMemento memento){
		memento.setLstWorkingTimezone(this.lstWorkingTimezone);
		memento.setLstOTTimezone(this.lstOTTimezone);
	}
}
