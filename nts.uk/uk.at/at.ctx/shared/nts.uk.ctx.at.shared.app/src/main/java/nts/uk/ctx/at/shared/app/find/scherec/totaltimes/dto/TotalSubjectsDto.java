/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TotalSubjectsDto.
 */
@Getter
@Setter
public class TotalSubjectsDto {

	/** The work type code. */
	private String workTypeCode;

	/** The work type atr. */
	private Integer workTypeAtr;

	/**
	 * Instantiates a new total subjects dto.
	 *
	 * @param workTypeCode the work type code
	 * @param workTypeAtr the work type atr
	 */
	public TotalSubjectsDto(String workTypeCode, Integer workTypeAtr) {
		super();
		this.workTypeCode = workTypeCode;
		this.workTypeAtr = workTypeAtr;
	}

	/**
	 * Instantiates a new total subjects dto.
	 */
	public TotalSubjectsDto() {
		super();
	}

}
