/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcdtAtdActuallockHist;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcdtAtdActuallockHistPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * The Class JpaActualLockHistGetMemento.
 */
public class JpaActualLockHistGetMemento implements ActualLockHistoryGetMemento {

	/** The typed value. */
	private KrcdtAtdActuallockHist typedValue;
	
	
	/**
	 * Instantiates a new jpa actual lock hist get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaActualLockHistGetMemento(KrcdtAtdActuallockHist typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getKrcdtAtdActuallockHistPK() == null) {
			this.typedValue.setKrcdtAtdActuallockHistPK(new KrcdtAtdActuallockHistPK());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typedValue.getKrcdtAtdActuallockHistPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getClosureId()
	 */
	@Override
	public ClosureId getClosureId() {
		return ClosureId.valueOf(this.typedValue.getKrcdtAtdActuallockHistPK().getClosureId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getDailyLockState()
	 */
	@Override
	public LockStatus getDailyLockState() {
		return LockStatus.valueOf(this.typedValue.getDLockState());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getMonthyLockState()
	 */
	@Override
	public LockStatus getMonthyLockState() {
		return LockStatus.valueOf(this.typedValue.getMLockState());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getTargetMonth()
	 */
	@Override
	public YearMonth getTargetMonth() {
		return YearMonth.of(this.typedValue.getTargetMonth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getLockDateTime()
	 */
	@Override
	public GeneralDateTime getLockDateTime() {
		return this.typedValue.getKrcdtAtdActuallockHistPK().getLockDate();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento#getUpdater()
	 */
	@Override
	public String getUpdater() {
		return this.typedValue.getUpdator();
	}

}
