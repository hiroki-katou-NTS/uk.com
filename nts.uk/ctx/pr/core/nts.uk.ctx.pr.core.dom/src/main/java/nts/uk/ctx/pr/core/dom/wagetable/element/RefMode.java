/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;

/**
 * The Class RefMode.
 */
@Getter
public class RefMode extends BaseMode {
	
	/** The company code. */
	private CompanyCode companyCode;

	/** The ref no. */
	private WtElementRefNo refNo;

	/**
	 * Instantiates a new ref mode.
	 *
	 * @param type the type
	 * @param companyCode the company code
	 * @param refNo the ref no
	 */
	public RefMode(ElementType type, CompanyCode companyCode, WtElementRefNo refNo) {
		super(type);
		this.companyCode = companyCode;
		this.refNo = refNo;
	}
}
