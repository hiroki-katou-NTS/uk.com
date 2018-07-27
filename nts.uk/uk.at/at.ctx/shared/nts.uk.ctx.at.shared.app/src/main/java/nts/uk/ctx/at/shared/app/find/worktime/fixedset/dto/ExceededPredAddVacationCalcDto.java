/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalcSetMemento;

/**
 * The Class ExceededPredAddVacationCalcDto.
 */
@Getter
@Setter
public class ExceededPredAddVacationCalcDto implements ExceededPredAddVacationCalcSetMemento {

	/** The calc method. */
	private Integer calcMethod;

	/** The ot frame no. */
	private Integer otFrameNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcSetMemento#setCalcMethod(nts.uk.ctx.at.shared.
	 * dom.worktime.common.CalcMethodExceededPredAddVacation)
	 */
	@Override
	public void setCalcMethod(CalcMethodExceededPredAddVacation calcMethod) {
		if (calcMethod == null) {
			return;
		}
		this.calcMethod = calcMethod.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcSetMemento#setOtFrameNo(nts.uk.ctx.at.shared.
	 * dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo)
	 */
	@Override
	public void setOtFrameNo(OTFrameNo otFrameNo) {
		if (otFrameNo == null) {
			return;
		}
		this.otFrameNo = otFrameNo.v();
	}

}
