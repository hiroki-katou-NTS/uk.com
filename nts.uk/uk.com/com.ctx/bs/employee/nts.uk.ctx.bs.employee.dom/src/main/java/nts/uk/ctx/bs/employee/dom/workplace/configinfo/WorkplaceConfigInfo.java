/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.configinfo;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;

/**
 * The Class WorkPlaceConfigInfo.
 */
// 職場構成情報
@Getter
public class WorkplaceConfigInfo extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The history id. */
	// 履歴ID
	private HistoryId historyId;

	/** The wkp hierarchy. */
	// 階層
	private List<WorkplaceHierarchy> wkpHierarchy;

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkplaceConfigInfoSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setHistoryId(this.getHistoryId());
		memento.setWkpHierarchy(this.wkpHierarchy);
	}

	/**
	 * Instantiates a new work place config info.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkplaceConfigInfo(WorkplaceConfigInfoGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.historyId = memento.getHistoryId();
		this.wkpHierarchy = memento.getWkpHierarchy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof WorkplaceConfigInfo))
			return false;
		WorkplaceConfigInfo other = (WorkplaceConfigInfo) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		return true;
	}

}
