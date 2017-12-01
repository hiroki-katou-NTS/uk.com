/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneShortTimeWorkSetDto.
 */
@Getter
@Setter
public class WorkTimezoneShortTimeWorkSetDto {
	
	/** The nurs timezone work use. */
	private boolean nursTimezoneWorkUse;
	
	/** The employment time deduct. */
	private boolean employmentTimeDeduct;
	
	/** The child care work use. */
	private boolean childCareWorkUse;

	/**
	 * Instantiates a new work timezone short time work set dto.
	 *
	 * @param nursTimezoneWorkUse the nurs timezone work use
	 * @param employmentTimeDeduct the employment time deduct
	 * @param childCareWorkUse the child care work use
	 */
	public WorkTimezoneShortTimeWorkSetDto(boolean nursTimezoneWorkUse, boolean employmentTimeDeduct,
			boolean childCareWorkUse) {
		super();
		this.nursTimezoneWorkUse = nursTimezoneWorkUse;
		this.employmentTimeDeduct = employmentTimeDeduct;
		this.childCareWorkUse = childCareWorkUse;
	}
	
	
}
