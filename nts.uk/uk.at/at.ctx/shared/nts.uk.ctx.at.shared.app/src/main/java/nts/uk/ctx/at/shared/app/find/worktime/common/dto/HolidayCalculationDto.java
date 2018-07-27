/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationSetMemento;

/**
 * The Class HolidayCalculationDto.
 */
@Getter
@Setter
public class HolidayCalculationDto implements HolidayCalculationSetMemento {

	/** The is calculate. */
	private Integer isCalculate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationSetMemento#
	 * setIsCalculate(nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr)
	 */
	@Override
	public void setIsCalculate(NotUseAtr isCalculate) {
		this.isCalculate = isCalculate.value;
	}

}
