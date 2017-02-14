/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Class UnitPrice.
 */
@Getter
public class UnitPrice extends AggregateRoot {

	/** The id. */
	private String id;

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private UnitPriceCode code;

	/** The name. */
	@Setter
	private UnitPriceName name;

	/**
	 * Instantiates a new unit price.
	 */
	public UnitPrice() {
		super();
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnitPrice(UnitPriceGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.code = memento.getCode();
		this.name = memento.getName();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(UnitPriceSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setCode(this.code);
		memento.setName(this.name);
		memento.setVersion(this.getVersion());
	}

}
