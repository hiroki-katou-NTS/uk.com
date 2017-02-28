/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;

/**
 * The Class CodeRefMode.
 */
@Getter
public class CodeRefMode implements ElementMode {

	/** The Constant mode. */
	public static final ElementType mode = ElementType.CodeRef;

	/** The company code. */
	private CompanyCode companyCode;

	/** The ref no. */
	private WtElementRefNo refNo;

	/** The items. */
	private List<CodeItem> items;

	/**
	 * Instantiates a new code ref mode.
	 *
	 * @param companyCode
	 *            the company code
	 * @param refNo
	 *            the ref no
	 */
	public CodeRefMode(CompanyCode companyCode, WtElementRefNo refNo) {
		super();
		this.companyCode = companyCode;
		this.refNo = refNo;

		// Create items
		this.items = null;
		new CodeItem("referenceCode", IdentifierUtil.randomUniqueId());
	}

}
