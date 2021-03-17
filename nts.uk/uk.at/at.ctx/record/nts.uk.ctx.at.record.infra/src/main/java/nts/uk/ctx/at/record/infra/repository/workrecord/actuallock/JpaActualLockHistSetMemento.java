/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistorySetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcdtAtdActuallockHist;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcdtActualLockHistPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Class JpaActualLockHistSetMemento.
 */
public class JpaActualLockHistSetMemento implements ActualLockHistorySetMemento {

	/** The typed value. */
	private KrcdtAtdActuallockHist typedValue;

	
	public JpaActualLockHistSetMemento(KrcdtAtdActuallockHist typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getKrcdtActualLockHistPK() == null) {
			this.typedValue.setKrcdtActualLockHistPK(new KrcdtActualLockHistPK());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typedValue.getKrcdtActualLockHistPK().setCid(companyId);
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
		this.typedValue.getKrcdtActualLockHistPK().setClosureId(closureId.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setTargetMonth(nts.arc.time.YearMonth)
	 */
	@Override
	public void setTargetMonth(YearMonth targetMonth) {
		this.typedValue.setTargetMonth(targetMonth.v());
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
		this.typedValue.setDLockState(dailyLockState.value);
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
		this.typedValue.setMLockState(monthlyLockState.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setLockDateTime(nts.arc.time.GeneralDateTime)
	 */
	@Override
	public void setLockDateTime(GeneralDateTime lockDateTime) {
		this.typedValue.getKrcdtActualLockHistPK().setLockDate(lockDateTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistorySetMemento#setUpdater(java.lang.String)
	 */
	@Override
	public void setUpdater(String updater) {
		this.typedValue.setUpdator(updater);
	}

}
