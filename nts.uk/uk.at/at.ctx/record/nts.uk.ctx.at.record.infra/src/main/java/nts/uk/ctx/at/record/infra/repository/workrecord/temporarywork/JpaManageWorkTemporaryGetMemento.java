/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.temporarywork;

import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.MaxUsage;
import nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork.KrcmtTemporaryMng;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Class JpaManageWorkTemporaryGetMemento.
 */
public class JpaManageWorkTemporaryGetMemento implements ManageWorkTemporaryGetMemento{

	/** The krcst manage work temp. */
	private KrcmtTemporaryMng krcmtTemporaryMng;
	
	
	/**
	 * @param krcmtTemporaryMng
	 */
	public JpaManageWorkTemporaryGetMemento(KrcmtTemporaryMng krcmtTemporaryMng) {
		this.krcmtTemporaryMng = krcmtTemporaryMng;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.krcmtTemporaryMng.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getMaxUsage()
	 */
	@Override
	public MaxUsage getMaxUsage() {
		return new MaxUsage(this.krcmtTemporaryMng.getMaxUsage().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryGetMemento#getTimeTreatTemporarySame()
	 */
	@Override
	public AttendanceTime getTimeTreatTemporarySame() {
		return new AttendanceTime(this.krcmtTemporaryMng.getTimeTreatTempSame().intValue());
	}

}

