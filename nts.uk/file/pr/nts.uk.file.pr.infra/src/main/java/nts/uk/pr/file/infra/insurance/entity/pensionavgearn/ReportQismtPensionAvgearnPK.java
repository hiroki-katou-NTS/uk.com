/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.insurance.entity.pensionavgearn;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class QismtPensionAvgearnPK.
 */
@Data
@Embeddable
public class ReportQismtPensionAvgearnPK implements Serializable {

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
	private BigDecimal pensionGrade;

	/**
	 * Instantiates a new qismt pension avgearn PK.
	 */
	public ReportQismtPensionAvgearnPK() {
		super();
	}

	/**
	 * Instantiates a new qismt pension avgearn PK.
	 *
	 * @param ccd the ccd
	 * @param siOfficeCd the si office cd
	 * @param histId the hist id
	 * @param pensionGrade the pension grade
	 */
	public ReportQismtPensionAvgearnPK(String ccd, String siOfficeCd, String histId, BigDecimal pensionGrade) {
		this.ccd = ccd;
		this.siOfficeCd = siOfficeCd;
		this.histId = histId;
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
		hash += pensionGrade.intValue();
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQismtPensionAvgearnPK)) {
			return false;
		}
		ReportQismtPensionAvgearnPK other = (ReportQismtPensionAvgearnPK) object;
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

}
