/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.temporarywork;

import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.MaxUsage;
import nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork.KrcstManageWorkTemp;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Class JpaManageWorkTemporaryGetMemento.
 */
public class JpaManageWorkTemporaryGetMemento implements ManageWorkTemporaryGetMemento{

	/** The krcst manage work temp. */
	private KrcstManageWorkTemp krcstManageWorkTemp;
	
	
	/**
	 * @param krcstManageWorkTemp
	 */
	public JpaManageWorkTemporaryGetMemento(KrcstManageWorkTemp krcstManageWorkTemp) {
		this.krcstManageWorkTemp = krcstManageWorkTemp;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.krcstManageWorkTemp.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getMaxUsage()
	 */
	@Override
	public MaxUsage getMaxUsage() {
		return new MaxUsage(this.krcstManageWorkTemp.getMaxUsage().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getTimeTreatTemporarySame()
	 */
	@Override
	public AttendanceTime getTimeTreatTemporarySame() {
		return new AttendanceTime(this.krcstManageWorkTemp.getTimeTreatTempSame().intValue());
	}

}

