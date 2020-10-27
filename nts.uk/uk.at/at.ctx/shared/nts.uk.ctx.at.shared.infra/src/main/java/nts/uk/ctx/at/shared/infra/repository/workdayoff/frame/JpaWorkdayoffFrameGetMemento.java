/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workdayoff.frame;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFrame;

/**
 * The Class JpaWorkdayoffFrameGetMemento.
 */
public class JpaWorkdayoffFrameGetMemento implements WorkdayoffFrameGetMemento{
	
	/** The kshst workdayoff frame. */
	private KshmtHdWorkFrame kshmtHdWorkFrame;
	
	/**
	 * Instantiates a new jpa workdayoff frame get memento.
	 *
	 * @param kshmtHdWorkFrame the kshst workdayoff frame
	 */
	public JpaWorkdayoffFrameGetMemento(KshmtHdWorkFrame kshmtHdWorkFrame) {
		this.kshmtHdWorkFrame = kshmtHdWorkFrame;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.kshmtHdWorkFrame.getKshmtHdWorkFramePK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getUseClassification()
	 */
	@Override
	public NotUseAtr getUseClassification() {
		return NotUseAtr.valueOf((int) this.kshmtHdWorkFrame.getUseAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getWorkdayoffFrameNo()
	 */
	@Override
	public WorkdayoffFrameNo getWorkdayoffFrameNo() {
		return new WorkdayoffFrameNo(BigDecimal.valueOf(this.kshmtHdWorkFrame.getKshmtHdWorkFramePK().getWdoFrNo()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getTransferFrameName()
	 */
	@Override
	public WorkdayoffFrameName getTransferFrameName() {
		return new WorkdayoffFrameName(this.kshmtHdWorkFrame.getTransFrName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getWorkdayoffFrameName()
	 */
	@Override
	public WorkdayoffFrameName getWorkdayoffFrameName() {
		return new WorkdayoffFrameName(this.kshmtHdWorkFrame.getWdoFrName());
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getRole()
	 */
	@Override
	public WorkdayoffFrameRole getRole() {
		return WorkdayoffFrameRole.valueOf((int)this.kshmtHdWorkFrame.getRole());
	}
}
