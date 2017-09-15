/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Workplace.
 */

@Getter
//職場
public class Workplace extends AggregateRoot{

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The workplace id. */
	//職場ID
	private WorkplaceId workplaceId;
	
	/** The workplace history. */
	//履歴
	private List<WorkplaceHistory> workplaceHistory;

	/**
	 * Instantiates a new workplace.
	 *
	 * @param memento the memento
	 */
	public Workplace(WorkplaceGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.workplaceHistory = memento.getWorkplaceHistory();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setWorkplaceHistory(this.workplaceHistory);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		if (!(obj instanceof Workplace))
			return false;
		Workplace other = (Workplace) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
	
}
