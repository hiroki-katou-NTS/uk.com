/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.frame;

import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento;
import nts.uk.ctx.at.shared.dom.ot.frame.RoleOvertimeWork;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFrame;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFramePK;

/**
 * The Class JpaOvertimeWorkFrameSetMemento.
 */
public class JpaOvertimeWorkFrameSetMemento implements OvertimeWorkFrameSetMemento{
	
	/** The kshst overtime frame. */
	private KshstOvertimeFrame kshstOvertimeFrame;
	
	/**
	 * Instantiates a new jpa overtime work frame set memento.
	 *
	 * @param kshstOvertimeFrame the kshst overtime frame
	 */
	public JpaOvertimeWorkFrameSetMemento(KshstOvertimeFrame kshstOvertimeFrame) {
		if(kshstOvertimeFrame.getKshstOvertimeFramePK() == null){
			kshstOvertimeFrame.setKshstOvertimeFramePK(new KshstOvertimeFramePK());
		}
		this.kshstOvertimeFrame = kshstOvertimeFrame;
	}

	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.kshstOvertimeFrame.getKshstOvertimeFramePK().setCid(companyId);
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setUseClassification(nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.kshstOvertimeFrame.setUseAtr((short)useAtr.value);
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setOvertimeWorkFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo)
	 */
	@Override
	public void setOvertimeWorkFrameNo(OvertimeWorkFrameNo overtimeWorkFrNo) {
		this.kshstOvertimeFrame.getKshstOvertimeFramePK().setOtFrNo(overtimeWorkFrNo.v().shortValue());
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setTransferFrameName(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName)
	 */
	@Override
	public void setTransferFrameName(OvertimeWorkFrameName transferFrName) {
		this.kshstOvertimeFrame.setTransFrName(transferFrName.v());
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setOvertimeWorkFrameName(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName)
	 */
	@Override
	public void setOvertimeWorkFrameName(OvertimeWorkFrameName overtimeWorkFrName) {
		this.kshstOvertimeFrame.setOtFrName(overtimeWorkFrName.v());
	}

	@Override
	public void setRole(RoleOvertimeWork role) {
		this.kshstOvertimeFrame.setRole((short)role.value);
	}
	
	@Override
	public void setTransferAtr(NotUseAtr transferAtr) {
		this.kshstOvertimeFrame.setTransferAtr((short)transferAtr.value);
	}
}
