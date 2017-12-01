/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneStampSet.
 */
//就業時間帯の打刻設定

/**
 * Gets the priority set.
 *
 * @return the priority set
 */
@Getter
public class WorkTimezoneStampSet extends DomainObject{
	
	/** The rounding set. */
	//丸め設定
	private List<RoundingSet> roundingSet;
	
	/** The priority set. */
	//優先設定
	private List<PrioritySetting> prioritySet;

	/**
	 * Instantiates a new work timezone stamp set.
	 *
	 * @param memento the memento
	 */
	public WorkTimezoneStampSet(WorkTimezoneStampSetGetMemento memento) {
		this.roundingSet = memento.getRoundingSet();
		this.prioritySet = memento.getPrioritySet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimezoneStampSetSetMemento memento) {
		memento.setRoundingSet(this.roundingSet);
		memento.setPrioritySet(this.prioritySet);
	}
}
