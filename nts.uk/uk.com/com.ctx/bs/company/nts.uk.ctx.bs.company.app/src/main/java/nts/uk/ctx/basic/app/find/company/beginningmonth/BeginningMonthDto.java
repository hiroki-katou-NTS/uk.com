/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.beginningmonth;

import lombok.Data;
import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthSetMemento;

/**
 * The Class BeginningMonthDto.
 */
@Data
public class BeginningMonthDto implements BeginningMonthSetMemento {

	/** The company id. */
	private String companyId;

	/** The start month. */
	private Integer startMonth;


}
