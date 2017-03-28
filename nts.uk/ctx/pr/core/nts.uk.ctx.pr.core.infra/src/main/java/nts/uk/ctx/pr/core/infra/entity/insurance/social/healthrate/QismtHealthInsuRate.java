/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate;

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
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;

/**
 * The Class QismtHealthInsuRate.
 */
@Data
@Entity
@Table(name = "QISMT_HEALTH_INSU_RATE")
public class QismtHealthInsuRate implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt health insu rate PK. */
	@EmbeddedId
	protected QismtHealthInsuRatePK qismtHealthInsuRatePK;

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

	/** The p pay general rate. */
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(name = "P_PAY_GENERAL_RATE")
	private BigDecimal pPayGeneralRate;

	/** The p pay nursing rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_NURSING_RATE")
	private BigDecimal pPayNursingRate;

	/** The p pay specific rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_SPECIFIC_RATE")
	private BigDecimal pPaySpecificRate;

	/** The p pay basic rate. */
	@Basic(optional = false)
	@Column(name = "P_PAY_BASIC_RATE")
	private BigDecimal pPayBasicRate;

	/** The p pay health round atr. */
	@Basic(optional = false)
	@Column(name = "P_PAY_HEALTH_ROUND_ATR")
	private int pPayHealthRoundAtr;

	/** The c pay general rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_GENERAL_RATE")
	private BigDecimal cPayGeneralRate;

	/** The c pay nursing rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_NURSING_RATE")
	private BigDecimal cPayNursingRate;

	/** The c pay specific rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_SPECIFIC_RATE")
	private BigDecimal cPaySpecificRate;

	/** The c pay basic rate. */
	@Basic(optional = false)
	@Column(name = "C_PAY_BASIC_RATE")
	private BigDecimal cPayBasicRate;

	/** The c pay health round atr. */
	@Basic(optional = false)
	@Column(name = "C_PAY_HEALTH_ROUND_ATR")
	private int cPayHealthRoundAtr;

	/** The p bns general rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_GENERAL_RATE")
	private BigDecimal pBnsGeneralRate;

	/** The p bns nursing rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_NURSING_RATE")
	private BigDecimal pBnsNursingRate;

	/** The p bns specific rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_SPECIFIC_RATE")
	private BigDecimal pBnsSpecificRate;

	/** The p bns basic rate. */
	@Basic(optional = false)
	@Column(name = "P_BNS_BASIC_RATE")
	private BigDecimal pBnsBasicRate;

	/** The p bns health round atr. */
	@Basic(optional = false)
	@Column(name = "P_BNS_HEALTH_ROUND_ATR")
	private int pBnsHealthRoundAtr;

	/** The c bns general rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_GENERAL_RATE")
	private BigDecimal cBnsGeneralRate;

	/** The c bns nursing rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_NURSING_RATE")
	private BigDecimal cBnsNursingRate;

	/** The c bns specific rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_SPECIFIC_RATE")
	private BigDecimal cBnsSpecificRate;

	/** The c bns basic rate. */
	@Basic(optional = false)
	@Column(name = "C_BNS_BASIC_RATE")
	private BigDecimal cBnsBasicRate;

	/** The c bns health round atr. */
	@Basic(optional = false)
	@Column(name = "C_BNS_HEALTH_ROUND_ATR")
	private int cBnsHealthRoundAtr;

	/** The bonus health max mny. */
	@Basic(optional = false)
	@Column(name = "BONUS_HEALTH_MAX_MNY")
	private BigDecimal bonusHealthMaxMny;

	/** The keep entry flg. */
	@Basic(optional = false)
	@Column(name = "KEEP_ENTRY_FLG")
	private int keepEntryFlg;

	/** The qismt health insu avgearn list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qismtHealthInsuRate", orphanRemoval = true)
	private List<QismtHealthInsuAvgearn> qismtHealthInsuAvgearnList;

	/** The qismt social insu office. */
	@JoinColumns({ @JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "SI_OFFICE_CD", referencedColumnName = "SI_OFFICE_CD", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private QismtSocialInsuOffice qismtSocialInsuOffice;

	/**
	 * Instantiates a new qismt health insu rate.
	 */
	public QismtHealthInsuRate() {
	}

	/**
	 * Instantiates a new qismt health insu rate.
	 *
	 * @param qismtHealthInsuRatePK
	 *            the qismt health insu rate PK
	 */
	public QismtHealthInsuRate(QismtHealthInsuRatePK qismtHealthInsuRatePK) {
		this.qismtHealthInsuRatePK = qismtHealthInsuRatePK;
	}

	/**
	 * Instantiates a new qismt health insu rate.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 * @param histId
	 *            the hist id
	 */
	public QismtHealthInsuRate(String ccd, String siOfficeCd, String histId) {
		this.qismtHealthInsuRatePK = new QismtHealthInsuRatePK(ccd, siOfficeCd, histId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtHealthInsuRatePK != null ? qismtHealthInsuRatePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QismtHealthInsuRate)) {
			return false;
		}
		QismtHealthInsuRate other = (QismtHealthInsuRate) object;
		if ((this.qismtHealthInsuRatePK == null && other.qismtHealthInsuRatePK != null)
				|| (this.qismtHealthInsuRatePK != null
						&& !this.qismtHealthInsuRatePK.equals(other.qismtHealthInsuRatePK))) {
			return false;
		}
		return true;
	}

}
