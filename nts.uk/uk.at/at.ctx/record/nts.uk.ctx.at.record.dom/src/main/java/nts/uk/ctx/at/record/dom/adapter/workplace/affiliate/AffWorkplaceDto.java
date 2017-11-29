/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.workplace.affiliate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AffWorkplaceDto.
 */
@Getter
@Setter
@Builder
public class AffWorkplaceDto {
	
	/** The workplace id. */
	// 職場ID
	private String workplaceId;

	/** The workplace code. */
	// 勤務場所コード
	private String workplaceCode;

	/** The workplace name. */
	private String workplaceName;

	public AffWorkplaceDto() {
		super();
	}

	public AffWorkplaceDto(String workplaceId, String workplaceCode, String workplaceName) {
		super();
		this.workplaceId = workplaceId;
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
	}
	
}
