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
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshstWorkdayoffFrame;

/**
 * The Class JpaWorkdayoffFrameGetMemento.
 */
public class JpaWorkdayoffFrameGetMemento implements WorkdayoffFrameGetMemento{
	
	/** The kshst workdayoff frame. */
	private KshstWorkdayoffFrame kshstWorkdayoffFrame;
	
	/**
	 * Instantiates a new jpa workdayoff frame get memento.
	 *
	 * @param kshstWorkdayoffFrame the kshst workdayoff frame
	 */
	public JpaWorkdayoffFrameGetMemento(KshstWorkdayoffFrame kshstWorkdayoffFrame) {
		this.kshstWorkdayoffFrame = kshstWorkdayoffFrame;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.kshstWorkdayoffFrame.getKshstWorkdayoffFramePK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getUseClassification()
	 */
	@Override
	public NotUseAtr getUseClassification() {
		return NotUseAtr.valueOf((int) this.kshstWorkdayoffFrame.getUseAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getWorkdayoffFrameNo()
	 */
	@Override
	public WorkdayoffFrameNo getWorkdayoffFrameNo() {
		return new WorkdayoffFrameNo(BigDecimal.valueOf(this.kshstWorkdayoffFrame.getKshstWorkdayoffFramePK().getWdoFrNo()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getTransferFrameName()
	 */
	@Override
	public WorkdayoffFrameName getTransferFrameName() {
		return new WorkdayoffFrameName(this.kshstWorkdayoffFrame.getTransFrName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getWorkdayoffFrameName()
	 */
	@Override
	public WorkdayoffFrameName getWorkdayoffFrameName() {
		return new WorkdayoffFrameName(this.kshstWorkdayoffFrame.getWdoFrName());
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getRole()
	 */
	@Override
	public WorkdayoffFrameRole getRole() {
		return WorkdayoffFrameRole.valueOf((int)this.kshstWorkdayoffFrame.getRole());
	}
}
