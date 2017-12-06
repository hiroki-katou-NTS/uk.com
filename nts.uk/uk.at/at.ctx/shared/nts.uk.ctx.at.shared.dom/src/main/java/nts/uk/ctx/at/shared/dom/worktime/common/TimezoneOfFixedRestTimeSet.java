/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimezoneOfFixedRestTimeSet.
 */
// 固定休憩時間の時間帯設定
@Getter
public class TimezoneOfFixedRestTimeSet extends DomainObject{

	/** The timezone. */
	//時間帯
	private List<DeductionTime> timezones;

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		this.checkOverlap();
	}

	/**
	 * Check overlap.
	 */
	private void checkOverlap() {
		val FREQUENCY = 1;
		// check timezone overlap
		if (this.timezones.stream().anyMatch(item -> Collections.frequency(this.timezones, item) > FREQUENCY)) {
			throw new BusinessException("Msg_515");
		}
	}

	/**
	 * Instantiates a new timezone of fixed rest time set.
	 *
	 * @param memento the memento
	 */
	public TimezoneOfFixedRestTimeSet(TimezoneOfFixedRestTimeSetGetMemento memento) {
		this.timezones = memento.getTimezones();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TimezoneOfFixedRestTimeSetSetMemento memento) {
		memento.setTimezones(this.timezones);
	}
	
}
