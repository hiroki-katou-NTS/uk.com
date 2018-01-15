/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Interface ActualLockHistoryGetMemento.
 */
public interface ActualLockHistoryGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	ClosureId getClosureId();

	/**
	 * Gets the daily lock state.
	 *
	 * @return the daily lock state
	 */
	LockStatus getDailyLockState();

	/**
	 * Gets the monthy lock state.
	 *
	 * @return the monthy lock state
	 */
	LockStatus getMonthyLockState();
	
	/**
	 * Gets the target month.
	 *
	 * @return the target month
	 */
	YearMonth getTargetMonth();
	
	/**
	 * Gets the lock date time.
	 *
	 * @return the lock date time
	 */
	GeneralDateTime getLockDateTime();
	
	/**
	 * Gets the updater.
	 *
	 * @return the updater
	 */
	String getUpdater();
	
}
