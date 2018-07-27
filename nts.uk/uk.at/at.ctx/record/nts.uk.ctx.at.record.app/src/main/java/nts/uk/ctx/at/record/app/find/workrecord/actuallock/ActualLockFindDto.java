/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.actuallock;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * Instantiates a new actual lock find dto.
 */
@Data
@Builder
public class ActualLockFindDto implements ActualLockSetMemento {

	/** The closure id. */
	private int closureId;

	/** The daily lock state. */
	private int dailyLockState;

	/** The monthly lock state. */
	private int monthlyLockState;
	
	/**
	 * Instantiates a new actual lock find dto.
	 */
	public ActualLockFindDto() {
		super();
	}
	
	/**
	 * Instantiates a new actual lock find dto.
	 *
	 * @param closureId the closure id
	 * @param dailyLockState the daily lock state
	 * @param monthlyLockState the monthly lock state
	 */
	public ActualLockFindDto(int closureId, int dailyLockState, int monthlyLockState) {
		super();
		this.closureId = closureId;
		this.dailyLockState = dailyLockState;
		this.monthlyLockState = monthlyLockState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Do Nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento#
	 * setClosureId(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.closureId = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento#
	 * setDailyLockState(nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * LockStatus)
	 */
	@Override
	public void setDailyLockState(LockStatus dailyLockState) {
		this.dailyLockState = dailyLockState.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockSetMemento#
	 * setMonthlyLockState(nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * LockStatus)
	 */
	@Override
	public void setMonthlyLockState(LockStatus monthlyLockState) {
		this.monthlyLockState = monthlyLockState.value;
	}

}
