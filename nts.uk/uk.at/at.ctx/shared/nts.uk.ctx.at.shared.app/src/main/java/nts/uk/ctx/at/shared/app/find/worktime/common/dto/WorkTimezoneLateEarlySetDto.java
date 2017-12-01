/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneLateEarlySetDto.
 */
@Getter
@Setter
public class WorkTimezoneLateEarlySetDto {

	/** The common set. */
	private boolean commonSet;
	
	/** The other class set. */
	private List<OtherEmTimezoneLateEarlySetDto> otherClassSet;

	/**
	 * Instantiates a new work timezone late early set dto.
	 *
	 * @param commonSet the common set
	 * @param otherClassSet the other class set
	 */
	public WorkTimezoneLateEarlySetDto(boolean commonSet, List<OtherEmTimezoneLateEarlySetDto> otherClassSet) {
		super();
		this.commonSet = commonSet;
		this.otherClassSet = otherClassSet;
	}
	
	
}
