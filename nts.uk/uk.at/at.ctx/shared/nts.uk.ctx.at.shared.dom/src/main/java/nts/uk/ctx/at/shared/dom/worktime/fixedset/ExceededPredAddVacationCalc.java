/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class ExceededPredAddVacationCalc.
 */
@Getter
// 休暇�算時間が所定を趁�した場合�計�
public class ExceededPredAddVacationCalc extends WorkTimeDomainObject {

	/** The calc method. */
	// 計算方�
	private CalcMethodExceededPredAddVacation calcMethod;

	/** The ot frame no. */
	// 残業�
	private OTFrameNo otFrameNo;

	/**
	 * Instantiates a new exceeded pred add vacation calc.
	 *
	 * @param memento
	 *            the memento
	 */
	public ExceededPredAddVacationCalc(ExceededPredAddVacationCalcGetMemento memento) {
		this.calcMethod = memento.getCalcMethod();
		this.otFrameNo = memento.getOtFrameNo();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ExceededPredAddVacationCalcSetMemento memento) {
		memento.setCalcMethod(this.calcMethod);
		memento.setOtFrameNo(this.otFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject#validate()
	 */
	@Override
	public void validate() {

		// Msg_890
		if (CalcMethodExceededPredAddVacation.CALC_AS_OVERTIME.equals(this.calcMethod)) {
			if (this.otFrameNo == null) {
				this.bundledBusinessExceptions.addMessage("Msg_890");
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
	public void correctData(ScreenMode screenMode, ExceededPredAddVacationCalc oldDomain) {
		if (CalcMethodExceededPredAddVacation.CALC_AS_OVERTIME.equals(this.calcMethod)) {
			this.otFrameNo = oldDomain.getOtFrameNo();
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		if (CalcMethodExceededPredAddVacation.CALC_AS_OVERTIME.equals(this.calcMethod)) {
			this.otFrameNo = new OTFrameNo(1);
		}
	}
}
