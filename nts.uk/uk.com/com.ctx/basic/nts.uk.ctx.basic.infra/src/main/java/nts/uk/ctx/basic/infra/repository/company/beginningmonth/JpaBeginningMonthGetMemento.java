/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.beginningmonth;

import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthGetMemento;
import nts.uk.ctx.basic.infra.entity.company.beginningmonth.CbmstBeginningMonth;

/**
 * The Class JpaBeginningMonthGetMemento.
 */
public class JpaBeginningMonthGetMemento implements BeginningMonthGetMemento {

	/** The entity. */
	private CbmstBeginningMonth entity;

	/**
	 * Instantiates a new jpa beginning month get memento.
	 *
	 * @param entity the entity
	 */
	public JpaBeginningMonthGetMemento(CbmstBeginningMonth entity) {
		this.entity = entity;
	}

}
