/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NormalSettingDto.
 */
@Getter
@Setter
public class NormalSettingDto {

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;
	
}