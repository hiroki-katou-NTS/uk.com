/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchyGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo;

public class JpaWorkplaceHierarchyGetMemento implements WorkplaceHierarchyGetMemento {

	/** The entity. */
	private BsymtWkpConfigInfo entity;

	public JpaWorkplaceHierarchyGetMemento(BsymtWkpConfigInfo item) {
		this.entity = item;
	}

	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.entity.getBsymtWkpConfigInfoPK().getWkpid());
	}

	@Override
	public HierarchyCode getHierarchyCode() {
		return new HierarchyCode(this.entity.getHierarchyCd());
	}

}
