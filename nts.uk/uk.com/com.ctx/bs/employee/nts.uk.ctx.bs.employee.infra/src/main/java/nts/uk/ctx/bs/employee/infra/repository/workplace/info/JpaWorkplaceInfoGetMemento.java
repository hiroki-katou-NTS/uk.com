/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.info;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.info.OutsideWorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceDisplayName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceGenericName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceInfo;

public class JpaWorkplaceInfoGetMemento implements WorkplaceInfoGetMemento {

	/** The bsymt workplace info. */
	private BsymtWorkplaceInfo bsymtWorkplaceInfo;

	/**
	 * Instantiates a new jpa workplace info get memento.
	 *
	 * @param item
	 *            the item
	 */
	public JpaWorkplaceInfoGetMemento(BsymtWorkplaceInfo item) {
		this.bsymtWorkplaceInfo = item;
	}

	@Override
	public String getCompanyId() {
		return this.bsymtWorkplaceInfo.getBsymtWorkplaceInfoPK().getCid();
	}

	@Override
	public HistoryId getHistoryId() {
		return new HistoryId(this.bsymtWorkplaceInfo.getBsymtWorkplaceInfoPK().getHistoryId());
	}

	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.bsymtWorkplaceInfo.getBsymtWorkplaceInfoPK().getWkpid());
	}

	@Override
	public WorkplaceCode getWorkplaceCode() {
		return new WorkplaceCode(this.bsymtWorkplaceInfo.getWkpcd());
	}

	@Override
	public WorkplaceName getWorkplaceName() {
		return new WorkplaceName(this.bsymtWorkplaceInfo.getWkpName());
	}

	@Override
	public WorkplaceGenericName getWkpGenericName() {
		return new WorkplaceGenericName(this.bsymtWorkplaceInfo.getWkpGenericName());
	}

	@Override
	public WorkplaceDisplayName getWkpDisplayName() {
		return new WorkplaceDisplayName(this.bsymtWorkplaceInfo.getWkpDisplayName());
	}

	@Override
	public OutsideWorkplaceCode getOutsideWkpCode() {
		return new OutsideWorkplaceCode(this.bsymtWorkplaceInfo.getWkpOutsideCode());
	}

}
