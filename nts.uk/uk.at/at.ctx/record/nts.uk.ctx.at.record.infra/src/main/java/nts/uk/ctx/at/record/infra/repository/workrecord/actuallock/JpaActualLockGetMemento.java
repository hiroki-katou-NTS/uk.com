/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcdtAtdActualLock;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcdtAtdActualLockPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Class JpaActualLockGetMemento.
 */
public class JpaActualLockGetMemento implements ActualLockGetMemento {
	
	/** The typed value. */
	private KrcdtAtdActualLock typedValue;
	
	/**
	 * Instantiates a new jpa actual lock get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaActualLockGetMemento(KrcdtAtdActualLock typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getKrcdtAtdActualLockPK() == null) {
			this.typedValue.setKrcdtAtdActualLockPK(new KrcdtAtdActualLockPK());
		}
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.typedValue.getKrcdtAtdActualLockPK().getCid();
	}

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	@Override
	public ClosureId getClosureId() {
		return ClosureId.valueOf(this.typedValue.getKrcdtAtdActualLockPK().getClosureId());
	}

	/**
	 * Gets the daily lock state.
	 *
	 * @return the daily lock state
	 */
	@Override
	public LockStatus getDailyLockState() {
		return LockStatus.valueOf(this.typedValue.getDLockState());
	}

	/**
	 * Gets the monthy lock state.
	 *
	 * @return the monthy lock state
	 */
	@Override
	public LockStatus getMonthyLockState() {
		return LockStatus.valueOf(this.typedValue.getMLockState());
	}

}
