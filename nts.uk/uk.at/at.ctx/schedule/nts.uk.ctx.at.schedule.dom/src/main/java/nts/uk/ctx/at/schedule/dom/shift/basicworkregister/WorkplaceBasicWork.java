/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
 * The Class WorkplaceBasicWork. 職場基本勤務設定
 */
@Getter
public class WorkplaceBasicWork extends AggregateRoot {

	/** The work place id. */
	private WorkplaceId workplaceId;
	
	/** The basic work setting. */
	private List<BasicWorkSetting> basicWorkSetting;
	
	/**
	 * Instantiates a new work place basic work.
	 *
	 * @param memento the memento
	 */
	public WorkplaceBasicWork(WorkplaceBasicWorkGetMemento memento) {
		this.workplaceId = memento.getWorkPlaceId();
		this.basicWorkSetting = memento.getBasicWorkSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceBasicWorkSetMemento memento) {
		memento.setWorkPlaceId(this.workplaceId);
		memento.setBasicWorkSetting(this.basicWorkSetting);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basicWorkSetting == null) ? 0 : basicWorkSetting.hashCode());
		result = prime * result + ((workplaceId == null) ? 0 : workplaceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkplaceBasicWork other = (WorkplaceBasicWork) obj;
		if (basicWorkSetting == null) {
			if (other.basicWorkSetting != null)
				return false;
		} else if (!basicWorkSetting.equals(other.basicWorkSetting))
			return false;
		if (workplaceId == null) {
			if (other.workplaceId != null)
				return false;
		} else if (!workplaceId.equals(other.workplaceId))
			return false;
		return true;
	}
	
}
