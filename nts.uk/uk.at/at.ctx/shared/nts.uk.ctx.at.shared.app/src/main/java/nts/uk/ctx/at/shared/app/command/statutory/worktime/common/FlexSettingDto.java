/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlexSettingDto.
 */
@Getter
@Setter
public class FlexSettingDto {

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;

	/** The specified setting. */
	protected List<MonthlyUnitDto> specifiedSetting;

	/** The week average setting. */
	protected List<MonthlyUnitDto> weekAveSetting;
	
	public FlexSettingDto() {
		super();
	}
	

}
