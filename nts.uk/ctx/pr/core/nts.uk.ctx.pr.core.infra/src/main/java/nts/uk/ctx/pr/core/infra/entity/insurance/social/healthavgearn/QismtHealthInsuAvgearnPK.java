/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class QismtHealthInsuAvgearnPK.
 */
@Data
@Embeddable
public class QismtHealthInsuAvgearnPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private String ccd;

	/** The si office cd. */
	@Basic(optional = false)
	@Column(name = "SI_OFFICE_CD")
	private String siOfficeCd;

	/** The hist id. */
	@Basic(optional = false)
	@Column(name = "HIST_ID")
	private String histId;

	/** The health insu grade. */
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_GRADE")
	private BigDecimal healthInsuGrade;

	/**
	 * Instantiates a new qismt health insu avgearn PK.
	 */
	public QismtHealthInsuAvgearnPK() {
		super();
	}

	/**
	 * Instantiates a new qismt health insu avgearn PK.
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
	public QismtHealthInsuAvgearnPK(String ccd, String siOfficeCd, String histId, BigDecimal healthInsuGrade) {
		this.ccd = ccd;
		this.siOfficeCd = siOfficeCd;
		this.histId = histId;
		this.healthInsuGrade = healthInsuGrade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ccd != null ? ccd.hashCode() : 0);
		hash += (siOfficeCd != null ? siOfficeCd.hashCode() : 0);
		hash += (histId != null ? histId.hashCode() : 0);
		hash += healthInsuGrade.intValue();
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QismtHealthInsuAvgearnPK)) {
			return false;
		}
		QismtHealthInsuAvgearnPK other = (QismtHealthInsuAvgearnPK) object;
		if ((this.ccd == null && other.ccd != null) || (this.ccd != null && !this.ccd.equals(other.ccd))) {
			return false;
		}
		if ((this.siOfficeCd == null && other.siOfficeCd != null)
				|| (this.siOfficeCd != null && !this.siOfficeCd.equals(other.siOfficeCd))) {
			return false;
		}
		if ((this.histId == null && other.histId != null)
				|| (this.histId != null && !this.histId.equals(other.histId))) {
			return false;
		}
		if (this.healthInsuGrade != other.healthInsuGrade) {
			return false;
		}
		return true;
	}
}
