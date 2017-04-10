/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CmnmtJobTitlePK.
 */
@Setter
@Getter
@Embeddable
public class ReportCmnmtJobTitlePK implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The company code. */
	@Basic(optional = false)	
	@Column(name = "CCD")
	public String companyCode;
	
	/** The job code. */
	@Basic(optional = false)
	@Column(name = "JOBCD")
	public String jobCode;
	
	/** The history id. */
	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String historyId;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		result = prime * result + ((jobCode == null) ? 0 : jobCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportCmnmtJobTitlePK other = (ReportCmnmtJobTitlePK) obj;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		if (jobCode == null) {
			if (other.jobCode != null)
				return false;
		} else if (!jobCode.equals(other.jobCode))
			return false;
		return true;
	}
	
}