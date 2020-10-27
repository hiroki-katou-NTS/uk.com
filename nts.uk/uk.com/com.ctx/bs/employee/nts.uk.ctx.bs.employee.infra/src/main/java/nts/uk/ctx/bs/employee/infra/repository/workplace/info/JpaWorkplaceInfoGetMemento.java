/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.info;

import nts.uk.ctx.bs.employee.dom.workplace.info.OutsideWorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WkpCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceDisplayName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceGenericName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName;
import nts.uk.ctx.bs.employee.infra.entity.workplace.master.BsymtWkpInfor;

/**
 * The Class JpaWorkplaceInfoGetMemento.
 */
public class JpaWorkplaceInfoGetMemento implements WorkplaceInfoGetMemento {

	/** The bsymt workplace info. */
	private BsymtWkpInfor bsymtWkpInfo;

	/**
	 * Instantiates a new jpa workplace info get memento.
	 *
	 * @param entity the item
	 */
	public JpaWorkplaceInfoGetMemento(BsymtWkpInfor entity) {
		this.bsymtWkpInfo = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.bsymtWkpInfo.getPk().getCompanyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.bsymtWkpInfo.getPk().getWorkplaceHistoryId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWorkplaceId()
	 */
	@Override
	public String getWorkplaceId() {
		return this.bsymtWkpInfo.getPk().getWorkplaceId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWorkplaceCode()
	 */
	@Override
	public WkpCode getWorkplaceCode() {
		return new WkpCode(this.bsymtWkpInfo.getWorkplaceCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWorkplaceName()
	 */
	@Override
	public WorkplaceName getWorkplaceName() {
		return new WorkplaceName(this.bsymtWkpInfo.getWorkplaceName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWkpGenericName()
	 */
	@Override
	public WorkplaceGenericName getWkpGenericName() {
		return new WorkplaceGenericName(this.bsymtWkpInfo.getWorkplaceGeneric());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getWkpDisplayName()
	 */
	@Override
	public WorkplaceDisplayName getWkpDisplayName() {
		return new WorkplaceDisplayName(this.bsymtWkpInfo.getWorkplaceDisplayName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#getOutsideWkpCode()
	 */
	@Override
	public OutsideWorkplaceCode getOutsideWkpCode() {
		return new OutsideWorkplaceCode(this.bsymtWkpInfo.getWorkplaceExternalCode());
	}

}
