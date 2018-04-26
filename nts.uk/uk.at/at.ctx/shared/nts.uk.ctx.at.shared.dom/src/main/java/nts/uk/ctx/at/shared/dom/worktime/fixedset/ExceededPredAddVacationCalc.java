/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class ExceededPredAddVacationCalc.
 */
@Getter
//休暇加算時間が所定を超過した場合の計算
public class ExceededPredAddVacationCalc extends WorkTimeDomainObject {

	/** The calc method. */
	// 計算方法
	private CalcMethodExceededPredAddVacation calcMethod;

	/** The ot frame no. */
	// 残業枠
	private OverTimeFrameNo otFrameNo;

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
		if (this.calcMethod == CalcMethodExceededPredAddVacation.CALC_AS_OVERTIME) {
			if (this.otFrameNo == null) {
				this.bundledBusinessExceptions.addMessage("Msg_890");
			}
		}

		super.validate();
	}

	/**
	 * Constructor 
	 */
	public ExceededPredAddVacationCalc(CalcMethodExceededPredAddVacation calcMethod, OverTimeFrameNo otFrameNo) {
		super();
		this.calcMethod = calcMethod;
		this.otFrameNo = otFrameNo;
	}
	
	
}
