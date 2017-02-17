/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class QismtPensionAvgearnPK.
 */
@Embeddable
public class QismtPensionAvgearnPK implements Serializable {

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

	/** The pension grade. */
	@Basic(optional = false)
	@Column(name = "PENSION_GRADE")
	private short pensionGrade;

	/**
	 * Instantiates a new qismt pension avgearn PK.
	 */
	public QismtPensionAvgearnPK() {
		super();
	}

	/**
	 * Instantiates a new qismt pension avgearn PK.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 * @param histId
	 *            the hist id
	 * @param pensionGrade
	 *            the pension grade
	 */
	public QismtPensionAvgearnPK(String ccd, String siOfficeCd, String histId, short pensionGrade) {
		this.ccd = ccd;
		this.siOfficeCd = siOfficeCd;
		this.histId = histId;
		this.pensionGrade = pensionGrade;
	}

	/**
	 * Gets the ccd.
	 *
	 * @return the ccd
	 */
	public String getCcd() {
		return ccd;
	}

	/**
	 * Sets the ccd.
	 *
	 * @param ccd
	 *            the new ccd
	 */
	public void setCcd(String ccd) {
		this.ccd = ccd;
	}

	/**
	 * Gets the si office cd.
	 *
	 * @return the si office cd
	 */
	public String getSiOfficeCd() {
		return siOfficeCd;
	}

	/**
	 * Sets the si office cd.
	 *
	 * @param siOfficeCd
	 *            the new si office cd
	 */
	public void setSiOfficeCd(String siOfficeCd) {
		this.siOfficeCd = siOfficeCd;
	}

	/**
	 * Gets the hist id.
	 *
	 * @return the hist id
	 */
	public String getHistId() {
		return histId;
	}

	/**
	 * Sets the hist id.
	 *
	 * @param histId
	 *            the new hist id
	 */
	public void setHistId(String histId) {
		this.histId = histId;
	}

	/**
	 * Gets the pension grade.
	 *
	 * @return the pension grade
	 */
	public short getPensionGrade() {
		return pensionGrade;
	}

	/**
	 * Sets the pension grade.
	 *
	 * @param pensionGrade
	 *            the new pension grade
	 */
	public void setPensionGrade(short pensionGrade) {
		this.pensionGrade = pensionGrade;
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
		hash += (int) pensionGrade;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof QismtPensionAvgearnPK)) {
			return false;
		}
		QismtPensionAvgearnPK other = (QismtPensionAvgearnPK) object;
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
		if (this.pensionGrade != other.pensionGrade) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.QismtPensionAvgearnPK[ ccd=" + ccd + ", siOfficeCd=" + siOfficeCd + ", histId=" + histId
				+ ", pensionGrade=" + pensionGrade + " ]";
	}

}
