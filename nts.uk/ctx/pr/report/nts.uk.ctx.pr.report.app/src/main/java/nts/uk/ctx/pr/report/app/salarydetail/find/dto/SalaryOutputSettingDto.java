/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find.dto;

import java.util.List;

import lombok.Builder;

/**
 * The Class SalaryOutputSettingDto.
 */
@Builder
public class SalaryOutputSettingDto {

	/** The code. */
	public String code;

	/** The name. */
	public String name;

	/** The category settings. */
	public List<SalaryCategorySettingDto> categorySettings;

}
