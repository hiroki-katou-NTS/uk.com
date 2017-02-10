/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate;

import lombok.Data;

/**
 * The Class UnemployeeInsuranceRateItemDto.
 */
@Data
public class UnemployeeInsuranceRateItemDto {
	/** The career group. */
	private Integer careerGroup;

	/** The company setting. */
	private UnemployeeInsuranceRateItemSettingDto companySetting;

	/** The personal setting. */
	private UnemployeeInsuranceRateItemSettingDto personalSetting;
}
