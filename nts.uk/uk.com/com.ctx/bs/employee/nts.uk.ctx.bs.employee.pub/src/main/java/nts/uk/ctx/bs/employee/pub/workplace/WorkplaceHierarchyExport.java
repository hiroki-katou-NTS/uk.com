/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.workplace;

import lombok.Data;

/**
 * The Class PubWorkplaceHierarchyDto.
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */
@Data
public class WorkplaceHierarchyExport {

	/** The workplace id. */
	private String workplaceId;

	/** The hierarchy code. */
	private String hierarchyCode;

	/**
	 * Instantiates a new pub workplace hierarchy dto.
	 *
	 * @param workplaceId
	 *            the workplace id
	 * @param hierarchyCode
	 *            the hierarchy code
	 */
	public WorkplaceHierarchyExport(String workplaceId, String hierarchyCode) {
		super();
		this.workplaceId = workplaceId;
		this.hierarchyCode = hierarchyCode;
	}

}
