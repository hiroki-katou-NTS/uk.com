/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Interface ActualLockHistorySetMemento.
 */
public interface ActualLockHistorySetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	void setClosureId(ClosureId closureId);
	
	/**
	 * Sets the target month.
	 *
	 * @param targetMonth the new target month
	 */
	void setTargetMonth(YearMonth targetMonth);
	
	/**
	 * Sets the daily lock state.
	 *
	 * @param dailyLockState the new daily lock state
	 */
	void setDailyLockState(LockStatus dailyLockState);
	
	/**
	 * Sets the monthly lock state.
	 *
	 * @param dailyLockState the new monthly lock state
	 */
	void setMonthlyLockState(LockStatus dailyLockState);
	
	/**
	 * Sets the lock date time.
	 *
	 * @param lockDateTime the new lock date time
	 */
	void setLockDateTime(GeneralDateTime lockDateTime);
	
	/**
	 * Sets the updater.
	 *
	 * @param updater the new updater
	 */
	void setUpdater(String updater);
	
}
