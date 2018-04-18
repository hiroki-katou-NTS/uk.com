/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class OverTimeCalcNoBreak.
 */
@Getter
// 休�未取得時の残業計�
public class OverTimeCalcNoBreak extends WorkTimeDomainObject {

	/** The calc method. */
	// 計算方�
	private CalcMethodNoBreak calcMethod;

	/** The in law OT. */
	// 法�残業
	private OTFrameNo inLawOT;

	/** The not in law OT. */
	// 法外残業
	private OTFrameNo notInLawOT;

	/**
	 * Instantiates a new over time calc no break.
	 *
	 * @param memento
	 *            the memento
	 */
	public OverTimeCalcNoBreak(OverTimeCalcNoBreakGetMemento memento) {
		this.calcMethod = memento.getCalcMethod();
		this.inLawOT = memento.getInLawOT();
		this.notInLawOT = memento.getNotInLawOT();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(OverTimeCalcNoBreakSetMemento memento) {
		memento.setCalcMethod(this.calcMethod);
		memento.setInLawOT(this.inLawOT);
		memento.setNotInLawOT(this.notInLawOT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject#validate()
	 */
	@Override
	public void validate() {

		// Msg_889
		if (CalcMethodNoBreak.CALC_AS_OVERTIME.equals(this.calcMethod)) {
			if (this.inLawOT == null || this.notInLawOT == null) {
				this.bundledBusinessExceptions.addMessage("Msg_889");
			}
		}

		super.validate();
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, OverTimeCalcNoBreak oldDomain) {
		if (CalcMethodNoBreak.CALC_AS_OVERTIME.equals(this.calcMethod)) {
			this.inLawOT = oldDomain.getInLawOT();
			this.notInLawOT = oldDomain.getNotInLawOT();
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		if (CalcMethodNoBreak.CALC_AS_OVERTIME.equals(this.calcMethod)) {
			this.inLawOT = new OTFrameNo(1);
			this.notInLawOT = new OTFrameNo(1);
		}
	}
}
