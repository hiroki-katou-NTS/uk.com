/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CjtmtJobTitle.
 */
@Getter
@Setter
@Entity
@Table(name = "CJTMT_JOB_TITLE")
public class CjtmtJobTitle extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cjmt job title PK. */
	@EmbeddedId
	protected CjtmtJobTitlePK cjmtJobTitlePK;

	/** The job name. */
	@Column(name = "POS_NAME")
	private String jobName;

	/** The sequence code. */
	@Column(name = "SEQ_CODE")
	private String sequenceCode;

	/** The start date. */
	@Column(name = "STR_D")
	private GeneralDate startDate;

	/** The end date. */
	@Column(name = "END_D")
	private GeneralDate endDate;

	/**
	 * Instantiates a new cjtmt job title.
	 *
	 * @param cjmtJobTitlePK the cjmt job title PK
	 */
	public CjtmtJobTitle(CjtmtJobTitlePK cjmtJobTitlePK) {
		super();
		this.cjmtJobTitlePK = cjmtJobTitlePK;
	}

	/**
	 * Instantiates a new cjtmt job title.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @param jobCode the job code
	 */
	public CjtmtJobTitle(String companyId, String jobId, String jobCode) {
		super();
		this.cjmtJobTitlePK = new CjtmtJobTitlePK(companyId, jobId, jobCode);
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cjmtJobTitlePK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cjmtJobTitlePK == null) ? 0 : cjmtJobTitlePK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CjtmtJobTitle other = (CjtmtJobTitle) obj;
		if (cjmtJobTitlePK == null) {
			if (other.cjmtJobTitlePK != null)
				return false;
		} else if (!cjmtJobTitlePK.equals(other.cjmtJobTitlePK))
			return false;
		return true;
	}

}
