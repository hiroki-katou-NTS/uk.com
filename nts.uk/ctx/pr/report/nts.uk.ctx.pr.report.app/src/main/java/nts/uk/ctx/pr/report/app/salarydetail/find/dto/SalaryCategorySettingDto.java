/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find.dto;

import java.util.List;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Class SalaryCategorySettingDto.
 */
@Builder
public class SalaryCategorySettingDto {

	/** The category. */
	public SalaryCategory category;

	/** The output items. */
	public List<SalaryOutputSettingItemDto> outputItems;

}
