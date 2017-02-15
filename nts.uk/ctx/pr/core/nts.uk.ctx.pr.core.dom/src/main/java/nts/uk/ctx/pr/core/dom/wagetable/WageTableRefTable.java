/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import lombok.Getter;

/**
 * The Class TableRefBase.
 */
@Getter
public class WageTableRefTable extends TableRefBase {

	private Integer wageRefTable;
	private Integer wageRefField;
	private Integer wageRefDispField;
	private Integer wagePersonTable;
	private Integer wagePersonField;
	private Integer wageRefQuery;
	private Integer wagePersonQuery;

}
