/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
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

	/** The cjtmt job title PK. */
	@EmbeddedId
	protected CjtmtJobTitlePK cjtmtJobTitlePK;

	/** The job name. */
	@Column(name = "JOB_NAME")
	private String jobName;

	/** The sequence code. */
	@Column(name = "SEQ_CD")
	private String sequenceCode;

	/** The start date. */
	@Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate startDate;

	/** The end date. */
	@Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endDate;

	/** The csqmt sequence master. */
	@JoinColumns({
			@JoinColumn(name = "SEQ_CD", referencedColumnName = "SEQ_CD", insertable = false, updatable = false),
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false), })
	@ManyToOne(optional = false)
	private CsqmtSequenceMaster csqmtSequenceMaster;

	/**
	 * Instantiates a new cjtmt job title.
	 */
	public CjtmtJobTitle() {
		super();
	}

	/**
	 * Instantiates a new cjtmt job title.
	 *
	 * @param cjtmtJobTitlePK the cjtmt job title PK
	 */
	public CjtmtJobTitle(CjtmtJobTitlePK cjtmtJobTitlePK) {
		super();
		this.cjtmtJobTitlePK = cjtmtJobTitlePK;
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
		this.cjtmtJobTitlePK = new CjtmtJobTitlePK(companyId, jobId, jobCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cjtmtJobTitlePK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cjtmtJobTitlePK == null) ? 0 : cjtmtJobTitlePK.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (cjtmtJobTitlePK == null) {
			if (other.cjtmtJobTitlePK != null)
				return false;
		} else if (!cjtmtJobTitlePK.equals(other.cjtmtJobTitlePK))
			return false;
		return true;
	}

}
