/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class CjtmtJobTitlePK.
 */
@Data
@Embeddable
public class CjtmtJobTitlePK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	@Basic(optional = false)
	@Column(name = "CID")
	private String companyId;

	/** The job id. */
	@Basic(optional = false)
	@Column(name = "POS_ID")
	private String jobId;

	/** The job code. */
	@Basic(optional = false)
	@Column(name = "POS_CODE")
	private String jobCode;

	/**
	 * Instantiates a new cjtmt job title PK.
	 */
	public CjtmtJobTitlePK() {
		super();
	}

	/**
	 * Instantiates a new cjtmt job title PK.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @param jobCode the job code
	 */
	public CjtmtJobTitlePK(String companyId, String jobId, String jobCode) {
		super();
		this.companyId = companyId;
		this.jobId = jobId;
		this.jobCode = jobCode;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		CjtmtJobTitlePK other = (CjtmtJobTitlePK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (jobCode == null) {
			if (other.jobCode != null)
				return false;
		} else if (!jobCode.equals(other.jobCode))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((jobCode == null) ? 0 : jobCode.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		return result;
	}

}
