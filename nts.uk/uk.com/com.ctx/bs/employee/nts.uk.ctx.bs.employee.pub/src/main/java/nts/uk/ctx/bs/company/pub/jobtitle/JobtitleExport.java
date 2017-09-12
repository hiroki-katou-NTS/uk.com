/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.jobtitle;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class PubJobtitleDto.
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */
@Data
public class JobtitleExport {

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
	 * Instantiates a new pub jobtitle dto.
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
	public JobtitleExport(String companyId, String positionId, String positionCode,
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
