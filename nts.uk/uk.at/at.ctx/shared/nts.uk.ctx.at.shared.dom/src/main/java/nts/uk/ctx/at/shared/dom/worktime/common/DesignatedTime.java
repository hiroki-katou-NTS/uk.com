/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DesignatedTime.
 */
//指定時間
@Getter
public class DesignatedTime extends DomainObject {
	
	/** The one day time. */
	//1日の時間
	private OneDayTime oneDayTime;
	
	/** The half day time. */
	//半日の時間
	private OneDayTime halfDayTime;

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.oneDayTime.lessThanOrEqualTo(this.halfDayTime)) {
			throw new BusinessException("Msg_782");
		}
	}

	/**
	 * Instantiates a new designated time.
	 *
	 * @param memento the memento
	 */
	public DesignatedTime(DesignatedTimeGetMemento memento) {
		this.oneDayTime = memento.getOneDayTime();
		this.halfDayTime = memento.getHalfDayTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DesignatedTimeSetMemento memento) {
		memento.setOneDayTime(this.oneDayTime);
		memento.setHalfDayTime(this.halfDayTime);
	}
	
	/**
	 * Restore data.
	 *
	 * @param otherDomain the other domain
	 */
	public void restoreData(DesignatedTime other) {
		this.oneDayTime = other.getOneDayTime();
		this.halfDayTime = other.getHalfDayTime();
	}
}
