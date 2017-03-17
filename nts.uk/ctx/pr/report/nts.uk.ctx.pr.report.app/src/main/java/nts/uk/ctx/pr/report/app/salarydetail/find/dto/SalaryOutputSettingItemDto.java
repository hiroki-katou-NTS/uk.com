/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find.dto;

import lombok.Builder;

/**
 * The Class SalaryOutputSettingItemDto.
 */
@Builder
public class SalaryOutputSettingItemDto {

	/** The code. */
	public String code;

	/** The name. */
	public String name;

	/** The is aggregate item. */
	public Boolean isAggregateItem;

	/** The order number. */
	public int orderNumber;

}
