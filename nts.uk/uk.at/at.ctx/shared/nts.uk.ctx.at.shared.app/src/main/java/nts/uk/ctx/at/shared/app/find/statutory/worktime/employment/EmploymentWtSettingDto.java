/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employment;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.DeformationLaborSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.FlexSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.NormalSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSetting;

/**
 * The Class EmploymentSettingDto.
 */
@Data
@Builder
public class EmploymentWtSettingDto {

	/** The flex setting. */
	private FlexSettingDto flexSetting;

	/** The deformation labor setting. */
	private DeformationLaborSettingDto deformationLaborSetting;

	/** The year. */
	private int year;

	/** The normal setting. */
	private NormalSettingDto normalSetting;

	/** The employment code. */
	private String employmentCode;

	/** The employment name. */
	private String employmentName;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the employment wt setting dto
	 */
	public static EmploymentWtSettingDto fromDomain(EmploymentWtSetting domain) {
		FlexSettingDto flexSetting = FlexSettingDto.fromDomain(domain.getFlexSetting());
		DeformationLaborSettingDto deformationLaborSetting = DeformationLaborSettingDto
				.fromDomain(domain.getDeformationLaborSetting());
		NormalSettingDto normalSetting = NormalSettingDto.fromDomain(domain.getNormalSetting());

		return new EmploymentWtSettingDto.EmploymentWtSettingDtoBuilder().year(domain.getYear().v())
				.deformationLaborSetting(deformationLaborSetting).flexSetting(flexSetting).normalSetting(normalSetting)
				.employmentCode(domain.getEmploymentCode()).build();
	}
}
