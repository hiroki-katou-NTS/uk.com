/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * The Class QismtHealthInsuAvgearn.
 */
@Data
@Entity
@Table(name = "QISMT_HEALTH_INSU_AVGEARN")
public class QismtHealthInsuAvgearn implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt health insu avgearn PK. */
	@EmbeddedId
	protected QismtHealthInsuAvgearnPK qismtHealthInsuAvgearnPK;

	/** The ins date. */
	@Column(name = "INS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDate;

	/** The ins ccd. */
	@Column(name = "INS_CCD")
	private String insCcd;

	/** The ins scd. */
	@Column(name = "INS_SCD")
	private String insScd;

	/** The ins pg. */
	@Column(name = "INS_PG")
	private String insPg;

	/** The upd date. */
	@Column(name = "UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDate;

	/** The upd ccd. */
	@Column(name = "UPD_CCD")
	private String updCcd;

	/** The upd scd. */
	@Column(name = "UPD_SCD")
	private String updScd;

	/** The upd pg. */
	@Column(name = "UPD_PG")
	private String updPg;

	/** The exclus ver. */
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	private long exclusVer;

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

	/**
	 * Instantiates a new qismt health insu avgearn.
	 */
	public QismtHealthInsuAvgearn() {
		super();
	}

	/**
	 * Instantiates a new qismt health insu avgearn.
	 *
	 * @param qismtHealthInsuAvgearnPK
	 *            the qismt health insu avgearn PK
	 */
	public QismtHealthInsuAvgearn(QismtHealthInsuAvgearnPK qismtHealthInsuAvgearnPK) {
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
	public QismtHealthInsuAvgearn(String ccd, String siOfficeCd, String histId, BigDecimal healthInsuGrade) {
		this.qismtHealthInsuAvgearnPK = new QismtHealthInsuAvgearnPK(ccd, siOfficeCd, histId, healthInsuGrade);
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
		if (!(object instanceof QismtHealthInsuAvgearn)) {
			return false;
		}
		QismtHealthInsuAvgearn other = (QismtHealthInsuAvgearn) object;
		if ((this.qismtHealthInsuAvgearnPK == null && other.qismtHealthInsuAvgearnPK != null)
				|| (this.qismtHealthInsuAvgearnPK != null
						&& !this.qismtHealthInsuAvgearnPK.equals(other.qismtHealthInsuAvgearnPK))) {
			return false;
		}
		return true;
	}
}
