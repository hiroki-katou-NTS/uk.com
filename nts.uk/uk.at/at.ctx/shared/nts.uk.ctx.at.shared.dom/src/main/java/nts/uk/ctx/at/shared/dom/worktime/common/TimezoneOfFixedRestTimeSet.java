/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
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
//		this.checkOverlap();
	}

	/**
	 * Constructor
	 * @param timezones the timezone
	 */
	public TimezoneOfFixedRestTimeSet(List<DeductionTime> timezones) {
		super();
		this.timezones = timezones;
	}
	
	/**
	 * Check overlap.
	 */
	public void checkOverlap(String param) {
		Collections.sort(this.timezones, Comparator.comparing(DeductionTime::getStart));
		
		for (int i = 0; i < this.timezones.size(); i++) {
			DeductionTime deduct1 = this.timezones.get(i);
			for (int j = i + 1; j < this.timezones.size(); j++) {
				DeductionTime deduct2 = this.timezones.get(j);
				// check overlap
				if (deduct1.isOverlap(deduct2)) {
					throw new BusinessException("Msg_515",param);
				}
			}
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
