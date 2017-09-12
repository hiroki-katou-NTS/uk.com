/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace_old;

import nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHierarchyGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceId;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWkpHierarchy;

/**
 * The Class JpaWorkplaceHierarchyGetMemento.
 */
public class JpaWorkplaceHierarchyGetMemento implements WorkPlaceHierarchyGetMemento {

	/** The cwpmt wkp hierarchy. */
	private CwpmtWkpHierarchy cwpmtWkpHierarchy;
	
	
	/**
	 * Instantiates a new jpa workplace hierarchy get memento.
	 *
	 * @param cwpmtWkpHierarchy the cwpmt wkp hierarchy
	 */
	public JpaWorkplaceHierarchyGetMemento(CwpmtWkpHierarchy cwpmtWkpHierarchy) {
		this.cwpmtWkpHierarchy = cwpmtWkpHierarchy;
	}

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.cwpmtWkpHierarchy.getCwpmtWkpHierarchyPK().getWkpid());
	}

	/**
	 * Gets the hierarchy code.
	 *
	 * @return the hierarchy code
	 */
	@Override
	public String getHierarchyCode() {
		return this.cwpmtWkpHierarchy.getHierarchyCd();
	}

}
