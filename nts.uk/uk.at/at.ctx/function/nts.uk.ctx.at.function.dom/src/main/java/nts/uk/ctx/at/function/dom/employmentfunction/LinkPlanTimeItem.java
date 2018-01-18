/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.employmentfunction;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class LinkPlanTimeItem.
 */
@Getter
@Setter
//予定項目と勤怠項目の紐付け
public class LinkPlanTimeItem extends DomainObject{
	
	/** The company id. */
	private String companyId;
	
	/** The schedule id. */
	private String scheduleId;
	
	/** The attendance id. */
	private Integer atdId;
	
	/**
	 * Instantiates a new link plan time item.
	 *
	 * @param memento the memento
	 */
	public LinkPlanTimeItem(LinkPlanTimeItemGetMemento memento) {
		this.scheduleId = memento.getScheduleID();
		this.atdId = memento.getAtdID();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(LinkPlanTimeItemSetMemento memento) {
		memento.setScheduleID(this.scheduleId);
		memento.setAtdID(this.atdId);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atdId == null) ? 0 : atdId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((scheduleId == null) ? 0 : scheduleId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkPlanTimeItem other = (LinkPlanTimeItem) obj;
		if (atdId == null) {
			if (other.atdId != null)
				return false;
		} else if (!atdId.equals(other.atdId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (scheduleId == null) {
			if (other.scheduleId != null)
				return false;
		} else if (!scheduleId.equals(other.scheduleId))
			return false;
		return true;
	}
	

}
