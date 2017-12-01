/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowFixedRestSetDto.
 */
@Getter
@Setter
public class FlowFixedRestSetDto {

	/** The is refer rest time. */
	private boolean isReferRestTime;
	
	/** The use private go out rest. */
	private boolean usePrivateGoOutRest;
	
	/** The use asso go out rest. */
	private boolean useAssoGoOutRest;
	
	/** The calculate method. */
	private Integer calculateMethod;
}
