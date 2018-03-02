/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class OverTimeCalcNoBreak.
 */
@Getter
// 休憩未取得時の残業計算
public class OverTimeCalcNoBreak extends WorkTimeDomainObject {

	/** The calc method. */
	// 計算方法
	private CalcMethodNoBreak calcMethod;

	/** The in law OT. */
	// 法内残業
	private OverTimeFrameNo inLawOT;

	/** The not in law OT. */
	// 法外残業
	private OverTimeFrameNo notInLawOT;

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
		if (this.calcMethod == CalcMethodNoBreak.CALC_AS_OVERTIME) {
			if (this.inLawOT == null || this.notInLawOT == null) {
				this.bundledBusinessExceptions.addMessage("Msg_889");
			}
		}

		super.validate();
	}
}
