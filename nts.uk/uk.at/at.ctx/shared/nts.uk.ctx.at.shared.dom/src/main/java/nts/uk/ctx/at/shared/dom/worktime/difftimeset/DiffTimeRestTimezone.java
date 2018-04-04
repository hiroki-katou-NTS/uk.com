/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class DiffTimeRestTimezone.
 */
// 時差勤務の休憩時間帯

/**
 * Gets the rest timezones.
 *
 * @return the rest timezones
 */
@Getter
public class DiffTimeRestTimezone extends WorkTimeDomainObject {

	/** The rest timezones. */
	// 休憩時間帯
	private List<DiffTimeDeductTimezone> restTimezones;

	/**
	 * Instantiates a new diff time rest timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeRestTimezone(DiffTimeRestTimezoneGetMemento memento) {
		this.restTimezones = memento.getRestTimezones();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DiffTimeRestTimezoneSetMemento memento) {
		memento.setRestTimezones(this.restTimezones);
	}

	/**
	 * Restore data.
	 *
	 * @param restTimezone
	 *            the rest timezone
	 */
	public void correctData(DiffTimeRestTimezone restTimezone) {
		this.restTimezones = restTimezone.restTimezones;
	}

	/**
	 * Restore default data.
	 */
	public void correctDefaultData() {
		this.restTimezones = new ArrayList<>();
	}

	/**
	 * Valid overlap.
	 *
	 * @param param
	 *            the param
	 */
	public void validOverlap(String param) {
		// sort asc by start time
		Collections.sort(this.restTimezones, Comparator.comparing(DeductionTime::getStart));

		Iterator<DiffTimeDeductTimezone> iterator = this.restTimezones.iterator();
		while (iterator.hasNext()) {
			DeductionTime current = iterator.next();

			if (!iterator.hasNext()) {
				break;
			}
			DeductionTime next = iterator.next();
			if (current.getEnd().greaterThan(next.getStart())) {
				this.bundledBusinessExceptions.addMessage("Msg_515", param);
			}
		}
	}
}
