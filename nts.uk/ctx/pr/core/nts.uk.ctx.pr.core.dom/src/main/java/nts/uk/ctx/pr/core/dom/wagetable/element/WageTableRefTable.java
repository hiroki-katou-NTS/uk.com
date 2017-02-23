/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;

/**
 * The Class WageTableRefTable.
 */
@Getter
public class WageTableRefTable extends TableRefBase {

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

}
