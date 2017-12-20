/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Builder
public class CurrentClosureDto {
	
	/** The closure id. */
	private Integer closureId;
	
	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;
	
	/** The closure name. */
	private String closureName;
		
	/** The processing date. */
	//処理年月
	private Integer processingDate;

	/**
	 * Instantiates a new closure name and period dto.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param closureName the closure name
	 */
	public CurrentClosureDto(Integer closureId, GeneralDate startDate, GeneralDate endDate, String closureName, Integer processingDate) {
		super();
		this.closureId = closureId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.closureName = closureName;
		this.processingDate = processingDate;
	}

	/**
	 * Instantiates a new closure name and period dto.
	 */
	public CurrentClosureDto() {
		super();
	}	
}
