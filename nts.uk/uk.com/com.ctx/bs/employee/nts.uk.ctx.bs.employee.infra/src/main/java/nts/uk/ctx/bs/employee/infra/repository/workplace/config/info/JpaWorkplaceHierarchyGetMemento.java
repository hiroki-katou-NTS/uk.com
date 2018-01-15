/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchyGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo;

/**
 * The Class JpaWorkplaceHierarchyGetMemento.
 */
public class JpaWorkplaceHierarchyGetMemento implements WorkplaceHierarchyGetMemento {

	/** The entity. */
	private BsymtWkpConfigInfo entity;

	/**
	 * Instantiates a new jpa workplace hierarchy get memento.
	 *
	 * @param item the item
	 */
	public JpaWorkplaceHierarchyGetMemento(BsymtWkpConfigInfo item) {
		this.entity = item;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchyGetMemento#getWorkplaceId()
	 */
	@Override
	public String getWorkplaceId() {
		return this.entity.getBsymtWkpConfigInfoPK().getWkpid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchyGetMemento#getHierarchyCode()
	 */
	@Override
	public HierarchyCode getHierarchyCode() {
		return new HierarchyCode(this.entity.getHierarchyCd());
	}

}
