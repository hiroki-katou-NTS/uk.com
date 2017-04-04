/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.avgearn.find;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSetting;

/**
 * The Class AvgEarnLevelMasterSettingDto.
 */
@Builder
@Getter
public class AvgEarnLevelMasterSettingDto {

	/** The code. */
	private Integer code;

	/** The health level. */
	private Integer healthLevel;

	/** The pension level. */
	private Integer pensionLevel;

	/** The avg earn. */
	private Long avgEarn;

	/** The sal limit. */
	private Long salLimit;

	/**
	 * From domain.
	 *
	 * @param domain
	 *            the domain
	 * @return the avg earn level master setting dto
	 */
	public static AvgEarnLevelMasterSettingDto fromDomain(AvgEarnLevelMasterSetting domain) {
		return AvgEarnLevelMasterSettingDto.builder().code(domain.getCode())
				.healthLevel(domain.getHealthLevel()).pensionLevel(domain.getPensionLevel())
				.avgEarn(domain.getAvgEarn()).salLimit(domain.getSalLimit()).build();
	}

}
