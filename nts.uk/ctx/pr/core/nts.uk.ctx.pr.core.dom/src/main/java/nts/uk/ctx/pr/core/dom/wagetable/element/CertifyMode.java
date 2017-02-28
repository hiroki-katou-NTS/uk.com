/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class CodeRefMode.
 */
@Getter
public class CertifyMode implements ElementMode {

	/** The Constant mode. */
	public static final ElementType mode = ElementType.Certification;

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
	public CertifyMode() {
		super();
		// Create items
		this.items = null;
		new CodeItem("referenceCode", IdentifierUtil.randomUniqueId());
	}

}
