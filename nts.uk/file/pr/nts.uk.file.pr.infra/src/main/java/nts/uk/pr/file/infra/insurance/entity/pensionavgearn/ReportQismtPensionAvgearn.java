/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.insurance.entity.pensionavgearn;

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
import nts.uk.pr.file.infra.insurance.entity.pensionrate.ReportQismtPensionRate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class QismtPensionAvgearn.
 */
@Getter
@Setter
@Entity
@Table(name = "QISMT_PENSION_AVGEARN")
public class ReportQismtPensionAvgearn extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt pension avgearn PK. */
	@EmbeddedId
	protected ReportQismtPensionAvgearnPK qismtPensionAvgearnPK;

	/** The pension avg earn. */
	@Basic(optional = false)
	@Column(name = "PENSION_AVG_EARN")
	private BigDecimal pensionAvgEarn;

	/** The pension upper limit. */
	@Basic(optional = false)
	@Column(name = "PENSION_UPPER_LIMIT")
	private BigDecimal pensionUpperLimit;

	/** The p pension male mny. */
	@Basic(optional = false)
	@Column(name = "P_PENSION_MALE_MNY")
	private BigDecimal pPensionMaleMny;

	/** The p pension fem mny. */
	@Basic(optional = false)
	@Column(name = "P_PENSION_FEM_MNY")
	private BigDecimal pPensionFemMny;

	/** The p pension miner mny. */
	@Basic(optional = false)
	@Column(name = "P_PENSION_MINER_MNY")
	private BigDecimal pPensionMinerMny;

	/** The c pension male mny. */
	@Basic(optional = false)
	@Column(name = "C_PENSION_MALE_MNY")
	private BigDecimal cPensionMaleMny;

	/** The c pension fem mny. */
	@Basic(optional = false)
	@Column(name = "C_PENSION_FEM_MNY")
	private BigDecimal cPensionFemMny;

	/** The c pension miner mny. */
	@Basic(optional = false)
	@Column(name = "C_PENSION_MINER_MNY")
	private BigDecimal cPensionMinerMny;

	/** The p fund male mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_MALE_MNY")
	private BigDecimal pFundMaleMny;

	/** The p fund fem mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_FEM_MNY")
	private BigDecimal pFundFemMny;

	/** The p fund miner mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_MINER_MNY")
	private BigDecimal pFundMinerMny;

	/** The c fund male mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_MALE_MNY")
	private BigDecimal cFundMaleMny;

	/** The c fund fem mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_FEM_MNY")
	private BigDecimal cFundFemMny;

	/** The c fund miner mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_MINER_MNY")
	private BigDecimal cFundMinerMny;

	/** The p fund exempt male mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_EXEMPT_MALE_MNY")
	private BigDecimal pFundExemptMaleMny;

	/** The p fund exempt fem mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_EXEMPT_FEM_MNY")
	private BigDecimal pFundExemptFemMny;

	/** The p fund exempt miner mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_EXEMPT_MINER_MNY")
	private BigDecimal pFundExemptMinerMny;

	/** The c fund exempt male mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_EXEMPT_MALE_MNY")
	private BigDecimal cFundExemptMaleMny;

	/** The c fund exempt fem mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_EXEMPT_FEM_MNY")
	private BigDecimal cFundExemptFemMny;

	/** The c fund exempt miner mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_EXEMPT_MINER_MNY")
	private BigDecimal cFundExemptMinerMny;

	/** The child contribution mny. */
	@Basic(optional = false)
	@Column(name = "CHILD_CONTRIBUTION_MNY")
	private BigDecimal childContributionMny;

	/** The qismt pension rate. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "SI_OFFICE_CD", referencedColumnName = "SI_OFFICE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private ReportQismtPensionRate qismtPensionRate;

	/**
	 * Instantiates a new qismt pension avgearn.
	 */
	public ReportQismtPensionAvgearn() {
		super();
	}

	/**
	 * Instantiates a new qismt pension avgearn.
	 *
	 * @param qismtPensionAvgearnPK
	 *            the qismt pension avgearn PK
	 */
	public ReportQismtPensionAvgearn(ReportQismtPensionAvgearnPK qismtPensionAvgearnPK) {
		this.qismtPensionAvgearnPK = qismtPensionAvgearnPK;
	}

	/**
	 * Instantiates a new qismt pension avgearn.
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
	public ReportQismtPensionAvgearn(String ccd, String siOfficeCd, String histId,
			BigDecimal pensionGrade) {
		this.qismtPensionAvgearnPK = new ReportQismtPensionAvgearnPK(ccd, siOfficeCd, histId,
				pensionGrade);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtPensionAvgearnPK != null ? qismtPensionAvgearnPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQismtPensionAvgearn)) {
			return false;
		}
		ReportQismtPensionAvgearn other = (ReportQismtPensionAvgearn) object;
		if ((this.qismtPensionAvgearnPK == null && other.qismtPensionAvgearnPK != null)
				|| (this.qismtPensionAvgearnPK != null
						&& !this.qismtPensionAvgearnPK.equals(other.qismtPensionAvgearnPK))) {
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
		return this.qismtPensionAvgearnPK;
	}
}
