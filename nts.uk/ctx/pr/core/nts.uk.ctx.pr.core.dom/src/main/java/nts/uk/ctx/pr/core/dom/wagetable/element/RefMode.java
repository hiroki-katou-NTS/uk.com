/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
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

	/** The items. */
	@Setter
	private List<CodeItem> items;

	/**
	 * Instantiates a new ref mode.
	 *
	 * @param type
	 *            the type
	 * @param companyCode
	 *            the company code
	 * @param refNo
	 *            the ref no
	 */
	public RefMode(ElementType type, CompanyCode companyCode, WtElementRefNo refNo) {
		super(type);
		this.companyCode = companyCode;
		this.refNo = refNo;
	}

	/**
	 * Instantiates a new ref mode.
	 *
	 * @param type
	 *            the type
	 * @param companyCode
	 *            the company code
	 * @param refNo
	 *            the ref no
	 * @param items
	 *            the items
	 */
	public RefMode(ElementType type, CompanyCode companyCode, WtElementRefNo refNo,
			List<CodeItem> items) {
		super(type);
		this.companyCode = companyCode;
		this.refNo = refNo;
		this.items = items;
	}

}
