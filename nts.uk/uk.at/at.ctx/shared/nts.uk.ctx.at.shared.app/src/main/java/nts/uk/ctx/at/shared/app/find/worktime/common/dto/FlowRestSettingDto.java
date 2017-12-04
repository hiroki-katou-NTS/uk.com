/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowRestSettingDto.
 */
@Getter
@Setter
public class FlowRestSettingDto {

	/** The flow rest time. */
	private Integer flowRestTime;

	/** The flow passage time. */
	private Integer flowPassageTime;

	/**
	 * Instantiates a new flow rest setting dto.
	 *
	 * @param flowRestTime the flow rest time
	 * @param flowPassageTime the flow passage time
	 */
	public FlowRestSettingDto(Integer flowRestTime, Integer flowPassageTime) {
		super();
		this.flowRestTime = flowRestTime;
		this.flowPassageTime = flowPassageTime;
	}
	
	
}
