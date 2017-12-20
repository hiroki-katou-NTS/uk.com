/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PredetermineTime.
 */
@Getter
//所定時間
public class PredetermineTime extends DomainObject {

	/** The add time. */
	//就業加算時間
	private BreakDownTimeDay addTime;
	
	/** The pred time. */
	//所定時間
	private BreakDownTimeDay predTime;
	
	/**
	 * Instantiates a new predetermine time.
	 *
	 * @param memento the memento
	 */
	public PredetermineTime(PredetermineTimeGetMemento memento) {
		this.addTime = memento.getAddTime();
		this.predTime = memento.getPredTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PredetermineTimeSetMemento memento){
		memento.setAddTime(this.addTime);
		memento.setPredTime(this.predTime);
	}
	
}
