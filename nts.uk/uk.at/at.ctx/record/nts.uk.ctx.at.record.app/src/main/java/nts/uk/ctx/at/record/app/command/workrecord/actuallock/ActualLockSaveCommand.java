/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.actuallock;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;

/**
 * Instantiates a new actual lock save command.
 */
@Data
public class ActualLockSaveCommand implements ActualLockGetMemento {
	
	/** The closure id. */
	private int closureId;
	
	/** The daily lock state. */
	private int dailyLockState;
	
	/** The monthly lock state. */
	private int monthlyLockState;
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockGetMemento#getClosureId()
	 */
	@Override
	public ClosureId getClosureId() {
		return ClosureId.valueOf(this.closureId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockGetMemento#getDailyLockState()
	 */
	@Override
	public LockStatus getDailyLockState() {
		return LockStatus.valueOf(this.dailyLockState);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockGetMemento#getMonthyLockState()
	 */
	@Override
	public LockStatus getMonthyLockState() {
		return LockStatus.valueOf(this.monthlyLockState);
	}

}
