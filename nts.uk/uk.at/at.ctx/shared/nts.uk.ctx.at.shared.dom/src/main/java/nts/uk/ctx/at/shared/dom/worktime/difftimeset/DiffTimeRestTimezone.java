/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class DiffTimeRestTimezone.
 */
// 時差勤務の休憩時間帯
@Getter
public class DiffTimeRestTimezone extends WorkTimeDomainObject {
	
	/** The rest timezone. */
	// 休憩時間帯
	private List<DiffTimeDeductTimezone> restTimezones;
	
	/**
	 * Instantiates a new diff time rest timezone.
	 *
	 * @param memento the memento
	 */
	public DiffTimeRestTimezone(DiffTimeRestTimezoneGetMemento memento)
	{
		this.restTimezones = memento.getRestTimezones();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimeRestTimezoneSetMemento memento)
	{
		memento.setRestTimezones(this.restTimezones);
	}

	public void restoreData(DiffTimeRestTimezone restTimezone) {
		this.restTimezones = restTimezone.restTimezones;
	}
}
