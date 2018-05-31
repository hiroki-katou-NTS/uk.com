/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationGetMemento;

/**
 * The Class HolidayCalculationDto.
 */
@Getter
@Setter
public class HolidayCalculationDto implements HolidayCalculationGetMemento {

	/** The is calculate. */
	private Integer isCalculate;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationGetMemento#getIsCalculate()
	 */
	@Override
	public NotUseAtr getIsCalculate() {
		return NotUseAtr.valueOf(this.isCalculate);
	}

}
