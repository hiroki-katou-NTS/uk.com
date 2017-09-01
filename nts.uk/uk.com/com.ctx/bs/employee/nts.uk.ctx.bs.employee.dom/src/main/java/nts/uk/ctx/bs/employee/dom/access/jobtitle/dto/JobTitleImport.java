/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.jobtitle.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class AcJobTitleDto.
 */
@Data
public class JobTitleImport {

	/** The company id. */
	private String companyId;

	/** The position id. */
	private String positionId;

	/** The position code. */
	private String positionCode;

	/** The position name. */
	private String positionName;

	/** The sequence code. */
	private String sequenceCode;

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/**
	 * Instantiates a new ac job title dto.
	 *
	 * @param companyId
	 *            the company id
	 * @param positionId
	 *            the position id
	 * @param positionCode
	 *            the position code
	 * @param positionName
	 *            the position name
	 * @param sequenceCode
	 *            the sequence code
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 */
	public JobTitleImport(String companyId, String positionId, String positionCode,
			String positionName, String sequenceCode, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.companyId = companyId;
		this.positionId = positionId;
		this.positionCode = positionCode;
		this.positionName = positionName;
		this.sequenceCode = sequenceCode;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
