/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.QismtSocialInsuOffice;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;

/**
 * The Class QismtPensionRate.
 */
@Data
@Entity
@Table(name = "QISMT_PENSION_RATE")
public class QismtPensionRate implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt pension rate PK. */
	@EmbeddedId
	protected QismtPensionRatePK qismtPensionRatePK;

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

	/** The str ym. */
	@Basic(optional = false)
	@Column(name = "STR_YM")
	private int strYm;

	/** The end ym. */
	@Basic(optional = false)
	@Column(name = "END_YM")
	private int endYm;

	/** The pension fund join atr. */
	@Basic(optional = false)
	@Column(name = "PENSION_FUND_JOIN_ATR")
	private int pensionFundJoinAtr;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	/** The p pay pension male rate. */
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(name = "P_PAY_PENSION_MALE_RATE")
	private BigDecimal pPayPensionMaleRate;

	/** The p pay pension fem rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_PENSION_FEM_RATE")
	private BigDecimal pPayPensionFemRate;

	/** The p pay pension miner rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_PENSION_MINER_RATE")
	private BigDecimal pPayPensionMinerRate;

	/** The p pay pension round atr. */
	@Basic(optional = false)
	@Column(name = "P_PAY_PENSION_ROUND_ATR")
	private int pPayPensionRoundAtr;

	/** The c pay pension male rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_PENSION_MALE_RATE")
	private BigDecimal cPayPensionMaleRate;

	/** The c pay pension fem rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_PENSION_FEM_RATE")
	private BigDecimal cPayPensionFemRate;

	/** The c pay pension miner rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_PENSION_MINER_RATE")
	private BigDecimal cPayPensionMinerRate;

	/** The c pay pension round atr. */
	@Basic(optional = false)
	@Column(name = "C_PAY_PENSION_ROUND_ATR")
	private int cPayPensionRoundAtr;

	/** The p bns pension male rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_PENSION_MALE_RATE")
	private BigDecimal pBnsPensionMaleRate;

	/** The p bns pension fem rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_PENSION_FEM_RATE")
	private BigDecimal pBnsPensionFemRate;

	/** The p bns pension miner rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_PENSION_MINER_RATE")
	private BigDecimal pBnsPensionMinerRate;

	/** The p bns pension round atr. */
	@Basic(optional = false)
	@Column(name = "P_BNS_PENSION_ROUND_ATR")
	private int pBnsPensionRoundAtr;

	/** The c bns pension male rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_PENSION_MALE_RATE")
	private BigDecimal cBnsPensionMaleRate;

	/** The c bns pension fem rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_PENSION_FEM_RATE")
	private BigDecimal cBnsPensionFemRate;

	/** The c bns pension miner rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_PENSION_MINER_RATE")
	private BigDecimal cBnsPensionMinerRate;

	/** The c bns pension round atr. */
	@Basic(optional = false)
	@Column(name = "C_BNS_PENSION_ROUND_ATR")
	private int cBnsPensionRoundAtr;

	/** The p pay fund male rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_FUND_MALE_RATE")
	private BigDecimal pPayFundMaleRate;

	/** The p pay fund ex male rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_FUND_EX_MALE_RATE")
	private BigDecimal pPayFundExMaleRate;

	/** The p pay fund fem rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_FUND_FEM_RATE")
	private BigDecimal pPayFundFemRate;

	/** The p pay fund ex fem rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_FUND_EX_FEM_RATE")
	private BigDecimal pPayFundExFemRate;

	/** The p pay fund miner rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_FUND_MINER_RATE")
	private BigDecimal pPayFundMinerRate;

	/** The p pay fund ex miner rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_FUND_EX_MINER_RATE")
	private BigDecimal pPayFundExMinerRate;

	/** The c pay fund male rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_FUND_MALE_RATE")
	private BigDecimal cPayFundMaleRate;

	/** The c pay fund ex male rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_FUND_EX_MALE_RATE")
	private BigDecimal cPayFundExMaleRate;

	/** The c pay fund fem rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_FUND_FEM_RATE")
	private BigDecimal cPayFundFemRate;

	/** The c pay fund ex fem rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_FUND_EX_FEM_RATE")
	private BigDecimal cPayFundExFemRate;

	/** The c pay fund miner rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_FUND_MINER_RATE")
	private BigDecimal cPayFundMinerRate;

	/** The c pay fund ex miner rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_FUND_EX_MINER_RATE")
	private BigDecimal cPayFundExMinerRate;

	/** The p bns fund man rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_FUND_MAN_RATE")
	private BigDecimal pBnsFundManRate;

	/** The p bns fund ex male rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_FUND_EX_MALE_RATE")
	private BigDecimal pBnsFundExMaleRate;

	/** The p bns fund fem rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_FUND_FEM_RATE")
	private BigDecimal pBnsFundFemRate;

	/** The p bns fund ex fem rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_FUND_EX_FEM_RATE")
	private BigDecimal pBnsFundExFemRate;

	/** The p bns fund miner rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_FUND_MINER_RATE")
	private BigDecimal pBnsFundMinerRate;

	/** The p bns fund ex miner rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_FUND_EX_MINER_RATE")
	private BigDecimal pBnsFundExMinerRate;

	/** The c bns fund man rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_FUND_MAN_RATE")
	private BigDecimal cBnsFundManRate;

	/** The c bns fund ex male rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_FUND_EX_MALE_RATE")
	private BigDecimal cBnsFundExMaleRate;

	/** The c bns fund fem rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_FUND_FEM_RATE")
	private BigDecimal cBnsFundFemRate;

	/** The c bns fund ex fem rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_FUND_EX_FEM_RATE")
	private BigDecimal cBnsFundExFemRate;

	/** The c bns fund miner rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_FUND_MINER_RATE")
	private BigDecimal cBnsFundMinerRate;

	/** The c bns fund ex miner rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_FUND_EX_MINER_RATE")
	private BigDecimal cBnsFundExMinerRate;

	/** The bonus pension max mny. */
	@Basic(optional = false)
	@Column(name = "BONUS_PENSION_MAX_MNY")
	private long bonusPensionMaxMny;

	/** The child contribution rate. */
	@Basic(optional = false)
	@Column(name = "CHILD_CONTRIBUTION_RATE")
	private BigDecimal childContributionRate;

	/** The keep entry flg. */
	@Basic(optional = false)
	@Column(name = "KEEP_ENTRY_FLG")
	private int keepEntryFlg;

	/** The qismt pension avgearn list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qismtPensionRate")
	private List<QismtPensionAvgearn> qismtPensionAvgearnList;

	/** The qismt social insu office. */
	@JoinColumns({ @JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "SI_OFFICE_CD", referencedColumnName = "SI_OFFICE_CD", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private QismtSocialInsuOffice qismtSocialInsuOffice;

	/**
	 * Instantiates a new qismt pension rate.
	 */
	public QismtPensionRate() {
	}

	/**
	 * Instantiates a new qismt pension rate.
	 *
	 * @param qismtPensionRatePK
	 *            the qismt pension rate PK
	 */
	public QismtPensionRate(QismtPensionRatePK qismtPensionRatePK) {
		this.qismtPensionRatePK = qismtPensionRatePK;
	}

	/**
	 * Instantiates a new qismt pension rate.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 * @param histId
	 *            the hist id
	 */
	public QismtPensionRate(String ccd, String siOfficeCd, String histId) {
		this.qismtPensionRatePK = new QismtPensionRatePK(ccd, siOfficeCd, histId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtPensionRatePK != null ? qismtPensionRatePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QismtPensionRate)) {
			return false;
		}
		QismtPensionRate other = (QismtPensionRate) object;
		if ((this.qismtPensionRatePK == null && other.qismtPensionRatePK != null)
				|| (this.qismtPensionRatePK != null && !this.qismtPensionRatePK.equals(other.qismtPensionRatePK))) {
			return false;
		}
		return true;
	}

}
