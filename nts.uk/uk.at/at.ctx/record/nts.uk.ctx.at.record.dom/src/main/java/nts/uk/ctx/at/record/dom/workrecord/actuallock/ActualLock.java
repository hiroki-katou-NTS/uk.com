/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Class ActualLock.
 */
// 当月の実績ロック
@Getter
public class ActualLock extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The closure id. */
	// 締めID
	private ClosureId closureId;
	
	/** The daily lock state. */
	// 日別のロック状態
	private LockStatus dailyLockState;
	
	/** The monthly lock state. */
	// 月別のロック状態
	private LockStatus monthlyLockState;
	
	/**
	 * Instantiates a new actual lock.
	 *
	 * @param memento the memento
	 */
	public ActualLock (ActualLockGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.closureId = memento.getClosureId();
		this.dailyLockState = memento.getDailyLockState();
		this.monthlyLockState = memento.getMonthyLockState();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (ActualLockSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setClosureId(this.closureId);
		memento.setDailyLockState(this.dailyLockState);
		memento.setMonthlyLockState(this.monthlyLockState);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((closureId == null) ? 0 : closureId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActualLock other = (ActualLock) obj;
		if (closureId != other.closureId)
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
	public void lockDaily() {
		this.dailyLockState = LockStatus.LOCK;
	}

	public void unlockDaily() {
		this.dailyLockState = LockStatus.UNLOCK;
	}

	public void lockMonthly() {
		this.monthlyLockState = LockStatus.LOCK;
	}

	public void unlockMonthly() {
		this.monthlyLockState = LockStatus.UNLOCK;
	}
	
}
