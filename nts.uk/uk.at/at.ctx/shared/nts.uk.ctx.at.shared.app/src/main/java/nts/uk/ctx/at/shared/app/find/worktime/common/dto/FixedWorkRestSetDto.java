/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FixedWorkRestSetDto.
 */
@Getter
@Setter
public class FixedWorkRestSetDto {

	/** The common rest set. */
	private Integer commonRestSet; 
	
	/** The calculate method. */
	private Integer calculateMethod;

	/**
	 * Instantiates a new fixed work rest set dto.
	 *
	 * @param commonRestSet the common rest set
	 * @param calculateMethod the calculate method
	 */
	public FixedWorkRestSetDto(Integer commonRestSet, Integer calculateMethod) {
		super();
		this.commonRestSet = commonRestSet;
		this.calculateMethod = calculateMethod;
	}
	
	
}
