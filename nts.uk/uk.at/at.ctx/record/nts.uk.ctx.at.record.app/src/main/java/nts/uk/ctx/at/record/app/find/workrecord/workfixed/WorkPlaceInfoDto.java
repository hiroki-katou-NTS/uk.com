/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Sets the workplace name.
 *
 * @param workplaceName the new workplace name
 */
@Setter

/**
 * Gets the workplace name.
 *
 * @return the workplace name
 */
@Getter

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder
public class WorkPlaceInfoDto {
	
	/** The workplace id. */
	// 職場ID
	private String workplaceId;

	/** The workplace code. */
	// 勤務場所コード
	private String workplaceCode;

	/** The workplace name. */
	private String workplaceName;

	/**
	 * Instantiates a new work place info dto.
	 *
	 * @param workplaceId the workplace id
	 * @param workplaceCode the workplace code
	 * @param workplaceName the workplace name
	 */
	public WorkPlaceInfoDto(String workplaceId, String workplaceCode, String workplaceName) {
		super();
		this.workplaceId = workplaceId;
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
	}

	/**
	 * Instantiates a new work place info dto.
	 */
	public WorkPlaceInfoDto() {
		super();
	}

}
