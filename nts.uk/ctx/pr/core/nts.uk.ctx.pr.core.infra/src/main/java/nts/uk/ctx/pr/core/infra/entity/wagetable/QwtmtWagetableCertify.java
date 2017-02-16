/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * The Class QwtmtWagetableCertify.
 */
@Data
@Entity
@Table(name = "qwtmt_wagetable_certify")
public class QwtmtWagetableCertify implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable certify PK. */
	@EmbeddedId
	protected QwtmtWagetableCertifyPK qwtmtWagetableCertifyPK;

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

	/** The qcemt certification. */
	@JoinColumns({ @JoinColumn(name = "ccd", referencedColumnName = "ccd", insertable = false, updatable = false),
			@JoinColumn(name = "certify_cd", referencedColumnName = "cert_cd", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private QcemtCertification qcemtCertification;

	/** The qwtmt wagetable certify G. */
	@JoinColumns({ @JoinColumn(name = "ccd", referencedColumnName = "ccd", insertable = false, updatable = false),
			@JoinColumn(name = "certify_group_cd", referencedColumnName = "certify_group_cd", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private QwtmtWagetableCertifyG qwtmtWagetableCertifyG;

	/**
	 * Instantiates a new qwtmt wagetable certify.
	 */
	public QwtmtWagetableCertify() {
	}

	/**
	 * Instantiates a new qwtmt wagetable certify.
	 *
	 * @param qwtmtWagetableCertifyPK the qwtmt wagetable certify PK
	 */
	public QwtmtWagetableCertify(QwtmtWagetableCertifyPK qwtmtWagetableCertifyPK) {
		this.qwtmtWagetableCertifyPK = qwtmtWagetableCertifyPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable certify.
	 *
	 * @param qwtmtWagetableCertifyPK the qwtmt wagetable certify PK
	 * @param exclusVer the exclus ver
	 */
	public QwtmtWagetableCertify(QwtmtWagetableCertifyPK qwtmtWagetableCertifyPK, long exclusVer) {
		this.qwtmtWagetableCertifyPK = qwtmtWagetableCertifyPK;
		this.exclusVer = exclusVer;
	}

	/**
	 * Instantiates a new qwtmt wagetable certify.
	 *
	 * @param ccd the ccd
	 * @param certifyGroupCd the certify group cd
	 * @param certifyCd the certify cd
	 */
	public QwtmtWagetableCertify(String ccd, String certifyGroupCd, String certifyCd) {
		this.qwtmtWagetableCertifyPK = new QwtmtWagetableCertifyPK(ccd, certifyGroupCd, certifyCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableCertifyPK != null ? qwtmtWagetableCertifyPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableCertify)) {
			return false;
		}
		QwtmtWagetableCertify other = (QwtmtWagetableCertify) object;
		if ((this.qwtmtWagetableCertifyPK == null && other.qwtmtWagetableCertifyPK != null)
				|| (this.qwtmtWagetableCertifyPK != null
						&& !this.qwtmtWagetableCertifyPK.equals(other.qwtmtWagetableCertifyPK))) {
			return false;
		}
		return true;
	}
}
