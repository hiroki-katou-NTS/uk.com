/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcstActualLock;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcstActualLockPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Class JpaActualLockSetMemento.
 */
public class JpaActualLockSetMemento implements ActualLockSetMemento {

	/** The typed value. */
	private KrcstActualLock typedValue;
	
	public JpaActualLockSetMemento(KrcstActualLock typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getKrcstActualLockPK() == null) {
			this.typedValue.setKrcstActualLockPK(new KrcstActualLockPK());
		}
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typedValue.getKrcstActualLockPK().setCid(companyId);
	}

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.typedValue.getKrcstActualLockPK().setClosureId(closureId.value);
	}

	/**
	 * Sets the daily lock state.
	 *
	 * @param dailyLockState the new daily lock state
	 */
	@Override
	public void setDailyLockState(LockStatus dailyLockState) {
		this.typedValue.setDLockState(dailyLockState.value);
	}

	/**
	 * Sets the monthly lock state.
	 *
	 * @param monthlyLockState the new monthly lock state
	 */
	@Override
	public void setMonthlyLockState(LockStatus monthlyLockState) {
		this.typedValue.setMLockState(monthlyLockState.value);
	}

}
