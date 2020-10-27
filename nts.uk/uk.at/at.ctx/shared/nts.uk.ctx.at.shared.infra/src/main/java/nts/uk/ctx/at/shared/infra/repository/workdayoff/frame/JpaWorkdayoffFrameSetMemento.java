/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workdayoff.frame;

import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFrame;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFramePK;

/**
 * The Class JpaWorkdayoffFrameSetMemento.
 */
public class JpaWorkdayoffFrameSetMemento implements WorkdayoffFrameSetMemento{
	
	/** The kshst workdayoff frame. */
	private KshmtHdWorkFrame kshmtHdWorkFrame;
	
	/**
	 * Instantiates a new jpa workdayoff frame set memento.
	 *
	 * @param kshmtHdWorkFrame the kshst workdayoff frame
	 */
	public JpaWorkdayoffFrameSetMemento(KshmtHdWorkFrame kshmtHdWorkFrame) {
		if(kshmtHdWorkFrame.getKshmtHdWorkFramePK() == null){
			kshmtHdWorkFrame.setKshmtHdWorkFramePK(new KshmtHdWorkFramePK());
		}
		this.kshmtHdWorkFrame = kshmtHdWorkFrame;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.kshmtHdWorkFrame.getKshmtHdWorkFramePK().setCid(companyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setUseClassification(nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.kshmtHdWorkFrame.setUseAtr((short)useAtr.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setWorkdayoffFrameNo(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo)
	 */
	@Override
	public void setWorkdayoffFrameNo(WorkdayoffFrameNo workdayoffFrNo) {
		this.kshmtHdWorkFrame.getKshmtHdWorkFramePK().setWdoFrNo(workdayoffFrNo.v().shortValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setTransferFrameName(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName)
	 */
	@Override
	public void setTransferFrameName(WorkdayoffFrameName transferFrName) {
		this.kshmtHdWorkFrame.setTransFrName(transferFrName.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setWorkdayoffFrameName(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName)
	 */
	@Override
	public void setWorkdayoffFrameName(WorkdayoffFrameName workdayoffFrName) {
		this.kshmtHdWorkFrame.setWdoFrName(workdayoffFrName.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setRole(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole)
	 */
	@Override
	public void setRole(WorkdayoffFrameRole role) {
		this.kshmtHdWorkFrame.setRole((short)role.value);
	}
}
