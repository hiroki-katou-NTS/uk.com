/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.beginningmonth;

import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthSetMemento;
import nts.uk.ctx.basic.infra.entity.company.beginningmonth.CbmstBeginningMonth;

/**
 * The Class JpaBeginningMonthSetMemento.
 */
public class JpaBeginningMonthSetMemento implements BeginningMonthSetMemento {

	/** The entity. */
	private CbmstBeginningMonth entity;

	/**
	 * Instantiates a new jpa beginning month set memento.
	 *
	 * @param entity the entity
	 */
	public JpaBeginningMonthSetMemento(CbmstBeginningMonth entity) {
		this.entity = entity;
	}


}
