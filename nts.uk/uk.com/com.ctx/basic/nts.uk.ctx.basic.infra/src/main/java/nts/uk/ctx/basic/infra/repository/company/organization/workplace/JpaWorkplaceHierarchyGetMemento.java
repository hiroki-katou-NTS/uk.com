/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

import nts.uk.ctx.basic.dom.company.organization.workplace.HierarchyCode;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchyGetMemento;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHierarchy;

/**
 * The Class JpaWorkplaceHierarchyGetMemento.
 */
public class JpaWorkplaceHierarchyGetMemento implements WorkPlaceHierarchyGetMemento {

	/** The kwpmt wpl hierarchy. */
	private KwpmtWplHierarchy kwpmtWplHierarchy;
	
	
	/**
	 * @param kwpmtWplHierarchy
	 */
	public JpaWorkplaceHierarchyGetMemento(KwpmtWplHierarchy kwpmtWplHierarchy) {
		this.kwpmtWplHierarchy = kwpmtWplHierarchy;
	}

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.kwpmtWplHierarchy.getKwpmtWplHierarchyPK().getWplid());
	}

	/**
	 * Gets the hierarchy code.
	 *
	 * @return the hierarchy code
	 */
	@Override
	public HierarchyCode getHierarchyCode() {
		return new HierarchyCode(this.kwpmtWplHierarchy.getHierarchyCd());
	}

}
