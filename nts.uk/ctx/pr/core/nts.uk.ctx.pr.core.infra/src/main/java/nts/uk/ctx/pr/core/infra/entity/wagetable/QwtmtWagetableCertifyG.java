/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * The Class QwtmtWagetableCertifyG.
 */
@Data
@Entity
@Table(name = "QWTMT_WAGETABLE_CERTIFY_G")
public class QwtmtWagetableCertifyG implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable certify GPK. */
	@EmbeddedId
	protected QwtmtWagetableCertifyGPK qwtmtWagetableCertifyGPK;

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

	/** The certify group name. */
	@Column(name = "CERTIFY_GROUP_NAME")
	private String certifyGroupName;

	/** The multi apply set. */
	@Column(name = "MULTI_APPLY_SET")
	private Integer multiApplySet;

	/** The qwtmt wagetable certify list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qwtmtWagetableCertifyG")
	private List<QwtmtWagetableCertify> qwtmtWagetableCertifyList;

	/**
	 * Instantiates a new qwtmt wagetable certify G.
	 */
	public QwtmtWagetableCertifyG() {
		super();
	}
	
	/**
	 * Instantiates a new qwtmt wagetable certify G.
	 *
	 * @param qwtmtWagetableCertifyGPK the qwtmt wagetable certify GPK
	 */
	public QwtmtWagetableCertifyG(QwtmtWagetableCertifyGPK qwtmtWagetableCertifyGPK) {
		this.qwtmtWagetableCertifyGPK = qwtmtWagetableCertifyGPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable certify G.
	 *
	 * @param qwtmtWagetableCertifyGPK the qwtmt wagetable certify GPK
	 * @param exclusVer the exclus ver
	 */
	public QwtmtWagetableCertifyG(QwtmtWagetableCertifyGPK qwtmtWagetableCertifyGPK, long exclusVer) {
		this.qwtmtWagetableCertifyGPK = qwtmtWagetableCertifyGPK;
		this.exclusVer = exclusVer;
	}

	/**
	 * Instantiates a new qwtmt wagetable certify G.
	 *
	 * @param ccd the ccd
	 * @param certifyGroupCd the certify group cd
	 */
	public QwtmtWagetableCertifyG(String ccd, String certifyGroupCd) {
		this.qwtmtWagetableCertifyGPK = new QwtmtWagetableCertifyGPK(ccd, certifyGroupCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableCertifyGPK != null ? qwtmtWagetableCertifyGPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableCertifyG)) {
			return false;
		}
		QwtmtWagetableCertifyG other = (QwtmtWagetableCertifyG) object;
		if ((this.qwtmtWagetableCertifyGPK == null && other.qwtmtWagetableCertifyGPK != null)
				|| (this.qwtmtWagetableCertifyGPK != null
						&& !this.qwtmtWagetableCertifyGPK.equals(other.qwtmtWagetableCertifyGPK))) {
			return false;
		}
		return true;
	}

}
