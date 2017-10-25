/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.info;

import nts.uk.ctx.bs.employee.dom.workplace.info.OutsideWorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceDisplayName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceGenericName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceInfo;

/**
 * The Class JpaWorkplaceInfoGetMemento.
 */
public class JpaWorkplaceInfoGetMemento implements WorkplaceInfoGetMemento {

	/** The bsymt workplace info. */
	private BsymtWorkplaceInfo bsymtWorkplaceInfo;

	/**
	 * Instantiates a new jpa workplace info get memento.
	 *
	 * @param entity the item
	 */
	public JpaWorkplaceInfoGetMemento(BsymtWorkplaceInfo entity) {
		this.bsymtWorkplaceInfo = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.bsymtWorkplaceInfo.getBsymtWorkplaceInfoPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.bsymtWorkplaceInfo.getBsymtWorkplaceInfoPK().getHistoryId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWorkplaceId()
	 */
	@Override
	public String getWorkplaceId() {
		return this.bsymtWorkplaceInfo.getBsymtWorkplaceInfoPK().getWkpid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWorkplaceCode()
	 */
	@Override
	public WorkplaceCode getWorkplaceCode() {
		return new WorkplaceCode(this.bsymtWorkplaceInfo.getWkpcd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWorkplaceName()
	 */
	@Override
	public WorkplaceName getWorkplaceName() {
		return new WorkplaceName(this.bsymtWorkplaceInfo.getWkpName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWkpGenericName()
	 */
	@Override
	public WorkplaceGenericName getWkpGenericName() {
		return new WorkplaceGenericName(this.bsymtWorkplaceInfo.getWkpGenericName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWkpDisplayName()
	 */
	@Override
	public WorkplaceDisplayName getWkpDisplayName() {
		return new WorkplaceDisplayName(this.bsymtWorkplaceInfo.getWkpDisplayName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getOutsideWkpCode()
	 */
	@Override
	public OutsideWorkplaceCode getOutsideWkpCode() {
		return new OutsideWorkplaceCode(this.bsymtWorkplaceInfo.getWkpOutsideCode());
	}

}
