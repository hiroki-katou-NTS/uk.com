/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config.info;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigInfo;

/**
 * The Class JpaWorkplaceConfigInfoGetMemento.
 */
public class JpaWorkplaceConfigInfoGetMemento implements WorkplaceConfigInfoGetMemento {

	/** The lst entity. */
	private List<BsymtWkpConfigInfo> lstEntity;

	/**
	 * Instantiates a new jpa workplace config info get memento.
	 *
	 * @param lstEntity the lst entity
	 */
	public JpaWorkplaceConfigInfoGetMemento(List<BsymtWkpConfigInfo> lstEntity) {
		this.lstEntity = lstEntity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.lstEntity.stream()
		        .map(entity -> entity.getBsymtWkpConfigInfoPK().getCid())
		        .findFirst()
		        .get();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
	    return this.lstEntity.stream()
                .map(entity -> entity.getBsymtWkpConfigInfoPK().getHistoryId())
                .findFirst()
                .get();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getWkpHierarchy()
	 */
	@Override
	public List<WorkplaceHierarchy> getWkpHierarchy() {
	    return this.lstEntity.stream()
                .map(entity -> new WorkplaceHierarchy(new JpaWorkplaceHierarchyGetMemento(entity)))
                .collect(Collectors.toList());
	}

}
