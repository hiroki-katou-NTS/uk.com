/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.insurance.entity.healthrate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class QismtHealthInsuRatePK.
 */
@Data
@Embeddable
public class ReportQismtHealthInsuRatePK implements Serializable {

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

	/**
	 * Instantiates a new qismt health insu rate PK.
	 */
	public ReportQismtHealthInsuRatePK() {
	}

	/**
	 * Instantiates a new qismt health insu rate PK.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 * @param histId
	 *            the hist id
	 */
	public ReportQismtHealthInsuRatePK(String ccd, String siOfficeCd, String histId) {
		this.ccd = ccd;
		this.siOfficeCd = siOfficeCd;
		this.histId = histId;
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
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQismtHealthInsuRatePK)) {
			return false;
		}
		ReportQismtHealthInsuRatePK other = (ReportQismtHealthInsuRatePK) object;
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
		return true;
	}
}
