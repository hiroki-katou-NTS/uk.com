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
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshstWorkdayoffFrame;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshstWorkdayoffFramePK;

/**
 * The Class JpaWorkdayoffFrameSetMemento.
 */
public class JpaWorkdayoffFrameSetMemento implements WorkdayoffFrameSetMemento{
	
	/** The kshst workdayoff frame. */
	private KshstWorkdayoffFrame kshstWorkdayoffFrame;
	
	/**
	 * Instantiates a new jpa workdayoff frame set memento.
	 *
	 * @param kshstWorkdayoffFrame the kshst workdayoff frame
	 */
	public JpaWorkdayoffFrameSetMemento(KshstWorkdayoffFrame kshstWorkdayoffFrame) {
		if(kshstWorkdayoffFrame.getKshstWorkdayoffFramePK() == null){
			kshstWorkdayoffFrame.setKshstWorkdayoffFramePK(new KshstWorkdayoffFramePK());
		}
		this.kshstWorkdayoffFrame = kshstWorkdayoffFrame;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.kshstWorkdayoffFrame.getKshstWorkdayoffFramePK().setCid(companyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setUseClassification(nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.kshstWorkdayoffFrame.setUseAtr((short)useAtr.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setWorkdayoffFrameNo(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo)
	 */
	@Override
	public void setWorkdayoffFrameNo(WorkdayoffFrameNo workdayoffFrNo) {
		this.kshstWorkdayoffFrame.getKshstWorkdayoffFramePK().setWdoFrNo(workdayoffFrNo.v().shortValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setTransferFrameName(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName)
	 */
	@Override
	public void setTransferFrameName(WorkdayoffFrameName transferFrName) {
		this.kshstWorkdayoffFrame.setTransFrName(transferFrName.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setWorkdayoffFrameName(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName)
	 */
	@Override
	public void setWorkdayoffFrameName(WorkdayoffFrameName workdayoffFrName) {
		this.kshstWorkdayoffFrame.setWdoFrName(workdayoffFrName.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setRole(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole)
	 */
	@Override
	public void setRole(WorkdayoffFrameRole role) {
		this.kshstWorkdayoffFrame.setRole((short)role.value);
	}
}
