/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplace;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.DeformationLaborSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.FlexSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.shared.NormalSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSetting;

/**
 * The Class WorkplaceWtSettingDto.
 */
@Data
@Builder
public class WorkplaceWtSettingDto {

	/** The flex setting. */
	private FlexSettingDto flexSetting;

	/** The deformation labor setting. */
	private DeformationLaborSettingDto deformationLaborSetting;

	/** The year. */
	private int year;

	/** The normal setting. */
	private NormalSettingDto normalSetting;

	/** The workplace id. */
	private String workplaceId;

	/** The workplace code. */
	private String workplaceCode;

	/** The workplace name. */
	private String workplaceName;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the workplace wt setting dto
	 */
	public static WorkplaceWtSettingDto fromDomain(WorkPlaceWtSetting domain) {
		FlexSettingDto flexSetting = FlexSettingDto.fromDomain(domain.getFlexSetting());
		DeformationLaborSettingDto deformationLaborSetting = DeformationLaborSettingDto
				.fromDomain(domain.getDeformationLaborSetting());
		NormalSettingDto normalSetting = NormalSettingDto.fromDomain(domain.getNormalSetting());

		return WorkplaceWtSettingDto.builder().flexSetting(flexSetting).deformationLaborSetting(deformationLaborSetting)
				.normalSetting(normalSetting).year(domain.getYear().v()).workplaceId(domain.getWorkPlaceId()).build();
	}
}
