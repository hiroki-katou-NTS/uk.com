/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneOtherSubHolTimeSetDto.
 */
@Getter
@Setter
public class WorkTimezoneOtherSubHolTimeSetDto {
	
	/** The sub hol time set. */
	private SubHolTransferSetDto subHolTimeSet;

	/** The work time code. */
	private String workTimeCode;

	/** The origin atr. */
	private Integer originAtr;

	/**
	 * Instantiates a new work timezone other sub hol time set dto.
	 *
	 * @param subHolTimeSet the sub hol time set
	 * @param workTimeCode the work time code
	 * @param originAtr the origin atr
	 */
	public WorkTimezoneOtherSubHolTimeSetDto(SubHolTransferSetDto subHolTimeSet, String workTimeCode,
			Integer originAtr) {
		super();
		this.subHolTimeSet = subHolTimeSet;
		this.workTimeCode = workTimeCode;
		this.originAtr = originAtr;
	}
	
	

}
