/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalcGetMemento;

/**
 * The Class ExceededPredAddVacationCalcDto.
 */
@Value
public class ExceededPredAddVacationCalcDto implements ExceededPredAddVacationCalcGetMemento {

	/** The calc method. */
	private Integer calcMethod;

	/** The ot frame no. */
	private Integer otFrameNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcGetMemento#getCalcMethod()
	 */
	@Override
	public CalcMethodExceededPredAddVacation getCalcMethod() {
		return CalcMethodExceededPredAddVacation.valueOf(calcMethod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcGetMemento#getOtFrameNo()
	 */
	@Override
	public OTFrameNo getOtFrameNo() {
		if (this.otFrameNo == null) {
			return null;
		}
		return new OTFrameNo(this.otFrameNo);
	}

}
