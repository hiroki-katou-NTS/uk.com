/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * The Class FixRestTimezoneSet.
 */
// 固定勤務の休憩時間帯
@Getter
public class FixRestTimezoneSet extends DomainObject {

	/** The lst timezone. */
	// 時間帯
	private List<DeductionTime> lstTimezone;

	/**
	 * Instantiates a new fix rest timezone set.
	 *
	 * @param memento
	 *            the memento
	 */
	public FixRestTimezoneSet(FixRestTimezoneSetGetMemento memento) {
		this.lstTimezone = memento.getLstTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FixRestTimezoneSetSetMemento memento) {
		memento.setLstTimezone(this.lstTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();

		// 時間帯が重複してはいけない。
		this.validOverlap();

		// TODO: validate message 516
	}

	/**
	 * Valid overlap.
	 */
	private void validOverlap() {
		// sort asc by start time
		Collections.sort(this.lstTimezone, Comparator.comparing(DeductionTime::getStart));

		Iterator<DeductionTime> iterator = this.lstTimezone.iterator();
		while (iterator.hasNext()) {
			DeductionTime current = iterator.next();

			if (!iterator.hasNext()) {
				break;
			}
			DeductionTime next = iterator.next();
			if (current.getEnd().greaterThanOrEqualTo(next.getStart())) {
				throw new BusinessException("Msg_515");
			}
		}
	}

}
