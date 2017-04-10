/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.insurance.entity.healthavgearn;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.pr.file.infra.insurance.entity.healthrate.ReportQismtHealthInsuRate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class QismtHealthInsuAvgearn.
 */
@Setter
@Getter
@Entity
@Table(name = "QISMT_HEALTH_INSU_AVGEARN")
public class ReportQismtHealthInsuAvgearn extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt health insu avgearn PK. */
	@EmbeddedId
	protected ReportQismtHealthInsuAvgearnPK qismtHealthInsuAvgearnPK;

	/** The health insu avg earn. */
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_AVG_EARN")
	private BigDecimal healthInsuAvgEarn;

	/** The health insu upper limit. */
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_UPPER_LIMIT")
	private BigDecimal healthInsuUpperLimit;

	/** The p health general mny. */
	@Basic(optional = false)
	@Column(name = "P_HEALTH_GENERAL_MNY")
	private BigDecimal pHealthGeneralMny;

	/** The p health nursing mny. */
	@Basic(optional = false)
	@Column(name = "P_HEALTH_NURSING_MNY")
	private BigDecimal pHealthNursingMny;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	/** The p health specific mny. */
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(name = "P_HEALTH_SPECIFIC_MNY")
	private BigDecimal pHealthSpecificMny;

	/** The p health basic mny. */
	@Basic(optional = false)
	@Column(name = "P_HEALTH_BASIC_MNY")
	private BigDecimal pHealthBasicMny;

	/** The c health general mny. */
	@Basic(optional = false)
	@Column(name = "C_HEALTH_GENERAL_MNY")
	private BigDecimal cHealthGeneralMny;

	/** The c health nursing mny. */
	@Basic(optional = false)
	@Column(name = "C_HEALTH_NURSING_MNY")
	private BigDecimal cHealthNursingMny;

	/** The c health specific mny. */
	@Basic(optional = false)
	@Column(name = "C_HEALTH_SPECIFIC_MNY")
	private BigDecimal cHealthSpecificMny;

	/** The c health basic mny. */
	@Basic(optional = false)
	@Column(name = "C_HEALTH_BASIC_MNY")
	private BigDecimal cHealthBasicMny;

	/** The qismt health insu rate. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "SI_OFFICE_CD", referencedColumnName = "SI_OFFICE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private ReportQismtHealthInsuRate qismtHealthInsuRate;

	/**
	 * Instantiates a new qismt health insu avgearn.
	 */
	public ReportQismtHealthInsuAvgearn() {
		super();
	}

	/**
	 * Instantiates a new qismt health insu avgearn.
	 *
	 * @param qismtHealthInsuAvgearnPK
	 *            the qismt health insu avgearn PK
	 */
	public ReportQismtHealthInsuAvgearn(ReportQismtHealthInsuAvgearnPK qismtHealthInsuAvgearnPK) {
		this.qismtHealthInsuAvgearnPK = qismtHealthInsuAvgearnPK;
	}

	/**
	 * Instantiates a new qismt health insu avgearn.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 * @param histId
	 *            the hist id
	 * @param healthInsuGrade
	 *            the health insu grade
	 */
	public ReportQismtHealthInsuAvgearn(String ccd, String siOfficeCd, String histId,
			BigDecimal healthInsuGrade) {
		this.qismtHealthInsuAvgearnPK = new ReportQismtHealthInsuAvgearnPK(ccd, siOfficeCd, histId,
				healthInsuGrade);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtHealthInsuAvgearnPK != null ? qismtHealthInsuAvgearnPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQismtHealthInsuAvgearn)) {
			return false;
		}
		ReportQismtHealthInsuAvgearn other = (ReportQismtHealthInsuAvgearn) object;
		if ((this.qismtHealthInsuAvgearnPK == null && other.qismtHealthInsuAvgearnPK != null)
				|| (this.qismtHealthInsuAvgearnPK != null
						&& !this.qismtHealthInsuAvgearnPK.equals(other.qismtHealthInsuAvgearnPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.qismtHealthInsuAvgearnPK;
	}
}
