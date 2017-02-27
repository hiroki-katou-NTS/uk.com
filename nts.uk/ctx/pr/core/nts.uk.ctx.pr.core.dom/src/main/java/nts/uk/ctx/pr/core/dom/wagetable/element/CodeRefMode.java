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
 * The Class WageTableRefCd.
 */
@Getter
public class CodeRefMode implements ElementMode {

	/** The Constant mode. */
	public static final ElementType mode = ElementType.CodeRef;

	/** The company code. */
	private CompanyCode companyCode;

	/** The ref no. */
	private WtElementRefNo refNo;

	/** The ref name. */
	private String refName;

	/** The wage ref value. */
	private String wageRefValue;

	/** The wage person table. */
	private String wagePersonTable;

	/** The wage person field. */
	private String wagePersonField;

	/** The wage person query. */
	private String wagePersonQuery;

	/** The items. */
	private List<CodeItem> items;

	public CodeRefMode(CompanyCode companyCode, WtElementRefNo refNo, String refName, String wageRefValue,
			String wagePersonTable, String wagePersonField, String wagePersonQuery) {
		super();
		this.companyCode = companyCode;
		this.refNo = refNo;
		this.refName = refName;
		this.wageRefValue = wageRefValue;
		this.wagePersonTable = wagePersonTable;
		this.wagePersonField = wagePersonField;
		this.wagePersonQuery = wagePersonQuery;

		// Create items
		this.items = null;
		new CodeItem("referenceCode", IdentifierUtil.randomUniqueId());

	}

}
