/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.dom.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class PubJobTitleDto.
 */
@Getter
@AllArgsConstructor
public class JobTitleImport {

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
	
	public boolean isGreaterThan(JobTitleImport compared) {
		if(this.getSequenceCode()==null||compared.getSequenceCode()==null) {
			return true;
		}
		return this.getSequenceCode().compareTo(compared.getSequenceCode()) > 0;
	}

}
