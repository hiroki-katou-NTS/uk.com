/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

import nts.uk.ctx.basic.dom.company.organization.workplace.HierarchyCode;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchyGetMemento;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.CwpmtWkpHierarchy;

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
	public HierarchyCode getHierarchyCode() {
		return new HierarchyCode(this.cwpmtWkpHierarchy.getHierarchyCd());
	}

}
