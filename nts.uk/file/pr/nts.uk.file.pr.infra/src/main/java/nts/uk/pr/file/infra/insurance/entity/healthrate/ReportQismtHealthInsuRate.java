/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.insurance.entity.healthrate;

import java.io.Serializable;
import java.math.BigDecimal;
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

import lombok.Getter;
import lombok.Setter;
import nts.uk.pr.file.infra.insurance.entity.ReportQismtSocialInsuOffice;
import nts.uk.pr.file.infra.insurance.entity.healthavgearn.ReportQismtHealthInsuAvgearn;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class QismtHealthInsuRate.
 */
@Setter
@Getter
@Entity
@Table(name = "QISMT_HEALTH_INSU_RATE")
public class ReportQismtHealthInsuRate extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt health insu rate PK. */
	@EmbeddedId
	protected ReportQismtHealthInsuRatePK qismtHealthInsuRatePK;

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
	private List<ReportQismtHealthInsuAvgearn> qismtHealthInsuAvgearnList;

	/** The qismt social insu office. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "SI_OFFICE_CD", referencedColumnName = "SI_OFFICE_CD", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private ReportQismtSocialInsuOffice qismtSocialInsuOffice;

	/**
	 * Instantiates a new qismt health insu rate.
	 */
	public ReportQismtHealthInsuRate() {
		super();
	}

	/**
	 * Instantiates a new qismt health insu rate.
	 *
	 * @param qismtHealthInsuRatePK
	 *            the qismt health insu rate PK
	 */
	public ReportQismtHealthInsuRate(ReportQismtHealthInsuRatePK qismtHealthInsuRatePK) {
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
	public ReportQismtHealthInsuRate(String ccd, String siOfficeCd, String histId) {
		this.qismtHealthInsuRatePK = new ReportQismtHealthInsuRatePK(ccd, siOfficeCd, histId);
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
		if (!(object instanceof ReportQismtHealthInsuRate)) {
			return false;
		}
		ReportQismtHealthInsuRate other = (ReportQismtHealthInsuRate) object;
		if ((this.qismtHealthInsuRatePK == null && other.qismtHealthInsuRatePK != null)
				|| (this.qismtHealthInsuRatePK != null
						&& !this.qismtHealthInsuRatePK.equals(other.qismtHealthInsuRatePK))) {
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
		return this.qismtHealthInsuRatePK;
	}

}
