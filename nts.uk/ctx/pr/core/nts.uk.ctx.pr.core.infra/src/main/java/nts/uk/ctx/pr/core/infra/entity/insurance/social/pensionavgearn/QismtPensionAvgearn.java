/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn;

import java.io.Serializable;
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
 * The Class QismtPensionAvgearn.
 */
@Data
@Entity
@Table(name = "QISMT_PENSION_AVGEARN")
public class QismtPensionAvgearn implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt pension avgearn PK. */
	@EmbeddedId
	protected QismtPensionAvgearnPK qismtPensionAvgearnPK;

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

	/** The pension avg earn. */
	@Basic(optional = false)
	@Column(name = "PENSION_AVG_EARN")
	private long pensionAvgEarn;

	/** The pension upper limit. */
	@Basic(optional = false)
	@Column(name = "PENSION_UPPER_LIMIT")
	private long pensionUpperLimit;

	/** The p pension male mny. */
	@Basic(optional = false)
	@Column(name = "P_PENSION_MALE_MNY")
	private long pPensionMaleMny;

	/** The p pension fem mny. */
	@Basic(optional = false)
	@Column(name = "P_PENSION_FEM_MNY")
	private long pPensionFemMny;

	/** The p pension miner mny. */
	@Basic(optional = false)
	@Column(name = "P_PENSION_MINER_MNY")
	private long pPensionMinerMny;

	/** The c pension male mny. */
	@Basic(optional = false)
	@Column(name = "C_PENSION_MALE_MNY")
	private long cPensionMaleMny;

	/** The c pension fem mny. */
	@Basic(optional = false)
	@Column(name = "C_PENSION_FEM_MNY")
	private long cPensionFemMny;

	/** The c pension miner mny. */
	@Basic(optional = false)
	@Column(name = "C_PENSION_MINER_MNY")
	private long cPensionMinerMny;

	/** The p fund male mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_MALE_MNY")
	private long pFundMaleMny;

	/** The p fund fem mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_FEM_MNY")
	private long pFundFemMny;

	/** The p fund miner mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_MINER_MNY")
	private long pFundMinerMny;

	/** The c fund male mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_MALE_MNY")
	private long cFundMaleMny;

	/** The c fund fem mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_FEM_MNY")
	private long cFundFemMny;

	/** The c fund miner mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_MINER_MNY")
	private long cFundMinerMny;

	/** The p fund exempt male mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_EXEMPT_MALE_MNY")
	private long pFundExemptMaleMny;

	/** The p fund exempt fem mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_EXEMPT_FEM_MNY")
	private long pFundExemptFemMny;

	/** The p fund exempt miner mny. */
	@Basic(optional = false)
	@Column(name = "P_FUND_EXEMPT_MINER_MNY")
	private long pFundExemptMinerMny;

	/** The c fund exempt male mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_EXEMPT_MALE_MNY")
	private long cFundExemptMaleMny;

	/** The c fund exempt fem mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_EXEMPT_FEM_MNY")
	private long cFundExemptFemMny;

	/** The c fund exempt miner mny. */
	@Basic(optional = false)
	@Column(name = "C_FUND_EXEMPT_MINER_MNY")
	private long cFundExemptMinerMny;

	/** The child contribution mny. */
	@Basic(optional = false)
	@Column(name = "CHILD_CONTRIBUTION_MNY")
	private long childContributionMny;

	/**
	 * Instantiates a new qismt pension avgearn.
	 */
	public QismtPensionAvgearn() {
		super();
	}

	/**
	 * Instantiates a new qismt pension avgearn.
	 *
	 * @param qismtPensionAvgearnPK
	 *            the qismt pension avgearn PK
	 */
	public QismtPensionAvgearn(QismtPensionAvgearnPK qismtPensionAvgearnPK) {
		this.qismtPensionAvgearnPK = qismtPensionAvgearnPK;
	}

	/**
	 * Instantiates a new qismt pension avgearn.
	 *
	 * @param qismtPensionAvgearnPK
	 *            the qismt pension avgearn PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param pensionAvgEarn
	 *            the pension avg earn
	 * @param pensionUpperLimit
	 *            the pension upper limit
	 * @param pPensionMaleMny
	 *            the pension male mny
	 * @param pPensionFemMny
	 *            the pension fem mny
	 * @param pPensionMinerMny
	 *            the pension miner mny
	 * @param cPensionMaleMny
	 *            the c pension male mny
	 * @param cPensionFemMny
	 *            the c pension fem mny
	 * @param cPensionMinerMny
	 *            the c pension miner mny
	 * @param pFundMaleMny
	 *            the fund male mny
	 * @param pFundFemMny
	 *            the fund fem mny
	 * @param pFundMinerMny
	 *            the fund miner mny
	 * @param cFundMaleMny
	 *            the c fund male mny
	 * @param cFundFemMny
	 *            the c fund fem mny
	 * @param cFundMinerMny
	 *            the c fund miner mny
	 * @param pFundExemptMaleMny
	 *            the fund exempt male mny
	 * @param pFundExemptFemMny
	 *            the fund exempt fem mny
	 * @param pFundExemptMinerMny
	 *            the fund exempt miner mny
	 * @param cFundExemptMaleMny
	 *            the c fund exempt male mny
	 * @param cFundExemptFemMny
	 *            the c fund exempt fem mny
	 * @param cFundExemptMinerMny
	 *            the c fund exempt miner mny
	 * @param childContributionMny
	 *            the child contribution mny
	 */
	public QismtPensionAvgearn(QismtPensionAvgearnPK qismtPensionAvgearnPK, long exclusVer, long pensionAvgEarn,
			long pensionUpperLimit, long pPensionMaleMny, long pPensionFemMny, long pPensionMinerMny,
			long cPensionMaleMny, long cPensionFemMny, long cPensionMinerMny, long pFundMaleMny, long pFundFemMny,
			long pFundMinerMny, long cFundMaleMny, long cFundFemMny, long cFundMinerMny, long pFundExemptMaleMny,
			long pFundExemptFemMny, long pFundExemptMinerMny, long cFundExemptMaleMny, long cFundExemptFemMny,
			long cFundExemptMinerMny, long childContributionMny) {
		this.qismtPensionAvgearnPK = qismtPensionAvgearnPK;
		this.exclusVer = exclusVer;
		this.pensionAvgEarn = pensionAvgEarn;
		this.pensionUpperLimit = pensionUpperLimit;
		this.pPensionMaleMny = pPensionMaleMny;
		this.pPensionFemMny = pPensionFemMny;
		this.pPensionMinerMny = pPensionMinerMny;
		this.cPensionMaleMny = cPensionMaleMny;
		this.cPensionFemMny = cPensionFemMny;
		this.cPensionMinerMny = cPensionMinerMny;
		this.pFundMaleMny = pFundMaleMny;
		this.pFundFemMny = pFundFemMny;
		this.pFundMinerMny = pFundMinerMny;
		this.cFundMaleMny = cFundMaleMny;
		this.cFundFemMny = cFundFemMny;
		this.cFundMinerMny = cFundMinerMny;
		this.pFundExemptMaleMny = pFundExemptMaleMny;
		this.pFundExemptFemMny = pFundExemptFemMny;
		this.pFundExemptMinerMny = pFundExemptMinerMny;
		this.cFundExemptMaleMny = cFundExemptMaleMny;
		this.cFundExemptFemMny = cFundExemptFemMny;
		this.cFundExemptMinerMny = cFundExemptMinerMny;
		this.childContributionMny = childContributionMny;
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
	public QismtPensionAvgearn(String ccd, String siOfficeCd, String histId, short pensionGrade) {
		this.qismtPensionAvgearnPK = new QismtPensionAvgearnPK(ccd, siOfficeCd, histId, pensionGrade);
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
		if (!(object instanceof QismtPensionAvgearn)) {
			return false;
		}
		QismtPensionAvgearn other = (QismtPensionAvgearn) object;
		if ((this.qismtPensionAvgearnPK == null && other.qismtPensionAvgearnPK != null)
				|| (this.qismtPensionAvgearnPK != null
						&& !this.qismtPensionAvgearnPK.equals(other.qismtPensionAvgearnPK))) {
			return false;
		}
		return true;
	}

}
