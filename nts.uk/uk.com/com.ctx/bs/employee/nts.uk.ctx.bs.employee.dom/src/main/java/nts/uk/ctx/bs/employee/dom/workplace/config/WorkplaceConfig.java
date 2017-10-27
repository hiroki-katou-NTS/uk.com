/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WorkplaceConfig.
 */
//職場構成
@Getter
public class WorkplaceConfig extends AggregateRoot {

	/** The company id. */
	//会社ID
	private String companyId;

	/** The wkp config history. */
	//履歴
	private List<WorkplaceConfigHistory> wkpConfigHistory;

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceConfigSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWkpConfigHistory(this.wkpConfigHistory);
	}

	/**
	 * Instantiates a new workplace config.
	 *
	 * @param memento the memento
	 */
	public WorkplaceConfig(WorkplaceConfigGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.wkpConfigHistory = memento.getWkpConfigHistory();
		
		// sort start date desc
        Collections.sort(this.wkpConfigHistory, new Comparator<WorkplaceConfigHistory>() {
            @Override
            public int compare(WorkplaceConfigHistory obj1, WorkplaceConfigHistory obj2) {
                return obj2.getPeriod().start().compareTo(obj1.getPeriod().start());
            }
        });
	}
	
	/**
	 * Gets the wkp config history latest.
	 *
	 * @return the wkp config history latest
	 */
	public WorkplaceConfigHistory getWkpConfigHistoryLatest() {
	    int indexLatestHist = 0;
	    return this.wkpConfigHistory.get(indexLatestHist);
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
		if (!(obj instanceof WorkplaceConfig))
			return false;
		WorkplaceConfig other = (WorkplaceConfig) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	
}
