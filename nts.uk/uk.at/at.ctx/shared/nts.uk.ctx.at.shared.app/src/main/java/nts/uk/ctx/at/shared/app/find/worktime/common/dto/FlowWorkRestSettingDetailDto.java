/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowWorkRestSettingDetailDto.
 */
@Getter
@Setter
public class FlowWorkRestSettingDetailDto {

	/** The flow rest setting. */
	private FlowRestSetDto flowRestSetting;

	/** The flow fixed rest setting. */
	private FlowFixedRestSetDto flowFixedRestSetting;

	/** The use plural work rest time. */
	private boolean usePluralWorkRestTime;	
	
}
