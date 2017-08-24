/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;

/**
 * The Interface TotalSubjectsGetMemento.
 */
public interface TotalSubjectsGetMemento {

	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	WorkTypeCode getWorkTypeCode();

	/**
	 * Gets the work type atr.
	 *
	 * @return the work type atr
	 */
	WorkTypeAtr getWorkTypeAtr();

}
