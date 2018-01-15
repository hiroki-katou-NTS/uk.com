/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.beginningmonth;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.bs.company.dom.company.CompanyId;
import nts.uk.ctx.bs.company.dom.company.StartMonth;

/**
 * The Class BeginningMonth.
 */
// 期首月
@Getter
public class BeginningMonth extends DomainObject {
	 
	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The month. */
	// 月
	private StartMonth month;
	
	/**
	 * Instantiates a new beginning month.
	 *
	 * @param memento the memento
	 */
	public BeginningMonth(BeginningMonthGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.month = memento.getMonth();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(BeginningMonthSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setMonth(this.month);
	}
}
