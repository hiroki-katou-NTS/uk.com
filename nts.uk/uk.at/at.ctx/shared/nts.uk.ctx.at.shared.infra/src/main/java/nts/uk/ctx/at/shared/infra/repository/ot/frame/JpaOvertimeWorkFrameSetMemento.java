/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.frame;

import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshmtOverFrame;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshmtOverFramePK;

/**
 * The Class JpaOvertimeWorkFrameSetMemento.
 */
public class JpaOvertimeWorkFrameSetMemento implements OvertimeWorkFrameSetMemento{
	
	/** The kshst overtime frame. */
	private KshmtOverFrame kshmtOverFrame;
	
	/**
	 * Instantiates a new jpa overtime work frame set memento.
	 *
	 * @param kshmtOverFrame the kshst overtime frame
	 */
	public JpaOvertimeWorkFrameSetMemento(KshmtOverFrame kshmtOverFrame) {
		if(kshmtOverFrame.getKshmtOverFramePK() == null){
			kshmtOverFrame.setKshmtOverFramePK(new KshmtOverFramePK());
		}
		this.kshmtOverFrame = kshmtOverFrame;
	}

	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.kshmtOverFrame.getKshmtOverFramePK().setCid(companyId);
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setUseClassification(nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.kshmtOverFrame.setUseAtr((short)useAtr.value);
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setOvertimeWorkFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo)
	 */
	@Override
	public void setOvertimeWorkFrameNo(OvertimeWorkFrameNo overtimeWorkFrNo) {
		this.kshmtOverFrame.getKshmtOverFramePK().setOtFrNo(overtimeWorkFrNo.v().shortValue());
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setTransferFrameName(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName)
	 */
	@Override
	public void setTransferFrameName(OvertimeWorkFrameName transferFrName) {
		this.kshmtOverFrame.setTransFrName(transferFrName.v());
	}


	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setOvertimeWorkFrameName(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName)
	 */
	@Override
	public void setOvertimeWorkFrameName(OvertimeWorkFrameName overtimeWorkFrName) {
		this.kshmtOverFrame.setOtFrName(overtimeWorkFrName.v());
	}

}
