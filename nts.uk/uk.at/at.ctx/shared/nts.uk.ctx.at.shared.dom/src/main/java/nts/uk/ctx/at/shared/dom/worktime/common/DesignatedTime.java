/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;

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
}
