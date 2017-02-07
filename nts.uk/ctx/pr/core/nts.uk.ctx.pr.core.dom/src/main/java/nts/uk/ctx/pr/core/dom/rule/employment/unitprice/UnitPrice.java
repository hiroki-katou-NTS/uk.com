/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Class UnitPrice.
 */
// TODO: @Data -> @Getter
@Data
public class UnitPrice extends AggregateRoot {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private UnitPriceCode code;

	/** The name. */
	private UnitPriceName name;

	/**
	 * Instantiates a new unit price.
	 */
	public UnitPrice() {
		super();
	}

	/**
	 * Instantiates a new unit price.
	 *
	 * @param companyCode
	 *            the company code
	 * @param code
	 *            the code
	 * @param name
	 *            the name
	 */
	public UnitPrice(CompanyCode companyCode, UnitPriceCode code, UnitPriceName name) {
		super();

		// Validate required item
		if (StringUtil.isNullOrEmpty(code.v(), true) || StringUtil.isNullOrEmpty(name.v(), true)) {
			throw new BusinessException("ER001");
		}

		// TODO: Check duplicate code in create mode.
		// throw new BusinessException("ER005");

		this.companyCode = companyCode;
		this.code = code;
		this.name = name;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnitPrice(UnitPriceMemento memento) {
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
	public void saveToMemento(UnitPriceMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setCode(this.code);
		memento.setName(this.name);
		memento.setVersion(this.getVersion());
	}

}
