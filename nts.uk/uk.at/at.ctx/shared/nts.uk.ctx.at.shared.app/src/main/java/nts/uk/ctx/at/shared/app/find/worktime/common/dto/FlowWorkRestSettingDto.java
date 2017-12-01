/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowWorkRestSettingDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDto {
	
	/** The common rest setting. */
	private Integer commonRestSetting;

	/** The flow rest setting. */
	private FlowWorkRestSettingDetailDto flowRestSetting;	

}
