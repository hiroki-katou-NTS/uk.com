/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.List;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;

/**
 * The Class WageTableRefTable.
 */
@Getter
public class MasterRefMode implements ElementMode {

	/** The company code. */
	private CompanyCode companyCode;

	/** The ref no. */
	private WtElementRefNo refNo;

	/** The ref name. */
	private String refName;

	/** The wage ref table. */
	private String wageRefTable;

	/** The wage ref field. */
	private String wageRefField;

	/** The wage ref disp field. */
	private String wageRefDispField;

	/** The wage person table. */
	private String wagePersonTable;

	/** The wage person field. */
	private String wagePersonField;

	/** The wage ref query. */
	private String wageRefQuery;

	/** The wage person query. */
	private String wagePersonQuery;

	/** The items. */
	private List<CodeItem> items;

	/**
	 * Instantiates a new master ref mode.
	 *
	 * @param companyCode the company code
	 * @param refNo the ref no
	 * @param refName the ref name
	 * @param wageRefTable the wage ref table
	 * @param wageRefField the wage ref field
	 * @param wageRefDispField the wage ref disp field
	 * @param wagePersonTable the wage person table
	 * @param wagePersonField the wage person field
	 * @param wageRefQuery the wage ref query
	 * @param wagePersonQuery the wage person query
	 */
	public MasterRefMode(CompanyCode companyCode, WtElementRefNo refNo, String refName, String wageRefTable,
			String wageRefField, String wageRefDispField, String wagePersonTable, String wagePersonField,
			String wageRefQuery, String wagePersonQuery) {
		super();
		this.companyCode = companyCode;
		this.refNo = refNo;
		this.refName = refName;
		this.wageRefTable = wageRefTable;
		this.wageRefField = wageRefField;
		this.wageRefDispField = wageRefDispField;
		this.wagePersonTable = wagePersonTable;
		this.wagePersonField = wagePersonField;
		this.wageRefQuery = wageRefQuery;
		this.wagePersonQuery = wagePersonQuery;

		// Create items
		this.items = null;
		new CodeItem("referenceCode", IdentifierUtil.randomUniqueId());
	}

}
