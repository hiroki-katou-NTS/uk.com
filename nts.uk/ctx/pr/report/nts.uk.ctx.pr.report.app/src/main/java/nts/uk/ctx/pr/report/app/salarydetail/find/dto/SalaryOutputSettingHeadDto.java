/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find.dto;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Class SalaryOutputSettingHeadDto.
 */
@Builder
public class SalaryOutputSettingHeadDto {

	/** The code. */
	public String code;

	/** The name. */
	public String name;

	/** The category. */
	public SalaryCategory category;

}
