/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.social;

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
 * The Class QismtSocialInsuOffice.
 */
@Data
@Entity
@Table(name = "QISMT_SOCIAL_INSU_OFFICE")
public class QismtSocialInsuOffice implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The qismt social insu office PK. */
	@EmbeddedId
	protected QismtSocialInsuOfficePK qismtSocialInsuOfficePK;
	
	/** The ins date. */
	@Column(name = "ins_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDate;
	
	/** The ins ccd. */
	@Column(name = "ins_ccd")
	private String insCcd;
	
	/** The ins scd. */
	@Column(name = "ins_scd")
	private String insScd;
	
	/** The ins pg. */
	@Column(name = "ins_pg")
	private String insPg;
	
	/** The upd date. */
	@Column(name = "upd_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDate;
	
	/** The upd ccd. */
	@Column(name = "upd_ccd")
	private String updCcd;
	
	/** The upd scd. */
	@Column(name = "upd_scd")
	private String updScd;
	
	/** The upd pg. */
	@Column(name = "upd_pg")
	private String updPg;
	
	/** The exclus ver. */
	@Basic(optional = false)
	@Column(name = "exclus_ver")
	private long exclusVer;
	
	/** The si office name. */
	@Basic(optional = false)
	@Column(name = "si_office_name")
	private String siOfficeName;
	
	/** The si office ab name. */
	@Column(name = "si_office_ab_name")
	private String siOfficeAbName;
	
	/** The president name. */
	@Column(name = "president_name")
	private String presidentName;
	
	/** The president title. */
	@Column(name = "president_title")
	private String presidentTitle;
	
	/** The postal. */
	@Column(name = "postal")
	private String postal;
	
	/** The prefecture. */
	@Column(name = "prefecture")
	private String prefecture;
	
	/** The address 1. */
	@Column(name = "address1")
	private String address1;
	
	/** The address 2. */
	@Column(name = "address2")
	private String address2;
	
	/** The kn address 1. */
	@Column(name = "kn_address1")
	private String knAddress1;
	
	/** The kn address 2. */
	@Column(name = "kn_address2")
	private String knAddress2;
	
	/** The tel no. */
	@Column(name = "tel_no")
	private String telNo;
	
	/** The health insu office refno 1. */
	@Column(name = "health_insu_office_refno1")
	private String healthInsuOfficeRefno1;
	
	/** The health insu office refno 2. */
	@Column(name = "health_insu_office_refno2")
	private String healthInsuOfficeRefno2;
	
	/** The pension office refno 1. */
	@Column(name = "pension_office_refno1")
	private String pensionOfficeRefno1;
	
	/** The pension office refno 2. */
	@Column(name = "pension_office_refno2")
	private String pensionOfficeRefno2;
	
	/** The pension fund no. */
	@Column(name = "pension_fund_no")
	private String pensionFundNo;
	
	/** The pension fund office no. */
	@Column(name = "pension_fund_office_no")
	private String pensionFundOfficeNo;
	
	/** The health insu city mark. */
	@Column(name = "health_insu_city_mark")
	private String healthInsuCityMark;
	
	/** The health insu office mark. */
	@Column(name = "health_insu_office_mark")
	private String healthInsuOfficeMark;
	
	/** The pension city mark. */
	@Column(name = "pension_city_mark")
	private String pensionCityMark;
	
	/** The pension office mark. */
	@Column(name = "pension_office_mark")
	private String pensionOfficeMark;
	
	/** The health insu office no. */
	@Column(name = "health_insu_office_no")
	private String healthInsuOfficeNo;
	
	/** The health insu asso no. */
	@Column(name = "health_insu_asso_no")
	private String healthInsuAssoNo;
	
	/** The memo. */
	@Column(name = "memo")
	private String memo;

	/**
	 * Instantiates a new qismt social insu office.
	 */
	public QismtSocialInsuOffice() {
	}

	/**
	 * Instantiates a new qismt social insu office.
	 *
	 * @param qismtSocialInsuOfficePK the qismt social insu office PK
	 */
	public QismtSocialInsuOffice(QismtSocialInsuOfficePK qismtSocialInsuOfficePK) {
		this.qismtSocialInsuOfficePK = qismtSocialInsuOfficePK;
	}

	/**
	 * Instantiates a new qismt social insu office.
	 *
	 * @param qismtSocialInsuOfficePK the qismt social insu office PK
	 * @param exclusVer the exclus ver
	 * @param siOfficeName the si office name
	 */
	public QismtSocialInsuOffice(QismtSocialInsuOfficePK qismtSocialInsuOfficePK, long exclusVer, String siOfficeName) {
		this.qismtSocialInsuOfficePK = qismtSocialInsuOfficePK;
		this.exclusVer = exclusVer;
		this.siOfficeName = siOfficeName;
	}

	/**
	 * Instantiates a new qismt social insu office.
	 *
	 * @param ccd the ccd
	 * @param siOfficeCd the si office cd
	 */
	public QismtSocialInsuOffice(String ccd, String siOfficeCd) {
		this.qismtSocialInsuOfficePK = new QismtSocialInsuOfficePK(ccd, siOfficeCd);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtSocialInsuOfficePK != null ? qismtSocialInsuOfficePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QismtSocialInsuOffice)) {
			return false;
		}
		QismtSocialInsuOffice other = (QismtSocialInsuOffice) object;
		if ((this.qismtSocialInsuOfficePK == null && other.qismtSocialInsuOfficePK != null)
				|| (this.qismtSocialInsuOfficePK != null
						&& !this.qismtSocialInsuOfficePK.equals(other.qismtSocialInsuOfficePK))) {
			return false;
		}
		return true;
	}

}
