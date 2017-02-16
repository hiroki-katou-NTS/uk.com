/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.labor;

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
 * The Class QismtLaborInsuOffice.
 */
@Data
@Entity
@Table(name = "QISMT_LABOR_INSU_OFFICE")
public class QismtLaborInsuOffice implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt labor insu office PK. */
	@EmbeddedId
	protected QismtLaborInsuOfficePK qismtLaborInsuOfficePK;

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

	/** The li office name. */
	@Basic(optional = false)
	@Column(name = "li_office_name")
	private String liOfficeName;

	/** The li office ab name. */
	@Column(name = "li_office_ab_name")
	private String liOfficeAbName;

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

	/** The city sign. */
	@Column(name = "city_sign")
	private String citySign;

	/** The office mark. */
	@Column(name = "office_mark")
	private String officeMark;

	/** The office no A. */
	@Column(name = "office_no_a")
	private String officeNoA;

	/** The office no B. */
	@Column(name = "office_no_b")
	private String officeNoB;

	/** The office no C. */
	@Column(name = "office_no_c")
	private Character officeNoC;

	/** The memo. */
	@Column(name = "memo")
	private String memo;

	/**
	 * Instantiates a new qismt labor insu office.
	 */
	public QismtLaborInsuOffice() {
	}

	/**
	 * Instantiates a new qismt labor insu office.
	 *
	 * @param qismtLaborInsuOfficePK the qismt labor insu office PK
	 */
	public QismtLaborInsuOffice(QismtLaborInsuOfficePK qismtLaborInsuOfficePK) {
		this.qismtLaborInsuOfficePK = qismtLaborInsuOfficePK;
	}

	/**
	 * Instantiates a new qismt labor insu office.
	 *
	 * @param qismtLaborInsuOfficePK the qismt labor insu office PK
	 * @param exclusVer the exclus ver
	 * @param liOfficeName the li office name
	 */
	public QismtLaborInsuOffice(QismtLaborInsuOfficePK qismtLaborInsuOfficePK, long exclusVer, String liOfficeName) {
		this.qismtLaborInsuOfficePK = qismtLaborInsuOfficePK;
		this.exclusVer = exclusVer;
		this.liOfficeName = liOfficeName;
	}

	/**
	 * Instantiates a new qismt labor insu office.
	 *
	 * @param ccd the ccd
	 * @param liOfficeCd the li office cd
	 */
	public QismtLaborInsuOffice(String ccd, String liOfficeCd) {
		this.qismtLaborInsuOfficePK = new QismtLaborInsuOfficePK(ccd, liOfficeCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtLaborInsuOfficePK != null ? qismtLaborInsuOfficePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QismtLaborInsuOffice)) {
			return false;
		}
		QismtLaborInsuOffice other = (QismtLaborInsuOffice) object;
		if ((this.qismtLaborInsuOfficePK == null && other.qismtLaborInsuOfficePK != null)
				|| (this.qismtLaborInsuOfficePK != null
						&& !this.qismtLaborInsuOfficePK.equals(other.qismtLaborInsuOfficePK))) {
			return false;
		}
		return true;
	}

}
