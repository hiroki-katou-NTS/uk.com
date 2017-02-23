/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;

/**
 * The Class WageTableRefCd.
 */
@Getter
public class WageTableRefCd extends TableRefBase {

	/** The wage ref value. */
	private String wageRefValue;
	
	/** The wage person table. */
	private String wagePersonTable;
	
	/** The wage person field. */
	private String wagePersonField;
	
	/** The wage person query. */
	private String wagePersonQuery;

}
