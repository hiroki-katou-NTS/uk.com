/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.actuallock;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistorySetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * Instantiates a new actual lock hist find dto.
 */
@Data
public class ActualLockHistFindDto implements ActualLockHistorySetMemento, ActualLockSetMemento {

	/** The closure id. */
	private int closureId;

	/** The lock date time. */
	private String lockDateTime;

	/** The daily lock state. */
	private int dailyLockState;

	/** The monthly lock state. */
	private int monthlyLockState;

	/** The target month. */
	private int targetMonth;

	/** The updater. */
	private String updater;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Do Nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setClosureId(nts.uk.ctx.at.shared.dom.
	 * workrule.closure.ClosureId)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.closureId = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setTargetMonth(nts.arc.time.YearMonth)
	 */
	@Override
	public void setTargetMonth(YearMonth targetMonth) {
		this.targetMonth = targetMonth.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setDailyLockState(nts.uk.ctx.at.record.dom.
	 * workrecord.actuallock.LockStatus)
	 */
	@Override
	public void setDailyLockState(LockStatus dailyLockState) {
		this.dailyLockState = dailyLockState.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setMonthlyLockState(nts.uk.ctx.at.record.dom.
	 * workrecord.actuallock.LockStatus)
	 */
	@Override
	public void setMonthlyLockState(LockStatus monthlyLockState) {
		this.monthlyLockState = monthlyLockState.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setLockDateTime(nts.arc.time.GeneralDateTime)
	 */
	@Override
	public void setLockDateTime(GeneralDateTime lockDateTime) {
		this.lockDateTime = lockDateTime.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setUpdater(java.lang.String)
	 */
	@Override
	public void setUpdater(String updater) {
		this.updater = updater;
	}

}
