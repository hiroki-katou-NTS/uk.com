/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

import nts.uk.ctx.basic.dom.company.organization.workplace.HierarchyCode;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchyGetMemento;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHierarchy;

public class JpaWorkplaceHierarchyGetMemento implements WorkPlaceHierarchyGetMemento {

	/** The kwpmt wpl hierarchy. */
	private KwpmtWplHierarchy kwpmtWplHierarchy;
	
	
	/**
	 * @param kwpmtWplHierarchy
	 */
	public JpaWorkplaceHierarchyGetMemento(KwpmtWplHierarchy kwpmtWplHierarchy) {
		this.kwpmtWplHierarchy = kwpmtWplHierarchy;
	}

	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.kwpmtWplHierarchy.getKwpmtWplHierarchyPK().getWplid());
	}

	@Override
	public HierarchyCode getHierarchyCode() {
		return new HierarchyCode(this.kwpmtWplHierarchy.getWplcd());
	}

}
