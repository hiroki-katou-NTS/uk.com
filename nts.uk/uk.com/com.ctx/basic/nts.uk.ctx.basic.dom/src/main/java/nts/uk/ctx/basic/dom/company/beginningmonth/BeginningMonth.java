/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.beginningmonth;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class BeginningMonth.
 */
// 期首月
@Getter
public class BeginningMonth extends DomainObject {

	/**
	 * Instantiates a new beginning month.
	 *
	 * @param memento the memento
	 */
	public BeginningMonth(BeginningMonthGetMemento memento) {
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(BeginningMonthSetMemento memento) {
	}
}
