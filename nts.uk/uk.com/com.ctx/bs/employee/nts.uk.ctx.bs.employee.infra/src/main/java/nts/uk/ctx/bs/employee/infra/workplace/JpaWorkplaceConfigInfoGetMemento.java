/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceHierarchy;

/**
 * The Class JpaWorkplaceConfigInfoGetMemento.
 */
public class JpaWorkplaceConfigInfoGetMemento implements WorkplaceConfigInfoGetMemento {

	/** The company id. */
	private String companyId;

	/** The history id. */
	private String historyId;

	/** The wkp hierarchy. */
	private List<WorkplaceHierarchy> wkpHierarchy;

	/**
	 * Instantiates a new jpa workplace config info get memento.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 * @param wkpHierarchy the wkp hierarchy
	 */
	public JpaWorkplaceConfigInfoGetMemento(String companyId, String historyId, List<WorkplaceHierarchy> wkpHierarchy) {
		this.companyId = companyId;
		this.historyId = historyId;
		this.wkpHierarchy = wkpHierarchy;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getHistoryId()
	 */
	@Override
	public HistoryId getHistoryId() {
		return new HistoryId(this.historyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getWkpHierarchy()
	 */
	@Override
	public List<WorkplaceHierarchy> getWkpHierarchy() {
		return this.wkpHierarchy;
	}

}
