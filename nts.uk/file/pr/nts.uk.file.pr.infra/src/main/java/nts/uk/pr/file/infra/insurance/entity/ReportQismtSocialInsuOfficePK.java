/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.insurance.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class QismtSocialInsuOfficePK.
 */
@Data
@Embeddable
public class ReportQismtSocialInsuOfficePK implements Serializable {

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

	/**
	 * Instantiates a new qismt social insu office PK.
	 */
	public ReportQismtSocialInsuOfficePK() {
		super();
	}

	/**
	 * Instantiates a new qismt social insu office PK.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 */
	public ReportQismtSocialInsuOfficePK(String ccd, String siOfficeCd) {
		this.ccd = ccd;
		this.siOfficeCd = siOfficeCd;
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
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQismtSocialInsuOfficePK)) {
			return false;
		}
		ReportQismtSocialInsuOfficePK other = (ReportQismtSocialInsuOfficePK) object;
		if ((this.ccd == null && other.ccd != null) || (this.ccd != null && !this.ccd.equals(other.ccd))) {
			return false;
		}
		if ((this.siOfficeCd == null && other.siOfficeCd != null)
				|| (this.siOfficeCd != null && !this.siOfficeCd.equals(other.siOfficeCd))) {
			return false;
		}
		return true;
	}

}
