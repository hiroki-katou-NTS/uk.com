/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Class ActualLockHistory.
 */
// 実績ロックの履歴
@Getter
public class ActualLockHistory extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The closure id. */
	//締めID
	private ClosureId closureId;
	
	/** The lock date time. */
	// ロック日時
	private GeneralDateTime lockDateTime;
	
	/** The target year month. */
	// 対象月
	private YearMonth targetMonth;
	
	/** The daily lock state. */
	// 日別のロック状態
	private LockStatus dailyLockState;
	
	/** The monthly lock state. */
	// 月別のロック状態
	private LockStatus monthlyLockState;
	
	/** The updater. */
	// 更新者
	private String updater;
	
	/**
	 * Instantiates a new actual lock history.
	 *
	 * @param memento the memento
	 */
	public ActualLockHistory (ActualLockHistoryGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.closureId = memento.getClosureId();
		this.lockDateTime = memento.getLockDateTime();
		this.targetMonth = memento.getTargetMonth();
		this.dailyLockState = memento.getDailyLockState();
		this.monthlyLockState = memento.getMonthyLockState();
		this.updater = memento.getUpdater();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (ActualLockHistorySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setClosureId(this.closureId);
		memento.setLockDateTime(this.lockDateTime);
		memento.setTargetMonth(this.targetMonth);
		memento.setDailyLockState(this.dailyLockState);
		memento.setMonthlyLockState(this.monthlyLockState);
		memento.setUpdater(this.updater);
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
		result = prime * result + ((lockDateTime == null) ? 0 : lockDateTime.hashCode());
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
		ActualLockHistory other = (ActualLockHistory) obj;
		if (closureId != other.closureId)
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (lockDateTime == null) {
			if (other.lockDateTime != null)
				return false;
		} else if (!lockDateTime.equals(other.lockDateTime))
			return false;
		return true;
	}

	
	
	
}
